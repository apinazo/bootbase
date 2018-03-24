package es.apinazo.bootbase.business.persons;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
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

    @Autowired // Inject dependencies in the constructor.
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

//    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping
    public List<Person> getAll() {
        return this.personService.getAll();
    }

    @GetMapping(value="/{id}")
    public Person getPersonById(@PathVariable("id") int id) {
        return this.personService.getById(id);
    }

    @GetMapping("/firstName/{firstName}")
    public Person getPersonByFirstName(@PathVariable("firstName") String firstName) {

        return this.personService.findByFirstName(firstName);
    }

//    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping
    public Person createPerson(@RequestBody Person person) {

        return new Person();
    }

}
