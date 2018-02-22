package es.apinazo.bootbase.events;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

/**
 * Listens to {@link ApplicationReadyEvent}.
 *
 * This is sent just before the refresh is started but after bean definitions have been loaded.
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
public class ApplicationPreparedEventListener implements ApplicationListener<ApplicationPreparedEvent> {

    @Override
    public void onApplicationEvent(ApplicationPreparedEvent applicationPreparedEvent) {
        log.info("Events: Catched ApplicationPreparedEvent");
    }
}
