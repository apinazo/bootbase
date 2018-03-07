package es.apinazo.bootbase.business.persons;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * {@link PersonController} tests.
 *
 * Demo of tests using:
 * <ul>
 * <li><a href="https://github.com/rest-assured/rest-assured/wiki/Usage#bootstrapping-restassuredmockmvc">
 * RestAssured</a>.</li>
 * <li></li><a hfref="https://docs.spring.io/spring/docs/current/spring-framework-reference/testing.html#spring-mvc-test-framework">
 * Spring MVC test framework</a>.</li>
 * </ul>
 */
@RunWith(SpringRunner.class)
@WebMvcTest(PersonController.class)
public class PersonControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PersonService personService;


    @Before
    public void setup() {

        // Mock the personService dependency from the personController.
        given(personService.getById(1))
            .willReturn(new Person(1, "angel", "pinazo", Gender.MALE, null));
    }


    @Test
    public void findPersonByName_WhenReturned_ShouldBeMe() throws Exception{

        mvc
            .perform(get("/persons/{id}", 1)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.firstName", is("angel")));
    }


    @Test
    public void findPersonByName_WhenReturned_ShouldBeMe_WithRestAssured() {

        // Setup RestAssuredMockMvc to use the configured MockMvc,
        // all MockBeans and also the base URL for services.
        RestAssuredMockMvc.mockMvc(mvc);

        RestAssuredMockMvc.
            given().
            when().
                get("/persons/{id}", 1).
            then().
                statusCode(200).
                body("firstName", equalTo("angel"));
    }

}