package es.apinazo.bootbase.business.persons;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


// https://docs.spring.io/spring/docs/current/spring-framework-reference/testing.html#spring-mvc-test-framework
@RunWith(SpringRunner.class)
@WebMvcTest(PersonController.class)
public class PersonControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PersonService personService;

    @Test
    public void findPersonByName_WhenReturned_ShouldBeMe() throws Exception{

        // Mock the personService dependency from the personController.
        given(personService.getById(1))
            .willReturn(new Person(1, "angel", "pinazo", Gender.MALE, null));

        this.mvc
            .perform(get("/persons/{id}", 1)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.firstName", Matchers.is("angel")));
    }

}