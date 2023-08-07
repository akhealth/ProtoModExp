package gov.alaska.m2.api.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
@Table(name = "ar_app_indv")
public class ApplicationIndividual {

		@JsonIgnore
		@EmbeddedId
		private ApplicationIndividualKey id;
		
		@JsonIgnore
		@ManyToOne
		@MapsId("indv_id")
		@JoinColumn(name ="indv_id")
		private Person person;
		
		@JsonIgnore
		@ManyToOne
		@MapsId("app_num")
		@JoinColumn(name ="app_num")
		private Application application;
		
		@Column(name = "head_of_household_sw")
		private char headOfHousehold;
		
		// need a default constructor for JPA magic to work
		private ApplicationIndividual() {}
		
		public ApplicationIndividual(Person person, Application application) {
			this.person = person;
			this.application = application;
			this.headOfHousehold = 'N';
			this.id = new ApplicationIndividualKey(application.getAppNum(), person.getAries_id());
		}

		public ApplicationIndividualKey getId() {
			return id;
		}

		public void setId(ApplicationIndividualKey id) {
			this.id = id;
		}

		public Person getPerson() {
			return person;
		}

		public void setPerson(Person person) {
			this.person = person;
		}

		public Application getApplication() {
			return application;
		}

		public void setApplication(Application application) {
			this.application = application;
		}

		public char getHeadOfHousehold() {
			return headOfHousehold;
		}

		public void setHeadOfHousehold(char headOfHousehold) {
			this.headOfHousehold = headOfHousehold;
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
			ApplicationIndividual other = (ApplicationIndividual) obj;
			return Objects.equals(application, other.application) && Objects.equals(person, other.person);
		}

		
	
}



