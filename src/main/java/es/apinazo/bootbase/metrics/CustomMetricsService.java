package es.apinazo.bootbase.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Metrics;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * Demo of using a {@link Counter} in a @{@link Service}.
 *
 * The idea is that the service itself manages its related metrics counters
 * and increase them.
 *
 * In Spring Boot 2.0.0, metrics now are built upon @see <a href="https://micrometer.io/docs">Micrometer</a>.
 *
 * In this example, metrics will available at this endpoints.
 *
 * All metrics for the counter:
 * <p>http://localhost:8001/actuator/metrics/page.visitors</p>
 *
 * Metrics for a given tag:
 * <p>http://localhost:8001/actuator/metrics/page.visitors?tag=age:30s</p> *
 */
@Slf4j
@Service
public class CustomMetricsService {

    // Adding a counter to Metrics registers it globally and makes it
    // available through the /actuator/metrics endpoint.
    //
    // Adding tags will help logically slicing the data when repeating the same counter.
    // Tags must be added with 2 values at a time since they a key-value pair.
    private Counter counterTag01 = Metrics.counter("page.visitors", "age", "20s");

    private Counter counterTag02 = Metrics.counter("page.visitors", "age", "30s");

    public CustomMetricsService(MeterRegistry registry) {
        registry.counter("page.visitors");
    }

    @Scheduled(fixedRate = 1000*5)
    private void incrementCounterTag01() {
        counterTag01.increment();
        log.info("page.visitors in their 20s: {}", counterTag01.count());
    }

    @Scheduled(fixedRate = 1000*5)
    private void incrementCounterTag02() {
        counterTag02.increment();
        counterTag02.increment();
        counterTag02.increment();
        log.info("page.visitors in their 30s: {}", counterTag02.count());
    }

}
