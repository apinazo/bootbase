package es.apinazo.bootbase.events;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationFailedEvent;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * Listens to {@link ApplicationReadyEvent}.
 *
 * This event is sent if there is an exception on startup.
 *
 * When the event is triggered, context is ready so the listener can be a @{@link org.springframework.context.annotation.Bean}.
 *
 * @see <a href="https://docs.spring.io/spring-boot/docs/current-SNAPSHOT/reference/htmlsingle/#boot-features-application-events-and-listeners">Application events and listeners</a>
 */
@Slf4j
@Component
public class ApplicationFailedEventListener implements ApplicationListener<ApplicationFailedEvent> {

    @Override
    public void onApplicationEvent(ApplicationFailedEvent applicationFailedEvent) {
        log.info("Events: Catched ApplicationFailedEvent");
    }
}
