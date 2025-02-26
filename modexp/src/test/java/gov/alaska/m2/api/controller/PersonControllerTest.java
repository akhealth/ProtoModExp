package gov.alaska.m2.api.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import gov.alaska.m2.api.model.Application;
import gov.alaska.m2.api.model.Person;
import gov.alaska.m2.api.repository.ApplicationRepository;
import gov.alaska.m2.api.repository.PersonRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class PersonControllerTest {

    @LocalServerPort
    private int port;

    @MockitoBean
    private ApplicationRepository applicationRepository;

    @MockitoBean
    private PersonRepository personRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    Application application = new Application();
    List<Application> applications = new ArrayList<Application>();

    Person person = new Person();
    Optional<Person> optionalPerson;
    Optional<Application> optionalApplication;

    HttpEntity<Application> request;

    List<Person> persons = new ArrayList<Person>();

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
        optionalApplication = Optional.of(application);

        application.addPersonToApplication(person);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        request = new HttpEntity<>(application, headers);

    }

    @Test
    void getAllApplicationsReturnsNoContentWhenEmpty() {

        when(personRepository.findAll()).thenReturn(persons);
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:" + port + "/api/person/",
                String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    void getAllApplicationsSuccessfullyReturns() {
        persons.add(person);
        when(personRepository.findAll()).thenReturn(persons);
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:" + port + "/api/person/",
                String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertEquals(response.getBody(),
                "[{\"aries_id\":1,\"ssn\":\"000000001\",\"first_name\":\"Generic\",\"last_name\":\"Person\",\"dob\":\"3865-02-15T09:00:00.000+00:00\"}]");
    }

    @Test
    void getAllPersonsWithErrorReturnsNoContentResponse() {
        when(personRepository.findAll()).thenThrow(new RuntimeException("Test Error"));
        ResponseEntity<Person> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/api/person/",
                Person.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    void postRequestReturnsSuccessfulCreation() {
        ResponseEntity<Person> response = restTemplate.exchange("http://localhost:" + port + "/api/person/",
                HttpMethod.POST, request, Person.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    void getPersonsByIdSuccessfullyReturns() {
        when(personRepository.findById(1L)).thenReturn(Optional.of(person));
        ResponseEntity<String> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/api/person/1",
                String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertEquals(response.getBody(),
                "{\"aries_id\":1,\"ssn\":\"000000001\",\"first_name\":\"Generic\",\"last_name\":\"Person\",\"dob\":\"3865-02-15T09:00:00.000+00:00\"}");
    }

    @Test
    void getPersonsByIdReturnsNotFound() {
        when(personRepository.findById(1L)).thenReturn(Optional.empty());
        ResponseEntity<String> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/api/person/1",
                String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void putRequestReturnsSuccessfulCreated() {
        ResponseEntity<Person> response = restTemplate.exchange(
                "http://localhost:" + port + "/api/person/1",
                HttpMethod.PUT, request, Person.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    void putRequestReturnsSuccessfulUpdatedCode() {
        when(personRepository.findById(1L)).thenReturn(Optional.of(person));
        ResponseEntity<Person> response = restTemplate.exchange(
                "http://localhost:" + port + "/api/person/1",
                HttpMethod.PUT, request, Person.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void deleteApplicationsByIdReturnsOk() {
        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:" + port + "/api/person/1", HttpMethod.DELETE, null, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("Person with id:1 has been deleted successfully");
    }

    @Test
    void getPersonBySSNSuccessfullyReturns() {
        when(personRepository.findBySsn("000000001")).thenReturn(optionalPerson);
        ResponseEntity<String> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/api/person/ssn/000000001",
                String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertEquals(response.getBody(),
                "{\"aries_id\":1,\"ssn\":\"000000001\",\"first_name\":\"Generic\",\"last_name\":\"Person\",\"dob\":\"3865-02-15T09:00:00.000+00:00\"}");
    }

    @Test
    void getPersonBySSNReturnsNotFound() {
        when(personRepository.findBySsn("000000001")).thenReturn(Optional.empty());
        ResponseEntity<String> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/api/person/ssn/000000001",
                String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void getApplicationByIdSuccessfullyReturns() {
        persons.add(person);
        when(applicationRepository.findById("T0000001")).thenReturn(optionalApplication);
        when(personRepository.findByApplications_Application(optionalApplication.get())).thenReturn(persons);
        ResponseEntity<String> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/api/person/application/T0000001",
                String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertEquals(response.getBody(),
                "[{\"aries_id\":1,\"ssn\":\"000000001\",\"first_name\":\"Generic\",\"last_name\":\"Person\",\"dob\":\"3865-02-15T09:00:00.000+00:00\"}]");
    }

    @Test
    void getApplicationByIdReturnsNotFound() {
        when(personRepository.findById(1L)).thenReturn(Optional.empty());
        ResponseEntity<String> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/api/person/application/T0000001",
                String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void getHeadOfHouseholdReturnsNotFound() {
        when(applicationRepository.findById("T0000001")).thenReturn(Optional.empty());
        ResponseEntity<String> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/api/person/application/T0000001/hoh",
                String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void nullHeadOfHouseholdReturnsNotFound() {
        when(applicationRepository.findById("T0000001")).thenReturn(Optional.of(application));
        ResponseEntity<String> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/api/person/application/T0000001/hoh",
                String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
