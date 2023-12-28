package application.dto;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="maintenance")
public class MaintenanceDto {

	@Column(name="id_maintenance")
	@Id
	private int idMaintenance;
	
	@Column(name="license_plate")
	private String licensePlate;
	
	@Column(name="maintenance_type")
	private String maintenanceType;
	
	@Column(name="service_company")
	private String serviceCompany;
	
	@Column(name="date")
	private LocalDate date;
	
	@Column(name="mileage")
	private int mileage;
	
	@Column(name="description")
	private String description;
	
	@Column(name="amount")
	private int amount;
	
	@Column(name="created_at")
	private LocalDate createdAt;
	
	@Column(name="updated_at")
	private LocalDate updatedAt;

	public MaintenanceDto(int idMaintenance, String licensePlate, String maintenanceType, String serviceCompany,
			LocalDate date, int mileage, String description, int amount, LocalDate createdAt, LocalDate updatedAt) {
		this.idMaintenance = idMaintenance;
		this.licensePlate = licensePlate;
		this.maintenanceType = maintenanceType;
		this.serviceCompany = serviceCompany;
		this.date = date;
		this.mileage = mileage;
		this.description = description;
		this.amount = amount;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public MaintenanceDto() {
		
	}

	public int getIdMaintenance() {
		return idMaintenance;
	}

	public String getLicensePlate() {
		return licensePlate;
	}

	public String getMaintenanceType() {
		return maintenanceType;
	}

	public String getServiceCompany() {
		return serviceCompany;
	}

	public LocalDate getDate() {
		return date;
	}

	public int getMileage() {
		return mileage;
	}

	public String getDescription() {
		return description;
	}

	public int getAmount() {
		return amount;
	}

	public LocalDate getCreatedAt() {
		return createdAt;
	}

	public LocalDate getUpdatedAt() {
		return updatedAt;
	}

	@Override
	public String toString() {
		return "MaintenanceDto [idMaintenance=" + idMaintenance + ", licensePlate=" + licensePlate
				+ ", maintenanceType=" + maintenanceType + ", serviceCompany=" + serviceCompany + ", date=" + date
				+ ", mileage=" + mileage + ", description=" + description + ", amount=" + amount + ", createdAt="
				+ createdAt + ", updatedAt=" + updatedAt + "]";
	}
	
	public void updateMaintenanceDto (String licensePlate, String maintenanceType, String serviceCompany,
			LocalDate date, int mileage, String description, int amount, LocalDate createdAt, LocalDate updatedAt) {
		this.licensePlate = licensePlate;
		this.maintenanceType = maintenanceType;
		this.serviceCompany = serviceCompany;
		this.date = date;
		this.mileage = mileage;
		this.description = description;
		this.amount = amount;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
	
	public void deleteMaintenanceDto() {
		int enabled = 0;
		LocalDate updatedAt = LocalDate.now();
	}
}
