package es.apinazo.bootbase.business.persons;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

@Slf4j
@Service
public class PersonService {

    public void doSomeMagic(){
        log.info("Doing magic !!!");
    }

}
