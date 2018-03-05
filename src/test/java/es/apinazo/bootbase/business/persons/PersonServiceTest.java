package es.apinazo.bootbase.business.persons;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
// Start the Spring Boot app in a random port.
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PersonServiceTest {

    // Will inject the random port.
    @Value("${local.server.port}")
    private String serverPort;

    @Autowired
    private PersonService personService;


    @Test
    public void doSomeMagic() throws Exception {
        personService.doSomeMagic();
    }

}