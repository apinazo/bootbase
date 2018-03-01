package es.apinazo.bootbase.business.persons;

import lombok.Data;
import org.springframework.context.annotation.Import;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Person {

    @Id
    @GeneratedValue
    Integer id;

    String firstName;

    String lastName;

}
