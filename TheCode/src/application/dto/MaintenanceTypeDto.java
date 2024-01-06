package application.dto;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="maintenance_type")
public class MaintenanceTypeDto {

	@Column(name="name_maintenance_type")
	@Id
	private String nameMaintenanceType;

	public MaintenanceTypeDto(String nameMaintenanceType) {
		this.nameMaintenanceType = nameMaintenanceType;
	}

	public MaintenanceTypeDto() {
		
	}

	public String getNameMaintenanceType() {
		return nameMaintenanceType;
	}

	@Override
	public String toString() {
		return nameMaintenanceType;
	}
	
	public void updateMaintenanceTypeDto(String nameMaintenanceType) {
		this.nameMaintenanceType = nameMaintenanceType;
	}
	
	public void deleteMaintenanceTypeDto() {
		int enabled = 0;
		LocalDate updatedAt = LocalDate.now();
	}	
}
