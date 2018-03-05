package es.apinazo.bootbase.business.persons;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/persons") // URL root of all endpoints managed by this controller.
public class PersonController {

    private PersonService personService;

    @Autowired // Inject dependencies in the constructor.
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/")
    public @ResponseBody List<Person> getAll() {
        return this.personService.getAll();
    }

    @GetMapping("/{id}")
    public @ResponseBody Person getPersonById(@PathVariable("id") int id) {
        return this.personService.getById(id);
    }

}
