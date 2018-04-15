package es.apinazo.bootbase.business.persons;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
@WebMvcTest(
    // Hook to this controller services without starting up a web server.
    value = PersonController.class,
    // Enable custom security in test. If not, Spring Security will be set upt with BASIC authentication.
    includeFilters = @ComponentScan.Filter(classes = EnableWebSecurity.class)
)
public class PersonControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PersonService personService;

    @MockBean
    private PersonRepository personRepository;

    @Before // Before each test.
    public void setup() {

        // Setup RestAssuredMockMvc to use the configured MockMvc,
        // all MockBeans and also the base URL for services.
        RestAssuredMockMvc.mockMvc(mvc);

        // Mock the personService dependency from the personController.
        given(personService.getById(1))
            .willReturn(new Person(1, "angel", "pinazo", Gender.MALE, null));

        // Mock the personRepository dependency from the personController.
        given(personRepository.save(ArgumentMatchers.any(Person.class)))
            .willReturn(new Person(1, "angel", "pinazo", Gender.MALE, null));
    }

    @Test
    public void whenFindPersonById_thenShouldBeMe() throws Exception{

        mvc
            .perform(
                MockMvcRequestBuilders
                    .get("/people/{id}", 1)
                    .with(user("user").password("password").roles("USER"))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.firstName", is("angel")))
            .andDo(print());
    }

    @Test
    public void whenFindPersonById_andNotAuthorizedRole_thenMustDenyAccess() throws Exception{

        mvc
            .perform(
                MockMvcRequestBuilders
                    .get("/people/{id}", 1)
                    .with(user("user").password("password").roles("INVALID_ROLE"))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().is4xxClientError())
            .andDo(print());
    }

    @Test
    public void whenFindPersonById_thenShouldBeMe_WithRestAssured() {

        RestAssuredMockMvc.
            given().
                auth().
                    with(user("user").password("password").roles("ADMIN")).
            when().
                get("/people/{id}", 1).
            then().
                statusCode(200).
                body("firstName", equalTo("angel")).
                time(lessThan(500L));
    }

    @Test
    public void whenCreatePerson_ShouldBeMe_WithRestAssured() {

        RestAssuredMockMvc
            .given()
                .auth()
                    .with(
                        user("admin").password("password").roles("ADMIN"),
                        // Anything but GET requires MockMvc to be aware of CSRF configuration
                        // since MockMvc will add a header with a csrf token.
                        csrf()
                    )
                .contentType("application/json")
                .body(new Person("angel", "pinazo", Gender.MALE)) // Auto converted into JSON.
            .when()
                .put("/people")
            .then()
                .statusCode(200)
                .body("firstName", equalTo("angel"));
    }

}