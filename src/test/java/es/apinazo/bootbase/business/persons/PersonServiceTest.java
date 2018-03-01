package es.apinazo.bootbase.business.persons;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonServiceTest {

    @Autowired
    PersonService personService;

    @Test
    public void doSomeMagic() throws Exception {
        personService.doSomeMagic();
    }

}