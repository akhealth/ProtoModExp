package gov.alaska.m2.api.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name= "ar_application_for_aid")
public class Application {

	@Id
	@GenericGenerator(name = "app_num", strategy = "gov.alaska.m2.api.model.ApplicationIDGenerator")
	@GeneratedValue(generator = "app_num")  
	private String appNum;

	@Column(name="app_recvd_dt")
	private Date receivedDate;
	
	@Column(name="application_status_cd")
	private String appStatusCode;
	
	@JsonIgnore
	@OneToMany(
			mappedBy = "application", 
			cascade = CascadeType.ALL, 
			orphanRemoval = true, 
			fetch = FetchType.LAZY )
	private List<ApplicationIndividual> people = new ArrayList<ApplicationIndividual>();
	
	public Application() {}
	
	public List<ApplicationIndividual> getPeople() {
		return people;
	}

	public Date getReceivedDate() {
		return receivedDate;
	}

	public void setReceivedDate(Date receivedDate) {
		this.receivedDate = receivedDate;
	}

	public String getAppStatusCode() {
		return appStatusCode;
	}

	public void setAppStatusCode(String appStatusCode) {
		this.appStatusCode = appStatusCode;
	}

	public String getAppNum() {
		return appNum;
	}
	
	public void setAppNum(String appNum) {
		this.appNum = appNum;
	}
	
	public Person getHeadOfHousehold() {
		Person hoh = null;
		for (ApplicationIndividual person : people) {
			if (person.getHeadOfHousehold() == 'Y')
				hoh = person.getPerson();
		}
		return hoh;		
	}
	
	public void addPersonToApplication(Person person) {
		ApplicationIndividual newMap = new ApplicationIndividual(person, this);
		people.add(newMap);
		person.getApplications().add(newMap);
	}
	
	public void removePersonFromApplication(Person person) { 
		for(Iterator<ApplicationIndividual> iterator = people.iterator(); iterator.hasNext();) {
			ApplicationIndividual map = iterator.next();
			if (map.getApplication().equals(this) && map.getPerson().equals(person)) {
				iterator.remove();
				map.getPerson().getApplications().remove(map);
				map.setApplication(null);
				map.setPerson(null);
			}
		}
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(appNum, appStatusCode);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Application other = (Application) obj;
		return Objects.equals(appNum, other.appNum) && Objects.equals(appStatusCode, other.appStatusCode);
	}


	
	
	
}
