package application.dto;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "transmission")
public class TransmissionDto {

	@Column(name = "transmission_type")
	@Id
	private String transmissionType;

	public TransmissionDto(String transmissionType) {
		this.transmissionType = transmissionType;
	}

	public TransmissionDto() {
		
	}

	public String getTransmissionType() {
		return transmissionType;
	}

	@Override
	public String toString() {
		return "TransmissionDto [nameCategory=" + transmissionType + "]";
	}
	
	public void updateTransmissionDto(String transmissionType) {
		this.transmissionType = transmissionType;
	}
	
	public void deleteTransmissionDto() {
		int enabled = 0;
		LocalDate updatedAt = LocalDate.now();
	}
	
}
