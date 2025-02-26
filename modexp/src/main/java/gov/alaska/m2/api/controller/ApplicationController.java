package gov.alaska.m2.api.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

// @CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api/application")
public class ApplicationController {

	@Autowired
	ApplicationRepository applicationRepository;

	@Autowired
	PersonRepository personRepository;

	@Operation(summary = "Get All Applications", description = "Get All Applications")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "successful operation")
	})
	@GetMapping("/")
	public ResponseEntity<List<Application>> getAllApplications() {
		try {
			List<Application> applications = new ArrayList<Application>();
			applicationRepository.findAll().forEach(applications::add);

			if (applications.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(applications, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Operation(summary = "Create Application", description = "Create Application")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "successful operation")
	})
	@PostMapping("/")
	public ResponseEntity<Object> createApplication(@Valid @RequestBody Application application) {
		applicationRepository.save(application);
		return new ResponseEntity<>(application, HttpStatus.CREATED);
	}

	@Operation(summary = "Get application by ID", description = "Get application by ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "successful operation")
	})
	@GetMapping("/{id}")
	public ResponseEntity<Application> getAppById(@PathVariable("id") String id) {
		Optional<Application> applicationData = applicationRepository.findById(id);

		if (applicationData.isPresent()) {
			return new ResponseEntity<>(applicationData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@Operation(summary = "Update Application", description = "Update application. If application doesn't exist, create it.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "successful operation")
	})
	@PutMapping("/{id}")
	public ResponseEntity<Object> updateApplication(@PathVariable(value = "id") String appNum,
			@Valid @RequestBody Application appDetails) {
		Optional<Application> app = applicationRepository.findById(appNum);

		if (app.isPresent()) {
			Application appData = app.get();
			appData.setReceivedDate(appDetails.getReceivedDate());
			appData.setAppStatusCode(appDetails.getAppStatusCode());
			final Application updatedApp = applicationRepository.save(appData);
			return new ResponseEntity<>(updatedApp, HttpStatus.OK);
		} else {
			applicationRepository.save(appDetails);
			return new ResponseEntity<>(appDetails, HttpStatus.CREATED);
		}
	}

	@Operation(summary = "Delete application", description = "Delete application by ID.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "successful operation")
	})
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteApplication(@PathVariable(value = "id") String appId) {
		applicationRepository.deleteById(appId);
		return new ResponseEntity<>("Application with id:" + appId + " has been deleted successfully", HttpStatus.OK);
	}

	@Operation(summary = "Find all applications for person", description = "Find all the applications associated with a person id.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "successful operation")
	})
	@GetMapping("/person/{id}")
	public ResponseEntity<Object> getApplicationsForPerson(@PathVariable("id") long id) {
		Optional<Person> personData = personRepository.findById(id);
		if (personData.isEmpty()) {
			return new ResponseEntity<>("Person not found", HttpStatus.NOT_FOUND);
		}

		List<Application> apps = applicationRepository.findByPeople_Person(personData.get());
		return new ResponseEntity<>(apps, HttpStatus.OK);
	}

	@Operation(summary = "Add Person to Application", description = "Add Person to Application.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "successful operation")
	})
	@PutMapping("/{appId}/person/{personId}")
	public ResponseEntity<Object> addPersonToApplication(@PathVariable(value = "appId") String appId,
			@PathVariable(value = "personId") long personId) {
		Optional<Application> application = applicationRepository.findById(appId);
		if (application.isEmpty()) {
			return new ResponseEntity<>("The application was not found.", HttpStatus.NOT_FOUND);
		}
		Application appData = application.get();

		Optional<Person> person = personRepository.findById(personId);

		if (person.isEmpty()) {
			return new ResponseEntity<>("The person was not found.", HttpStatus.NOT_FOUND);
		}
		Person personData = person.get();

		for (ApplicationIndividual map : appData.getPeople()) {
			if (map.getPerson().getAries_id() == personData.getAries_id())
				return new ResponseEntity<>("Person is already associated with this application", HttpStatus.OK);
		}

		appData.addPersonToApplication(personData);
		applicationRepository.save(appData);

		return new ResponseEntity<>(appData, HttpStatus.OK);
	}

	@Operation(summary = "Remove Person from Application", description = "Remove Person from Application")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "successful operation")
	})
	@DeleteMapping("/{appId}/person/{personId}")
	public ResponseEntity<Object> removePersonFromApplication(@PathVariable(value = "appId") String appId,
			@PathVariable(value = "personId") long personId) {
		Optional<Application> application = applicationRepository.findById(appId);
		if (application.isEmpty()) {
			return new ResponseEntity<>("The application was not found.", HttpStatus.NOT_FOUND);
		}
		Application appData = application.get();

		Optional<Person> person = personRepository.findById(personId);
		if (person.isEmpty()) {
			return new ResponseEntity<>("The person was not found.", HttpStatus.NOT_FOUND);
		}
		Person personData = person.get();

		appData.removePersonFromApplication(personData);
		applicationRepository.save(appData);

		return new ResponseEntity<>(appData, HttpStatus.OK);
	}
}
