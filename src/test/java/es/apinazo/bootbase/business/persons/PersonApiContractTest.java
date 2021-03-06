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

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

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

    @MockBean
    private PersonRepository personRepository;

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
    public void findAll_shouldMatchSchema() {

        RestAssuredMockMvc.
            given().
                auth().
                    with(user("user").password("password").roles("USER")).
            when().
                get("/people").
            then().
                statusCode(200).
                body(matchesJsonSchemaInClasspath("api/people/person-list.json"));
    }

    @Test
    public void findById_shouldMatchSchema() {

        RestAssuredMockMvc.
            given().
                auth().
                    with(user("user").password("password").roles("USER")).
            when().
                get("/people/{id}", 1).
            then().
                statusCode(200).
                body(matchesJsonSchemaInClasspath("api/people/person-detail.json"));
    }

}
