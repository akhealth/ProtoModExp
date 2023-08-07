package gov.alaska.m2.api.model;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.*;

// ID class to represent the composite key needed for applications and person mapping.
@Embeddable
public class ApplicationIndividualKey implements Serializable {
	
	// Name needs to match the fields in the mapping class. 
	//Type needs to match ID of the associated type.
	
	@Column(name = "app_num")
	private String application;
	
	@Column(name = "indv_id")
	private long person;
	
	public ApplicationIndividualKey() {}
	
	public ApplicationIndividualKey(String application, long person) {
		this.application = application;
		this.person = person;
	}
	
	public String getApplication() {
		return application;
	}
	
	public void setApplication(String application) {
		this.application = application;
	}
	
	public long getPerson() {
		return person;
	}
	
	public void setPerson(long person) {
		this.person = person;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(application, person);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ApplicationIndividualKey other = (ApplicationIndividualKey) obj;
		return Objects.equals(application, other.application) && Objects.equals(person, other.person);
	}
	
	
}
