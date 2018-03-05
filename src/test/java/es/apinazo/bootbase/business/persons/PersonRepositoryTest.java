package es.apinazo.bootbase.business.persons;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

@Slf4j
@RunWith(SpringRunner.class)
@DataJpaTest // Creates an in-memory DB, disable @Component beans and rollback when finished.
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // Use the configured Datasource.
public class PersonRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PersonRepository personRepository;


    @Before
    public void before() {

        Cat cat = new Cat("miau", 2, Gender.MALE);
        entityManager.persist(cat);

        Dog dog = new Dog("guau", 3, Gender.FEMALE);
        entityManager.persist(dog);

        Person person01 = new Person();
        person01.setFirstName("angel");
        person01.setLastName("pinazo");
        person01.setGender(Gender.MALE);
        person01.addPet(cat);
        person01.addPet(dog);
        entityManager.persist(person01);
    }


    @Test
    public void findPersonByExample_WhenReturned_ShouldBeMe() {

        Person examplePerson = new Person();
        examplePerson.setLastName("pinazo");

        Example example = Example.of(examplePerson);

        this.checkThePersonIsMe(example);
    }


    @Test
    public void findPersonByExampleMatcher_WhenReturned_ShouldBeMe() {

        Person examplePerson = new Person();
        examplePerson.setLastName("Pinazo");

        ExampleMatcher matcher = ExampleMatcher.matching()
            .withIgnoreCase();

        Example example = Example.of(examplePerson, matcher);

        this.checkThePersonIsMe(example);
    }


    @Test
    public void findPersonByExampleMatcherIgnoringProperties_WhenReturned_ShouldBeMe() {

        Person examplePerson = new Person();
        examplePerson.setLastName("Pinazo");
        examplePerson.setFirstName("Not me");

        ExampleMatcher matcher = ExampleMatcher.matching()
            .withIgnoreCase()
            .withIgnorePaths("firstName");

        Example example = Example.of(examplePerson, matcher);

        this.checkThePersonIsMe(example);
    }


    private void checkThePersonIsMe(Example example) {

        Optional<Person> optionalPerson = personRepository.findOne(example);

        assertTrue(
            "Person must not be null",
            optionalPerson.isPresent());

        assertThat(optionalPerson.get().getLastName())
            .as("The person's name that should be mine")
            .isEqualTo("pinazo");
   }

}