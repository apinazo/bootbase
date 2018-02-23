package es.apinazo.bootbase.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Slf4j
@Configuration
@ConditionalOnProperty("configurations.second.enabled")
public class SecondConfiguration{

    /**
     * A {@link PostConstruct} annotated method will execute after
     * the bean has been initialized so after all the configurations are set.
     *
     * @see <a href="http://www.baeldung.com/running-setup-logic-on-startup-in-spring">Running setup logic on startup in Spring</a>
     */
    @PostConstruct
    public void init(){
        log.info("Second configuration is ready.");
    }

}
