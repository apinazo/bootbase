package es.apinazo.bootbase.business.persons;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

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

}
