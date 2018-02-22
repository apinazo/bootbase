package es.apinazo.bootbase.events;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * Listens to {@link ApplicationReadyEvent}.
 *
 * This event is sent after any application and command-line runners have been called.
 * It indicates that the application is ready to service requests.
 *
 * This would be a great place where implement a service registration, for example.
 *
 * When the event is triggered, context is ready so the listener can be a @{@link org.springframework.context.annotation.Bean}.
 *
 * @see <a href="https://docs.spring.io/spring-boot/docs/current-SNAPSHOT/reference/htmlsingle/#boot-features-application-events-and-listeners">Application events and listeners</a>
 */
@Slf4j
@Component
public class ApplicationReadyEventListener implements ApplicationListener<ApplicationReadyEvent> {

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        log.info("Events: Catched ApplicationReadyEvent");
    }
}
