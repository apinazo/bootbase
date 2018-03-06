package es.apinazo.bootbase.business.persons;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

/**
 * Loads some data for testing purposes.
 */
@Transactional
@Component
public class PersonTestDataLoader {

    @Autowired
    private EntityManager entityManager;

    public void loadData() {
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

}
