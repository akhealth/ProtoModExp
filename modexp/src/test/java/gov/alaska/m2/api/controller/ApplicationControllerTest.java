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
public class ApplicationControllerTest {

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
    void getAllApplicationsSuccessfullyReturns() {
        when(applicationRepository.findAll()).thenReturn(applications);
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:" + port + "/api/application/",
                String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertEquals(response.getBody(),
                "[{\"appNum\":\"T0000001\",\"receivedDate\":\"1970-01-27T21:20:23.232+00:00\",\"appStatusCode\":\"GOODSTATUS_CD\",\"headOfHousehold\":null}]");
    }

    @Test
    void emptyApplicationReturnsNoContentResponse() {
        List<Application> emptyApplications = new ArrayList<Application>();
        when(applicationRepository.findAll()).thenReturn(emptyApplications);
        ResponseEntity<Application> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/api/application/",
                Application.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    void getAllApplicationsWithErrorReturnsNoContentResponse() {
        when(applicationRepository.findAll()).thenThrow(new RuntimeException("Test Error"));
        ResponseEntity<Application> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/api/application/",
                Application.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    void runtimeErrorReturnsServerErrorStatus() {
        when(applicationRepository.findAll()).thenThrow(new RuntimeException());
        ResponseEntity<Application> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/api/application/",
                Application.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    void postRequestReturnsSuccessfulCreation() {
        ResponseEntity<Application> response = restTemplate.exchange("http://localhost:" + port + "/api/application/",
                HttpMethod.POST, request, Application.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    void getApplicationsByIdSuccessfullyReturns() {
        when(applicationRepository.findById("T0000001")).thenReturn(Optional.of(application));
        ResponseEntity<String> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/api/application/T0000001",
                String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertEquals(response.getBody(),
                "{\"appNum\":\"T0000001\",\"receivedDate\":\"1970-01-27T21:20:23.232+00:00\",\"appStatusCode\":\"GOODSTATUS_CD\",\"headOfHousehold\":null}");
    }

    @Test
    void getApplicationsByIdReturnsNotFound() {
        when(applicationRepository.findById("T0000001")).thenReturn(Optional.empty());
        ResponseEntity<String> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/api/application/T0000001",
                String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void putRequestReturnsSuccessfulCreated() {
        ResponseEntity<Application> response = restTemplate.exchange(
                "http://localhost:" + port + "/api/application/T0000001",
                HttpMethod.PUT, request, Application.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    void putRequestReturnsSuccessfulUpdatedCode() {
        when(applicationRepository.findById("T0000001")).thenReturn(Optional.of(application));
        ResponseEntity<Application> response = restTemplate.exchange(
                "http://localhost:" + port + "/api/application/T0000001",
                HttpMethod.PUT, request, Application.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void deleteApplicationsByIdReturnsOk() {
        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:" + port + "/api/application/T0000001", HttpMethod.DELETE, null, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("Application with id:T0000001 has been deleted successfully");
    }

    @Test
    void getPersonByIdSuccessfullyReturns() {
        when(personRepository.findById(1L)).thenReturn(optionalPerson);
        when(applicationRepository.findByPeople_Person(optionalPerson.get())).thenReturn(applications);
        ResponseEntity<String> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/api/application/person/1",
                String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertEquals(response.getBody(),
                "[{\"appNum\":\"T0000001\",\"receivedDate\":\"1970-01-27T21:20:23.232+00:00\",\"appStatusCode\":\"GOODSTATUS_CD\",\"headOfHousehold\":null}]");
    }

    @Test
    void getPersonByIdReturnsNotFound() {
        when(personRepository.findById(1L)).thenReturn(Optional.empty());
        ResponseEntity<String> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/api/application/person/1",
                String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertEquals(response.getBody(), "Person not found");
    }

    @Test
    void emptyApplicationPutRequestForReturnsNotFound() {
        when(applicationRepository.findById("T0000001")).thenReturn(Optional.empty());
        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:" + port + "/api/application/T0000001/person/1",
                HttpMethod.PUT, request, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertEquals(response.getBody(), "The application was not found.");
    }

    @Test
    void emptyPersonPutRequestForReturnsNotFound() {
        when(applicationRepository.findById("T0000001")).thenReturn(Optional.of(application));
        when(personRepository.findById(1L)).thenReturn(Optional.empty());
        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:" + port + "/api/application/T0000001/person/1",
                HttpMethod.PUT, request, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertEquals(response.getBody(), "The person was not found.");
    }

    @Test
    void addPersonRequestReturnsOkPersonAlreadyExists() {
        application.addPersonToApplication(person);
        when(applicationRepository.findById("T0000001")).thenReturn(Optional.of(application));
        when(personRepository.findById(1L)).thenReturn(Optional.of(person));
        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:" + port + "/api/application/T0000001/person/1",
                HttpMethod.PUT, request, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertEquals(response.getBody(),
                "Person is already associated with this application");
    }

    @Test
    void addPersonRequestReturnsOk() {
        Person person2 = new Person();
        when(applicationRepository.findById("T0000001")).thenReturn(Optional.of(application));
        when(personRepository.findById(1L)).thenReturn(Optional.of(person2));
        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:" + port + "/api/application/T0000001/person/1",
                HttpMethod.PUT, request, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertEquals(response.getBody(),
                "{\"appNum\":\"T0000001\",\"receivedDate\":\"1970-01-27T21:20:23.232+00:00\",\"appStatusCode\":\"GOODSTATUS_CD\",\"headOfHousehold\":null}");
    }

    @Test
    void removePersonFromApplicationReturnsNotFoundApplicationEmpty() {
        when(applicationRepository.findById("T0000001")).thenReturn(Optional.empty());
        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:" + port + "/api/application/T0000001/person/1", HttpMethod.DELETE, null,
                String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).contains("The application was not found.");
    }

    @Test
    void removePersonFromApplicationReturnsNotFoundPersonEmpy() {
        when(applicationRepository.findById("T0000001")).thenReturn(Optional.of(application));
        when(personRepository.findById(1L)).thenReturn(Optional.empty());
        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:" + port + "/api/application/T0000001/person/1", HttpMethod.DELETE, null,
                String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).contains("The person was not found.");
    }

    @Test
    void removePersonFromApplicationReturnsOk() {
        when(applicationRepository.findById("T0000001")).thenReturn(Optional.of(application));
        when(personRepository.findById(1L)).thenReturn(Optional.of(person));
        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:" + port + "/api/application/T0000001/person/1", HttpMethod.DELETE, null,
                String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertEquals(response.getBody(),
                "{\"appNum\":\"T0000001\",\"receivedDate\":\"1970-01-27T21:20:23.232+00:00\",\"appStatusCode\":\"GOODSTATUS_CD\",\"headOfHousehold\":null}");
    }
}