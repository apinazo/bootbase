package es.apinazo.bootbase.business.persons;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("1")
public class Cat extends Pet {

    public Cat(){}

    public Cat(String name, int age, Gender gender) {
        super(name, age, gender);
    }

}
