package es.apinazo.bootbase.events;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * Listens to {@link ApplicationReadyEvent}.
 *
 * This is sent when the Environment to be used in the context is known but before the context is created.
 *
 * Therefore cannot be a bean nor @{@link org.springframework.beans.factory.annotation.Autowired} since there is yet no
 * context when this event is triggered.
 *
 * In order to catch it, it must be added to a SpringApplication instance with addListeners(...)
 * or to the META-INF/spring.factories file.
 *
 * @see <a href="https://docs.spring.io/spring-boot/docs/current-SNAPSHOT/reference/htmlsingle/#boot-features-application-events-and-listeners">Application events and listeners</a>
 */
@Slf4j
public class ApplicationEnvironmentPreparedEventListener implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {

    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent applicationEnvironmentPreparedEvent) {
        log.info("Events: Catched ApplicationEnvironmentPreparedEvent");
    }
}
