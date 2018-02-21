package es.apinazo.bootbase.actuator;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

/**
 * In order to view all {@link HealthIndicator}s info, the property
 * management.endpoint.health.show-details must be set to true.
 *
 * The identifier for a {@link HealthIndicator} is the name of the bean
 * without the "HealthIndicator" suffix. So {@link CustomHealthIndicator}
 * will have the identifier "custom".
 */
@Component
public class CustomHealthIndicator implements HealthIndicator {

    private boolean error;

    /**
     * Return the health status for this indicator.
     * If any {@link HealthIndicator} returns DOWN, the global
     * health status will be DOWN also.
     *
     * @return The health status.
     */
    @Override
    public Health health() {
        int errorCode = check(); // Perform some specific health check.
        if (errorCode != 0) {
            // Build the endpoint object that will returned as JSON.
            return
                Health
                    .down() // "status" : "DOWN"
                    .withDetail("Error Code", errorCode) // "details" : {"Error Code" : "<errorCode>"}
                    .build();
        }
        return Health.up().build();
    }

    private int check() {
        error = !error;
        return error ? 1 : 0;
    }
}