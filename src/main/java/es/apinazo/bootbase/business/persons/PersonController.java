package es.apinazo.bootbase.business.persons;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

}
