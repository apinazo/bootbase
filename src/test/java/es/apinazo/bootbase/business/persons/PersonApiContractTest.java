package es.apinazo.bootbase.business.persons;


import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.mockito.BDDMockito.given;

/**
 * Validation of the {@link Person} API contract using {@link io.restassured.RestAssured}.
 *
 * <a href="https://blog.jayway.com/2013/12/10/json-schema-validation-with-rest-assured/">Some examples.</a>
 */
@RunWith(SpringRunner.class)
@WebMvcTest(PersonController.class)
public class PersonApiContractTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PersonService personService;

    @Before
    public void setup() {

        // Mock the personService dependency from the personController.
        given(personService.getById(1))
            .willReturn(new Person(1, "angel", "pinazo", Gender.MALE, null));

        // Setup RestAssuredMockMvc to use the configured MockMvc,
        // all MockBeans and also the base URL for services.
        RestAssuredMockMvc.mockMvc(mvc);
    }

    @Test
    public void findAll_MatchSchema() {

        RestAssuredMockMvc.
            given().
            when().
                get("/persons").
            then().
                statusCode(200).
                body(matchesJsonSchemaInClasspath("api/persons/person-list.json"));
    }

    @Test
    public void findById_MatchSchema() {

        RestAssuredMockMvc.
            given().
            when().
                get("/persons/{id}", 1).
            then().
                statusCode(200).
                body(matchesJsonSchemaInClasspath("api/persons/person-detail.json"));
    }

}
