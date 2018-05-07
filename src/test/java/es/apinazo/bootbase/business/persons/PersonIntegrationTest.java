package es.apinazo.bootbase.business.persons;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNotNull;


/**
 * Person integration tests.
 *
 * Will start a full Spring Boot web app and will test {@link PersonController}
 * services end to end, using no mocks but the real beans.
 *
 * Some tests will be using {@link TestRestTemplate}, some other will use {@link RestAssured}.
 *
 * The test data will be loaded from {@link PersonTestDataLoader}.
 *
 * NOTE: This test is not transactional and thereupon there will be no DB rollback !!!
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// As this test will modify the DB in a no transactional way and so
// it can't roll back, use an exclusive DB for it.
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class PersonIntegrationTest {

    // The random generated port.
    @LocalServerPort
    int serverPort;

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

        findAll_WithJsonPath();

        findAll_ParsingRawJson();

        findAll_WithObjects();
    }


    // Using the raw JSON response and JSON-PATH.
    // https://github.com/json-path/JsonPath
    private void findAll_WithJsonPath() {

        // Get the response as a raw JSON.
        ResponseEntity<String> response =
            testRestTemplate
                .getForEntity("/people", String.class, 1);

        Integer length = JsonPath.read(response.getBody(), "$.length()");

        assertThat(length).as("There can be only one!").isEqualTo(1);
    }


    // Parsing the raw JSON into domain objects directly from the response.
    private void findAll_ParsingRawJson() throws Exception {

        // Get the response as a raw JSON.
        ResponseEntity<String> response =
                testRestTemplate
                        .getForEntity("/people", String.class, 1);

        // Parse the raw JSON into objects.
        // Using TypeReference is needed for collections.
        ObjectMapper mapper = new ObjectMapper();
        List<Person> people =
            mapper.readValue(response.getBody(), new TypeReference<List<Person>>(){});

        assertThat(people.size()).as("There can be only one!").isEqualTo(1);
    }


    // Getting the response directly into objects.
    private void findAll_WithObjects() {

        ResponseEntity<List<Person>> response2 =
            testRestTemplate
                .exchange(
                    "/people",
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
                .getForEntity("/people/{id}", Person.class, 1);

        checkThePersonIsMe(responseById);
    }


    @Test
    public void findPersonByName_WhenReturned_ShouldBeMe() throws Exception {

        ResponseEntity<Person> response =
            testRestTemplate
                .getForEntity("/people/firstName/{firstName}", Person.class, "angel");

        checkThePersonIsMe(response);
    }


    // Example of using RestAssured instead of TestRestTemplate.
    @Test
    public void findPersonByName_WithRestAssured() {

        RestAssured.defaultParser = Parser.JSON; // To parse the response body.
        RestAssured.port = serverPort; // Because we're using a random port.

        given().
            pathParam("firstName", "angel").
        when().
            get("/people/firstName/{firstName}").
        then().
            body("firstName", equalTo("angel"));
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
