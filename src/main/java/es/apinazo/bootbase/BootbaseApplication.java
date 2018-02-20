package es.apinazo.bootbase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


// @SpringBootApplication equals to @Configuration, @EnableAutoConfiguration and @ComponentScan.
// @ComponentScan will scan all packages here and below.
@SpringBootApplication
public class BootbaseApplication {

	public static void main(String[] args) {

		SpringApplication.run(BootbaseApplication.class, args);
	}
}
