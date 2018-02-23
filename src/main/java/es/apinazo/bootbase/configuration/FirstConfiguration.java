package es.apinazo.bootbase.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * Sample first configuration, enabled by the @{@link ConditionalOnProperty} value.
 * If false, no {@link org.springframework.context.annotation.Bean} will be created.
 */
@Slf4j
@Configuration
@ConditionalOnProperty("configurations.first.enabled")
public class FirstConfiguration {

    /**
     * A {@link PostConstruct} annotated method will execute after
     * the bean has been initialized so after all the configurations are set.
     *
     * @see <a href="http://www.baeldung.com/running-setup-logic-on-startup-in-spring">Running setup logic on startup in Spring</a>
     */
    @PostConstruct
    public void init(){
        log.info("First configuration is ready.");
    }

}
