package gov.alaska.m2.api.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "dc_indv")
public class Person {

	@Id
	@Column(name = "indv_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long aries_id;

	@Column(name = "ssn")
	private String ssn;

	@Column(name = "first_name")
	private String first_name;

	@Column(name = "last_name")
	private String last_name;

	@Column(name = "dob_dt")
	private Date dob;

	@JsonIgnore
	@OneToMany(mappedBy = "person", fetch = FetchType.LAZY)
	private List<ApplicationIndividual> applications = new ArrayList<ApplicationIndividual>();

	public Person() {
	}

	public List<ApplicationIndividual> getApplications() {
		return applications;
	}

	public Person(String ssn, String first_name, String last_name, Date dob) {
		this.ssn = ssn;
		this.first_name = first_name;
		this.last_name = last_name;
		this.dob = dob;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public long getAries_id() {
		return aries_id;
	}

	public void setAries_id(long ariesId) {
		this.aries_id = ariesId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(aries_id, ssn);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Person other = (Person) obj;
		return aries_id == other.aries_id && Objects.equals(ssn, other.ssn);
	}

}
