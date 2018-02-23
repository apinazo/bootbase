package es.apinazo.bootbase;

import es.apinazo.bootbase.events.ApplicationStartingEventListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;


/**
 * Sample Spring Boot app.
 *
 * This class will start the Spring Boot Application.
 * Also is a good candidate as the primary @Configuration.
 *
 * @SpringBootApplication equals to @Configuration, @EnableAutoConfiguration and @ComponentScan.
 * @ComponentScan will scan all packages here and below.
 */
@SpringBootApplication
public class BootbaseApplication {

	/**
	 * This will launch the Spring Boot app in a embedded container.
	 *
	 * @param args Args.
	 */
	public static void main(String[] args) {

		SpringApplication app = new SpringApplication(BootbaseApplication.class);

		// Add listeners which must be set before context is ready.
		// Alternatively they can be added to META-INF/spring.factories file.
		app.addListeners(new ApplicationStartingEventListener());

		// And run the app!
		app.run(args);
	}
}
