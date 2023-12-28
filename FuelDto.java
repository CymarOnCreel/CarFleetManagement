package application.dto;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name="fuel")
public class FuelDto {

	@Column(name="fuel_type")
	@Id
	private String fuelType;

	public FuelDto(String fuelType) {
		this.fuelType = fuelType;
	}
	
	public FuelDto() {
		
	}

	public String getFuelType() {
		return fuelType;
	}

	@Override
	public String toString() {
		return "FuelDto [fuelType=" + fuelType + "]";
	}
	
	public void updateFuelDto(String fuelType) {
		this.fuelType = fuelType;
	}
	
	public void deleteFuelDto() {
		int enabled = 0;
		LocalDate updatedAt = LocalDate.now();
	}
	
	
	
	
}
