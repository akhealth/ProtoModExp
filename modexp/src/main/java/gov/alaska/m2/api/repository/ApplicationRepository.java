package gov.alaska.m2.api.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import gov.alaska.m2.api.model.Application;
import gov.alaska.m2.api.model.Person;


public interface ApplicationRepository extends JpaRepository<Application, String>{
	List<Application> findByPeople_Person(Person person); 

}
