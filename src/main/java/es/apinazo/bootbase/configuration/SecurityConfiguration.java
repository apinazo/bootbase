package es.apinazo.bootbase.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Global app Spring Security configuration.
 * Will affect all app URIS including both REST services and static content - if there were any.
 *
 * No @{@link org.springframework.context.annotation.Configuration} is needed since
 * @{@link EnableWebSecurity} includes it.
 *
 * See the excellent post by Baeldung named
 * <a href="http://www.baeldung.com/securing-a-restful-web-service-with-spring-security">
 * Securing a restful web service with Spring Security</a>.
 */
// Security by URI.
@EnableWebSecurity
// By roles and authorities with @Secured, @PreAuthorize and @PostAuthorized annotations enabled.
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;


    /**
     * Configure the entry point of authentication.
     *
     * Authentication starts on an entry point. This is the first place where a client will go.
     * It will always return unauthorized as that's true at the first request. Spring Security
     * offers a default implementation that later redirects to a login form page but we don't
     * want that behaviour here - the login form makes no sense in a REST API.
     * So this implementation will just deny the access.
     *
     * @return The entry point.
     */
    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {

        // Anonymous class implementing the AuthenticationEntryPoint.commence() method is replaced by a lambda expression.
        return
            (HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
            -> response.sendError( HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized" );
    }


    /**
     * Define what to do once the user has successfully logged in.
     *
     * In example, retrieve all its data from a custom domain entity,
     * write some logs...
     *
     * @return The handler.
     */
    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return
            (HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) -> {

                // Do something with the user, log the login, etc...
            };
    }


    /**
     * Configure the authentication management.
     *
     * When a request is received, this will be used to check the given
     * user name and password against the existing users.
     *
     * This implementation uses in memory authentication with an 'user'
     * and an 'admin' users created by default.
     *
     * @param auth An {@link AuthenticationManagerBuilder}.
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {

        // TODO: Replace by auth.authenticationProvider( <? extends DaoAuthenticationProvider> ).
        auth.inMemoryAuthentication() // Creates an in-memory AuthenticationProvider.
            .withUser("admin").password("password").roles("ADMIN")
            .and()
            .withUser("user").password("password").roles("USER");
    }


    /**
     * Configures security by URI.
     *
     * Defines the entry point, the {@link org.springframework.security.web.authentication.AuthenticationSuccessHandler},
     * which URIs are to be secured - by pattern - and which none.
     *
     * This will not secure class methods but will work in combination with that
     * and the most restrictive rule will be applied.
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
            .csrf().disable()
            .exceptionHandling()
            .authenticationEntryPoint(authenticationEntryPoint)
            .and()
            .authorizeRequests()
            .antMatchers("/**").authenticated()
            .and()
            .formLogin()
            .successHandler(authenticationSuccessHandler)
            .failureHandler(new SimpleUrlAuthenticationFailureHandler())
            .and()
            .logout();
    }

}