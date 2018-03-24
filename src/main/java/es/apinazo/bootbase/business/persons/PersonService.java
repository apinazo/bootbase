package es.apinazo.bootbase.business.persons;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Slf4j
@Service
public class PersonService {

    private PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        Assert.notNull(personRepository, "personRepository");
        this.personRepository = personRepository;
    }

    public void doSomeMagic(){
        log.info("Doing magic !!!");
    }

    public Person getById(int id) {
        return personRepository.findById(id).orElse(null);
    }

    public Person findByFirstName(String name) {
        return personRepository.findByFirstName(name);
    }

//    @PreAuthorize("hasRole('ROLE_USER')")
    public List<Person> getAll() {
        return personRepository.findAll();
    }

    public Page<Person> getAll(Pageable pageable) {
        return personRepository.findAll(pageable);
    }

}
