package es.apinazo.bootbase.business.persons;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonServiceTest {

    @Autowired
    private PersonService personService;

    @MockBean
    private PersonRepository personRepository;

    private Person testPerson;

    @Before
    public void init() {

        testPerson = new Person(1, "angel", "pinazo", Gender.MALE, null);

        given(personRepository.save(ArgumentMatchers.any(Person.class)))
            .willReturn(testPerson);
    }

    @Test
    @WithMockUser(username = "user", roles = "ADMIN")
    public void whenCreatePerson_mustBeMe() throws Exception {

        Person person = personService.create(testPerson);
        Assert.assertEquals("angel", person.getFirstName());
    }

    @Test(expected = AccessDeniedException.class)
    @WithMockUser(username = "user", roles = "INVALID_ROLE")
    public void whenCreatedByDifferentRole_mustDenyAccess() throws Exception {

        Person person = personService.create(testPerson);
        Assert.assertEquals("angel", person.getFirstName());
    }

}