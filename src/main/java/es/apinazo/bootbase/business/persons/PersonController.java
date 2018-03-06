package es.apinazo.bootbase.business.persons;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController // Save us from using @ResponseBody on each endpoint.
@RequestMapping("/persons") // URL root of all endpoints managed by this controller.
public class PersonController {

    private PersonService personService;

    @Autowired // Inject dependencies in the constructor.
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/")
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
