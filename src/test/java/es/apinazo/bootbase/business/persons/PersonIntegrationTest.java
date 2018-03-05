package es.apinazo.bootbase.business.persons;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
// Start the Spring Boot app in a random port.
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PersonIntegrationTest {

    // Will inject the random port.
    @Value("${local.server.port}")
    private String serverPort;


}
