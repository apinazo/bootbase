package es.apinazo.bootbase.business.persons;

import javax.persistence.Entity;

@Entity
public class Cat extends Pet {

    public Cat(String name, int age, Gender gender) {
        super(name, age, gender);
    }

}
