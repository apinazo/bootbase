package es.apinazo.bootbase.business.persons;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;


/**
 * Person integration tests.
 *
 * Will start a full Spring Boot web app and will test end to end
 * all {@link PersonController} endpoints using {@link TestRestTemplate}
 * and using no mocks but the real beans.
 * The test data will be loaded from {@link PersonTestDataLoader}.
 *
 * NOTE: This test is not transactional and thereupon there will be no DB rollback !!!
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PersonIntegrationTest {


    // TestRestTemplate is configured to use the prefix
    // http://localhost:${local.server.port} in all requests
    // so it can use relative paths.
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private PersonTestDataLoader testDataLoader;

    static private boolean dataIsLoaded = false;


    /**
     * Load data for this test.
     * Must be done in a separate transaction requests with RestTemplate
     * will be done outside the transaction.
     */
    @Before
    public void before() {

        // Check to ensure test data is loaded only once.
        if ( !dataIsLoaded ) {
            testDataLoader.loadData();
            dataIsLoaded = true;
        }
    }


    @Test
    public void findAllPeople_WhenReturned_ShouldBeAll() throws Exception {

        // 1
        // Using the raw JSON response and JSON-PATH.
        // https://github.com/json-path/JsonPath
        ResponseEntity<String> response =
            testRestTemplate
                .getForEntity("/persons", String.class, 1);

        Integer length = JsonPath.read(response.getBody(), "$.length()");

        assertThat( length ).as("There can be only one!").isEqualTo(1);

        // 2
        // Parsing the raw JSON
        ObjectMapper mapper = new ObjectMapper();
        List<Person> people2 = mapper.readValue(response.getBody(), new TypeReference<List<Person>>(){});

        assertThat(people2.size()).as("There can be only one!").isEqualTo(1);

        // 3
        // Getting the response directly into objects.
        ResponseEntity<List<Person>> response2 =
            testRestTemplate
                .exchange(
                    "/persons",
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Person>>(){}
                );

        List<Person> people = response2.getBody();

        assertThat(people.size()).as("There can be only one!").isEqualTo(1);
    }


    @Test
    public void findPersonById_WhenReturned_ShouldBeMe() throws Exception {

        ResponseEntity<Person> responseById =
            testRestTemplate
                .getForEntity("/persons/{id}", Person.class, 1);

        checkThePersonIsMe(responseById);
    }


    @Test
    public void findPersonByName_WhenReturned_ShouldBeMe() throws Exception {

        ResponseEntity<Person> response =
            testRestTemplate
                .getForEntity("/persons/firstName/{firstName}", Person.class, "angel");

        checkThePersonIsMe(response);
    }


    private void checkThePersonIsMe(ResponseEntity<Person> response) {

        Person person = response.getBody();

        assertThat(response.getStatusCode())
            .as("Response status must be OK")
            .isEqualTo(HttpStatus.OK);

        assertNotNull("Person must not be null", person);

        assertThat(person.getFirstName())
            .as("The person's first name that should be mine")
            .isEqualTo("angel");
    }

}
