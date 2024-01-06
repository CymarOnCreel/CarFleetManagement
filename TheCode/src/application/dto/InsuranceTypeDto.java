package application.dto;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="insurance_type")
public class InsuranceTypeDto {

	@Column(name="name_insurance_type")
	@Id
	private String nameInsuranceType;

	public InsuranceTypeDto(String nameInsuranceType) {
		this.nameInsuranceType = nameInsuranceType;
	}

	public InsuranceTypeDto() {
		
	}

	public String getNameInsuranceType() {
		return nameInsuranceType;
	}

	@Override
	public String toString() {
		return nameInsuranceType;
	}
	
	public void updateInsuranceTypeDto(String nameInsuranceType) {
		this.nameInsuranceType = nameInsuranceType;
	}
	
	public void deleteInsuranceTypeDto() {
		int enabled = 0;
		LocalDate updatedAt = LocalDate.now();
	}
}