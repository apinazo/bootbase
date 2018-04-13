package es.apinazo.bootbase.business.persons;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * Persons API.
 *
 * {@link RestController} offers several advantages:
 * <ul>
 *     <li>Save us from using @ResponseBody on each endpoint.</li>
 *     <li>It's transactional by default and this avoids the need of building DTO objects to avoid lazy loading problems.</li>
 * </ul>
 * To better use the object to JSON auto conversion and to avoid infinite recursion it will be needed
 * to add @JsonIgnore in the "many" side of relationships. @see {@link Pet} example.
 * If you don't want the controller to be transactional you can disable this behaviour with the configuration:
 * spring.jpa.open-in-view=false
 */
@NoArgsConstructor
@RestController
@RequestMapping("/people") // Root prefix of the URI of all endpoints managed by this controller.
public class PersonController {

    private PersonService personService;

    private PersonRepository personRepository;

    @Autowired // Inject dependencies in the constructor to ease tests.
    public PersonController(
        PersonService personService,
        PersonRepository personRepository) {

        this.personService = personService;
        this.personRepository = personRepository;
    }

    @PreAuthorize("hasAnyRole('ROLE_USER, ROLE_ADMIN')")
    @GetMapping
    public List<Person> getAll() {
        return this.personService.getAll();
    }

    @PreAuthorize("hasAnyRole('ROLE_USER, ROLE_ADMIN')")
    @GetMapping(value="/{id}")
    public Person getPersonById(@PathVariable("id") int id) {
        return this.personService.getById(id);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER, ROLE_ADMIN')")
    @GetMapping("/firstName/{firstName}")
    public Person getPersonByFirstName(@PathVariable("firstName") String firstName) {

        return this.personService.findByFirstName(firstName);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping
    public Person createPerson(@RequestBody Person person) {

        return this.personRepository.save(person);
    }

}
