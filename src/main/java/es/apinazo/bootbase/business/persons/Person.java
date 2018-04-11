package es.apinazo.bootbase.business.persons;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Person {

    public Person(String firstName, String lastName, Gender gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // A linear sequence for each entity.
    private Integer id; // Long should be used if a big deal of IDs are to be expected.

    @Basic(optional = false) // Not nullable, but @Basic checks it before accessing the DB.
    private String firstName;

    private String lastName;

    @Enumerated
    private Gender gender;

    @JsonManagedReference // To avoid exceptions if no transaction is present.
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
