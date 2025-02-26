package gov.alaska.m2.api.model;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.*;

// ID class to represent the composite key needed for applications and person mapping.
@Embeddable
public class ApplicationIndividualKey implements Serializable {

	// Name needs to match the fields in the mapping class.
	// Type needs to match ID of the associated type.

	@Column(name = "app_num")
	private String app_num;

	@Column(name = "indv_id")
	private long indv_id;

	public ApplicationIndividualKey() {
	}

	public ApplicationIndividualKey(String application, long person) {
		this.app_num = application;
		this.indv_id = person;
	}

	public String getApp_num() {
		return app_num;
	}

	public void setApp_num(String application) {
		this.app_num = application;
	}

	public long getIndv_id() {
		return indv_id;
	}

	public void setIndv_id(long person) {
		this.indv_id = person;
	}

	@Override
	public int hashCode() {
		return Objects.hash(app_num, indv_id);
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
		return Objects.equals(app_num, other.app_num) && Objects.equals(indv_id, other.indv_id);
	}

}
