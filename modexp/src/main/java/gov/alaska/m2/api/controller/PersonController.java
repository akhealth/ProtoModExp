package gov.alaska.m2.api.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gov.alaska.m2.api.model.Application;
import gov.alaska.m2.api.model.ApplicationIndividual;
import gov.alaska.m2.api.model.Person;
import gov.alaska.m2.api.repository.ApplicationRepository;
import gov.alaska.m2.api.repository.PersonRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api/person")
public class PersonController {

	@Autowired
	PersonRepository personRepository;

	@Autowired
	ApplicationRepository applicationRepository;

	@Operation(summary = "Get all Persons", description = "Get all people in the db")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "500", description = "Exception thrown."),
			@ApiResponse(responseCode = "204", description = "No people in the DB"),
			@ApiResponse(responseCode = "200", description = "successful operation")// , content= {@Content(mediaType =
																					// "application/json", schema =
																					// @Schema(implementation =
																					// Person.class))})
	})
	@GetMapping("/")
	public ResponseEntity<List<Person>> getAllPersons() {
		try {
			List<Person> persons = new ArrayList<Person>();
			personRepository.findAll().forEach(persons::add);

			if (persons.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(persons, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Operation(summary = "Create Person", description = "Create new person.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "successful operation")
	})
	@PostMapping("/")
	public ResponseEntity<Object> createPerson(@Valid @RequestBody Person person) {
		personRepository.save(person);
		return new ResponseEntity<>(person, HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> getPersonById(@PathVariable("id") long id) {
		Optional<Person> personData = personRepository.findById(id);

		if (personData.isPresent()) {
			return new ResponseEntity<>(personData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Person does not exist.", HttpStatus.NOT_FOUND);
		}
	}

	@Operation(summary = "Update Person", description = "Update person. If person doesn't exist, create it.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "successful operation")
	})
	@PutMapping("/{id}")
	public ResponseEntity<Object> updatePerson(@PathVariable(value = "id") long personId,
			@Valid @RequestBody Person personDetails) {
		Optional<Person> person = personRepository.findById(personId);

		if (person.isPresent()) {
			Person personData = person.get();
			personData.setDob(personDetails.getDob());
			personData.setFirst_name(personDetails.getFirst_name());
			personData.setLast_name(personDetails.getLast_name());
			personData.setSsn(personDetails.getSsn());
			final Person updatedPerson = personRepository.save(personData);
			return new ResponseEntity<>(updatedPerson, HttpStatus.OK);
		} else {
			personRepository.save(personDetails);
			return new ResponseEntity<>(personDetails, HttpStatus.OK);
		}
	}

	@Operation(summary = "Delete Person", description = "Delete Person ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "successful operation")
	})
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deletePerson(@PathVariable(value = "id") long personId) {
		personRepository.deleteById(personId);
		return new ResponseEntity<>("Person with id:" + personId + " has been deleted successfully", HttpStatus.OK);
	}

	@Operation(summary = "Get Person by SSN", description = "Find a person by their SSN")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "successful operation")
	})
	@GetMapping("/ssn/{ssn}")
	public ResponseEntity<Object> getPersonBySSN(@PathVariable("ssn") String ssn) {
		Optional<Person> personData = personRepository.findBySsn(ssn);

		if (personData.isPresent()) {
			return new ResponseEntity<>(personData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@Operation(summary = "Get People by Application", description = "Return all people on a given application.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "successful operation")
	})
	@GetMapping("/application/{appNum}")
	public ResponseEntity<Object> getPeopleByApplication(@PathVariable("appNum") String appNum) {

		Optional<Application> appData = applicationRepository.findById(appNum);
		if (appData.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		List<Person> people = personRepository.findByApplications_Application(appData.get());

		return new ResponseEntity<>(people, HttpStatus.OK);
	}

	@Operation(summary = "Get Head of Household", description = "Get Head of Household Person on Application")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "successful operation")
	})
	@GetMapping("/application/{appNum}/hoh")
	public ResponseEntity<Object> getHOHOnApplication(@PathVariable("appNum") String appNum) {

		Optional<Application> appData = applicationRepository.findById(appNum);
		if (appData.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		Person headOfHousehold = null;
		for (ApplicationIndividual map : appData.get().getPeople()) {
			if (map.getHeadOfHousehold() == 'Y') {
				headOfHousehold = map.getPerson();
			}
		}

		if (headOfHousehold != null) {
			return new ResponseEntity<>(headOfHousehold, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}
