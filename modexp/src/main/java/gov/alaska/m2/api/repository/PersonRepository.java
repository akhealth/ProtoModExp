package gov.alaska.m2.api.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import gov.alaska.m2.api.model.Application;
import gov.alaska.m2.api.model.Person;

public interface PersonRepository extends JpaRepository<Person, Long>{

	Optional<Person> findBySsn(String ssn);
	List<Person> findByApplications_Application(Application application); 
}
