package es.apinazo.bootbase.business.persons;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Data
@NoArgsConstructor
@ToString(exclude = "person")
@EqualsAndHashCode(exclude = "person")
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "kind", discriminatorType = DiscriminatorType.INTEGER)
public abstract class Pet {

    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    private int age;

    private Gender gender;

    @ManyToOne(fetch = FetchType.LAZY)
    private Person person;

    public Pet(String name, int age, Gender gender) {
        super();
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

}
