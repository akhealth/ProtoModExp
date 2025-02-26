package gov.alaska.m2.web;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import gov.alaska.m2.api.model.Application;
import gov.alaska.m2.api.model.Person;
import gov.alaska.m2.api.repository.ApplicationRepository;
import gov.alaska.m2.api.repository.PersonRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class ClientSearchControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    PersonRepository personRepository;

    @MockitoBean
    ApplicationRepository applicationRepository;

    Application application = new Application();
    List<Application> applications = new ArrayList<Application>();

    Person person = new Person();
    Optional<Person> optionalPerson;

    HttpEntity<Application> request;

    @BeforeEach
    void setUpTest() {

        application.setAppNum("T0000001");
        application.setReceivedDate(new Date(2323223232L));
        application.setAppStatusCode("GOODSTATUS_CD");
        applications.add(application);

        person.setAries_id(1);
        person.setDob(new Date(1965, 01, 15));
        person.setFirst_name("Generic");
        person.setLast_name("Person");
        person.setSsn("000000001");
        optionalPerson = Optional.of(person);

        application.addPersonToApplication(person);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        request = new HttpEntity<>(application, headers);

    }

    @Test
    public void defaultClientSearchPageIsDisplayed() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .get("/clientsearch"))
                .andExpect(view().name("clientsearch"))
                .andExpect(status().isOk());
    }

    @Test
    public void defaultAppSearchPageCorrectlyRespondsToPostRequest() throws Exception {

        when(personRepository.findById(anyLong())).thenReturn(Optional.of(person));

        mvc.perform(MockMvcRequestBuilders
                .post("/clientsearch")
                .param("clientId", "1")
                .param("lastName", "Person")
                .param("firstName", "Generic")
                .param("submit", ""))
                .andExpect(model().attribute("lastName", "Person"))
                .andExpect(model().attribute("firstName", "Generic"))
                .andExpect(model().attribute("clientId", "1"))
                .andExpect(view().name("clientsearch"))
                .andExpect(status().isOk());
    }

    @Test
    public void applicationDetailsPageCorrectlyRespondsToGetRequest() throws Exception {

        mvc.perform(MockMvcRequestBuilders
                .get("/clientdetails")
                .param("search", ""))
                .andExpect(view().name("clientsearch"))
                .andExpect(status().isOk());
    }

    @Test
    public void applicationDetailsPageCorrectlyRespondsToPostRequest() throws Exception {

        mvc.perform(MockMvcRequestBuilders
                .post("/clientdetails")
                .param("edit", "")
                .param("appNum", "1")
                .param("lastName", "Person")
                .param("firstName", "Generic")
                .param("personData", "")
                .param("dateOfBirth", "10-12-1999"))
                .andExpect(view().name("clientdetails"))
                .andExpect(status().isOk());
    }
}
