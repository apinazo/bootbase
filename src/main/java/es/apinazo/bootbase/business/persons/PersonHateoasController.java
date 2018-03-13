package es.apinazo.bootbase.business.persons;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Example of using HATEOAS.
 *
 * {@link RequestMapping} produces hal+json as it is using the
 * <a href="https://en.wikipedia.org/wiki/Hypertext_Application_Language">
 * Hypermedia Application Language</a> with JSON.
 */
@NoArgsConstructor
@RestController
@RequestMapping(value = "/people/hateoas", produces = "application/hal+json")
public class PersonHateoasController {

    private PersonService personService;

    @Autowired
    public PersonHateoasController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping(value="/{id}")
    public ResponseEntity<PersonResource> getPersonById(@PathVariable("id") int id) {

        PersonResource personResource = new PersonResource( this.personService.getById(id) );

        return ResponseEntity.ok(personResource);
    }

    @GetMapping
    public ResponseEntity<Resources<PersonResource>> getAll(Pageable pageable) {

        List<PersonResource> collection =
            personService.getAll(pageable).stream().map(PersonResource::new).collect(Collectors.toList());

        Resources<PersonResource> resources = new Resources<>(collection);

        String uriString = ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString();

        resources.add(new Link(uriString, "self"));

        return ResponseEntity.ok(resources);
    }

}
