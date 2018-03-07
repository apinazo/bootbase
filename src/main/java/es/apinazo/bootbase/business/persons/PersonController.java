package es.apinazo.bootbase.business.persons;

import io.micrometer.core.annotation.Timed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RestController
@RequestMapping("/persons") // Root prefix of the URI of all endpoints managed by this controller.
public class PersonController {

    private PersonService personService;

    @Autowired // Inject dependencies in the constructor.
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public List<Person> getAll() {
        return this.personService.getAll();
    }

    @GetMapping("/{id}")
    public Person getPersonById(@PathVariable("id") int id) {
        return this.personService.getById(id);
    }

    @GetMapping("/firstName/{firstName}")
    public Person getPersonByFirstName(@PathVariable("firstName") String firstName) {

        return this.personService.findByFirstName(firstName);
    }

}
