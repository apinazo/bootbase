package es.apinazo.bootbase.business.persons;

import lombok.Data;
import org.springframework.hateoas.ResourceSupport;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Data // Getters and setters must be defined in order to this to work.
public class PersonResource extends ResourceSupport {

    private final Person person;

    public PersonResource(final Person person) {

        // This will set the Person to the ResourceSupport
        // and it will converted to JSON on the response.
        this.person = person;
        final int id = person.getId();

        // Add link to root URI of PersonHateoasController with the label "people".
        add(linkTo(PersonHateoasController.class).withRel("people"));

        // Add link to getPersonById method in PersonHateoasController with label "self".
        add(linkTo(methodOn(PersonHateoasController.class).getPersonById(id)).withSelfRel());
    }
}