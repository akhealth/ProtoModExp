package gov.alaska.m2.api.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name="dc_cases")
public class Case {

	@Id
	@Column(name="case_num")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long caseNum;
	
	@Column(name="case_status_cd")
	private String caseStatusCode;
	
	@Column(name="case_mode_cd")
	private String caseModeCode;
	
	public String getCaseStatusCode() {
		return caseStatusCode;
	}

	public void setCaseStatusCode(String caseStatusCode) {
		this.caseStatusCode = caseStatusCode;
	}

	public String getCaseModeCode() {
		return caseModeCode;
	}

	public void setCaseModeCode(String caseModeCode) {
		this.caseModeCode = caseModeCode;
	}

	public Long getCaseNum() {
		return caseNum;
	}


}
