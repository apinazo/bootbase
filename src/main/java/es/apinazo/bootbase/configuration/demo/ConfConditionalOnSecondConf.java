package es.apinazo.bootbase.configuration.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * Sample configuration conditional on another.
 *
 * Will be created only if {@link SecondConfiguration} is.
 */
@Slf4j
@Configuration
@ConditionalOnBean(SecondConfiguration.class)
public class ConfConditionalOnSecondConf {

    /**
     * A {@link PostConstruct} annotated method will execute after
     * the bean has been initialized so after all the configurations are set.
     *
     * @see <a href="http://www.baeldung.com/running-setup-logic-on-startup-in-spring">Running setup logic on startup in Spring</a>
     */
    @PostConstruct
    public void init(){
        log.info("Conditional on second configuration is ready.");
    }

}
