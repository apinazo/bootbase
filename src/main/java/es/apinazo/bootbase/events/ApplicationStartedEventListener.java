package es.apinazo.bootbase.events;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * Listens to {@link ApplicationReadyEvent}.
 *
 * This event is sent after the context has been refreshed but before any application and command-line runners have been called.
 * In this point, the app has started but it's not ready yet to service requests.
 *
 * Here we could implement a check to test if another services used by this one are available,
 * before being ready to service requests.
 *
 * When the event is triggered, context is ready so the listener can be a @{@link org.springframework.context.annotation.Bean}.
 *
 * @see <a href="https://docs.spring.io/spring-boot/docs/current-SNAPSHOT/reference/htmlsingle/#boot-features-application-events-and-listeners">Application events and listeners</a>
 */
@Slf4j
@Component
public class ApplicationStartedEventListener implements ApplicationListener<ApplicationStartedEvent> {

    @Override
    public void onApplicationEvent(ApplicationStartedEvent applicationStartedEvent) {
        log.info("Events: Catched ApplicationStartedEvent");
    }
}
