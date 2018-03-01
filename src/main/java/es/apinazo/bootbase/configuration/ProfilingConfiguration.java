package es.apinazo.bootbase.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.interceptor.CustomizableTraceInterceptor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

/**
 * Example of using AOP to profile and log method executions on any class
 * matching a pattern, an AOP expression.
 *
 * Enables logging and timing of {@link org.springframework.stereotype.Service},
 * {@link org.springframework.stereotype.Controller} and {@link org.springframework.stereotype.Repository}
 * classes depending on separate configurations.
 *
 * <ul>
 *     <li>Controller: bootbase.profiling.api.enabled</li>
 *     <li>Service: bootbase.profiling.service.enabled</li>
 *     <li>Repository: bootbase.profiling.repository.enabled</li>
 * </ul>
 *
 * All configurations default to true.
 *
 * Logs will be printed only on TRACE level and will show method, parameters and execution time.
 */
@Slf4j
@Configuration("bootbase.profiling")
public class ProfilingConfiguration {

    // There are many ways of defining pointcuts. Here are some of them,
    // meaning more less the same but using different syntax.
    //
    // Lots of examples of AOP pointcuts:
    // https://howtodoinjava.com/spring/spring-aop/writing-spring-aop-aspectj-pointcut-expressions-with-examples/

    // All methods from beans whose name ends by "Controller".
    private static final String API_POINTCUT = "bean(*Controller)";

    // All public methods of classes with a name ended by "Service", inside the es.bootbase package.
    private static final String SERVICE_POINTCUT = "execution(public * es.bootbase..*Service.*(..))";

    // All public methods of classes implementing the Repository interface.
    private static final String REPO_POINTCUT = "execution(public * org.springframework.data.repository.Repository+.*(..))";

    /**
     * The {@link CustomizableTraceInterceptor} is the piece responsible of printing logs.
     *
     * @return A {@link CustomizableTraceInterceptor} {@link Bean}.
     */
    @Bean
    public CustomizableTraceInterceptor interceptor() {

        CustomizableTraceInterceptor interceptor = new CustomizableTraceInterceptor();

        // Dynamically create a logger in the adviced class.
        // If false, CustomizableTraceInterceptor will be used and therefore the logs
        // will show that class name instead of the adviced one.
        interceptor.setUseDynamicLogger(true);

        // Set messages using placeholders provided by CustomizableTraceInterceptor.
        interceptor.setEnterMessage("Entering: $[methodName]($[arguments]).");
        interceptor.setExitMessage("Leaving: $[methodName](..) with return value $[returnValue], took $[invocationTime]ms.");
        interceptor.setExceptionMessage("Exception: $[exception]");

        return interceptor;
    }

    @Bean
    @ConditionalOnProperty(
        prefix = "bootbase.profiling",
        value = "repository.enabled")
    public Advisor repositoryTraceAdvisor() {

        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(REPO_POINTCUT);

        // Attach the interceptor to the endpoint.
        return new DefaultPointcutAdvisor(pointcut, interceptor());
    }

    @Bean
    @ConditionalOnProperty(
        prefix = "bootbase.profiling",
        value = "api.enabled")
    public Advisor apiTraceAdvisor() {

        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(API_POINTCUT);

        return new DefaultPointcutAdvisor(pointcut, interceptor());
    }

    @Bean
    @ConditionalOnProperty(
        prefix = "bootbase.profiling",
        value = "service.enabled")
    public Advisor serviceTraceAdvisor() {

        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(SERVICE_POINTCUT);

        return new DefaultPointcutAdvisor(pointcut, interceptor());
    }

    /**
     * Creates a {@link javax.servlet.Filter} that logs all requests,
     * including client info, headers, query string and payload.
     *
     * Suitable only for debugging purposes due to overload.
     *
     * @return A {@link CommonsRequestLoggingFilter} @{@link Bean}.
     */
    @Bean
    @ConditionalOnProperty(
        prefix = "bootbase.profiling",
        value = "requests.enabled")
    public CommonsRequestLoggingFilter requestLoggingFilter() {
        CommonsRequestLoggingFilter crlf = new CommonsRequestLoggingFilter();
        crlf.setIncludeHeaders(true);
        crlf.setIncludeClientInfo(true);
        crlf.setIncludeQueryString(true);
        crlf.setIncludePayload(true);
        return crlf;
    }

}
