package es.apinazo.bootbase.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration {

    @Bean
    public WebMvcConfigurer webConfigurer() {
        return new WebMvcConfigurer() {

            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                // This redirects /info to /actuator/info.
                registry.addRedirectViewController("/info", "/actuator/info");
            }

            @Override
            public void addCorsMappings(CorsRegistry registry) {
                // Enable CORS in all endpoints and allow any method and origin.
                registry.addMapping("/**").allowedMethods("*");
            }
        };
    }

}
