package es.apinazo.bootbase.configuration;

import es.apinazo.bootbase.business.persons.Person;
import es.apinazo.bootbase.business.persons.PersonRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProfilingConfigurationTest {

    @Autowired
    PersonRepository repository;

    @Test
    public void testWriteToLogs() {

        Person person = new Person();
        person.setFirstName("Perico");
        person.setLastName("Palotes");

        // Set logs to TRACE and see if some have been written.
        repository.save(person);
    }

}