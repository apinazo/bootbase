package es.apinazo.bootbase.business.persons;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.context.annotation.Import;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Person {

    @Id
    @GeneratedValue
    private Integer id;

    private String firstName;

    private String lastName;

    @Enumerated
    private Gender gender;

    @OneToMany(mappedBy = "person", fetch = FetchType.LAZY)
    private List<Pet> pets = new ArrayList<>();


    /**
     * Adds a {@link Pet} to a {@link Person}.
     *
     * Since the {@link OneToMany} is bidirectional, changes must be made
     * in both sides. Having this utility method helps to avoid bugs
     * and to sync both sides of the relationship.
     *
     * @param pet The {@link Pet}.
     */
    public void addPet(Pet pet) {
        this.pets.add(pet);
        pet.setPerson(this);
    }

    /**
     * Removes a {@link Pet} from a {@link Person}.
     *
     * @param pet The {@link Pet}.
     */
    public void removePet(Pet pet) {
        this.pets.remove(pet);
        pet.setPerson(null);
    }

}
