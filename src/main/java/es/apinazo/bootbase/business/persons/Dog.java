package es.apinazo.bootbase.business.persons;

import javax.persistence.Entity;

@Entity
public class Dog extends Pet {

    public Dog(String name, int age, Gender gender) {
        super(name, age, gender);
    }

}
