package es.apinazo.bootbase.configuration.demo;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;


/**
 * Demo of a configuration that would wail and abort the application launching.
 *
 * This will fail when property <code>configurations.fail</code> is <code>true</code>.
 */
@Data
@Slf4j
@Configuration
@ConfigurationProperties("configurations")
public class ExitOnFailureConfiguration {

    private boolean fail;

    public static class CustomExitOnFailureException
        extends Exception
        implements ExitCodeGenerator {

        /**
         * Implementing {@link ExitCodeGenerator} you can define
         * which exit code to use when this exception is thrown.
         *
         * @return The exit code.
         */
        @Override
        public int getExitCode() {
            return 666;
        }
    }

    /**
     * Maybe your app depends on a very important configuration
     * or condition it can't work without.
     * You can check it in {@link PostConstruct} and if so,
     * stop the application at all.
     *
     * @throws CustomExitOnFailureException The exception.
     */
    @PostConstruct
    public void check() throws CustomExitOnFailureException {

        // If configurations.fail == true, stop the app
        // with the custom exit code for this exception.
        if (fail) {
            throw new CustomExitOnFailureException();
        }
    }

}
