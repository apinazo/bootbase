package es.apinazo.bootbase.business.persons;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Loads some data for testing purposes.
 */
@Transactional
@Component
public class PersonTestDataLoader {

    private EntityManager entityManager;


    @Autowired
    public PersonTestDataLoader(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    public void loadData() {

        Cat cat = new Cat("miau", 2, Gender.MALE);
        entityManager.persist(cat);

        Dog dog = new Dog("guau", 3, Gender.FEMALE);
        entityManager.persist(dog);

        Person person = new Person();
        person.setFirstName("angel");
        person.setLastName("pinazo");
        person.setGender(Gender.MALE);
        person.addPet(cat);
        person.addPet(dog);
        entityManager.persist(person);
    }

}
