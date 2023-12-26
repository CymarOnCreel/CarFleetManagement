package application.dto;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="car")
public class CarDto {
	
	@Column(name="license_plate")
	@Id
	private String licensePlate;
	
	@Column(name="make")
	private String make;
	
	@Column(name="model")
	private String model;
	
	@Column(name="category")
	private String category;

	@Column(name="fuel")
	private String fuel;
	
	@Column(name="doors")
	private int doors;
	
	@Column(name="seats")
	private int seats;
	
	@Column(name="transmission_type")
	private String transmissionType;
	
	@Column(name="mileage")
	private int mileage;
	
	@Column(name="service_interval")
	private int serviceInterval;
	
	@Column(name="inspection_expiry_date")
	private LocalDate inspectionExpiryDate;
	
	@Column(name="site_name")
	private String siteName;
	
	@Column(name="status")
	private String status;
	
	@Column(name="created_at")
	private LocalDate createdAt;
	
	@Column(name="updated_at")
	private LocalDate updatedAt;
	
	@Column(name="enabled")
	private int enabled;

	public CarDto(String licensePlate, String make, String model, String category, String fuel, int doors, int seats,
			String transmissionType, int mileage, int serviceInterval, LocalDate inspectionExpiryDate, String siteName,
			String status, boolean enabled) {
		this.licensePlate = licensePlate;
		this.make = make;
		this.model = model;
		this.category = category;
		this.fuel = fuel;
		this.doors = doors;
		this.seats = seats;
		this.transmissionType = transmissionType;
		this.mileage = mileage;
		this.serviceInterval = serviceInterval;
		this.inspectionExpiryDate = inspectionExpiryDate;
		this.siteName = siteName;
		this.status = status;
		this.createdAt = LocalDate.now();
		this.updatedAt = null;
		this.enabled = enabled ? 1:0;
	}

	public CarDto() {
	}

	public String getLicensePlate() {
		return licensePlate;
	}

	public String getMake() {
		return make;
	}
	
	public String getModel() {
		return model;
	}

	public String getCategory() {
		return category;
	}

	public String getFuel() {
		return fuel;
	}

	public int getDoors() {
		return doors;
	}

	public int getSeats() {
		return seats;
	}

	public String getTransmissionType() {
		return transmissionType;
	}

	public int getMileage() {
		return mileage;
	}

	public int getServiceInterval() {
		return serviceInterval;
	}

	public LocalDate getInspectionExpiryDate() {
		return inspectionExpiryDate;
	}

	public String getSiteName() {
		return siteName;
	}

	public String getStatus() {
		return status;
	}

	public LocalDate getCreatedAt() {
		return createdAt;
	}

	public LocalDate getUpdatedAt() {
		return updatedAt;
	}

	public boolean isEnabled() {
		return enabled==1 ? true:false;
	}
	

	@Override
	public String toString() {
		return "CarDto [licensePlate=" + licensePlate + ", make=" + make + ", model=" + model + ", category=" + category
				+ ", fuel=" + fuel + ", doors=" + doors + ", seats=" + seats + ", transmissionType=" + transmissionType
				+ ", mileage=" + mileage + ", serviceInterval=" + serviceInterval + ", inspectionExpiryDate="
				+ inspectionExpiryDate + ", siteName=" + siteName + ", status=" + status + ", createdAt=" + createdAt
				+ ", updatedAt=" + updatedAt + ", enabled=" + enabled + "]";
	}
	
	public void updateCarDto(String make, String model, String category, String fuel, int doors, int seats,
			String transmissionType, int mileage, int serviceInterval, LocalDate inspectionExpiryDate, String siteName,
			String status, boolean enabled) {
		this.make = make;
		this.model = model;
		this.category = category;
		this.fuel = fuel;
		this.doors = doors;
		this.seats = seats;
		this.transmissionType = transmissionType;
		this.mileage = mileage;
		this.serviceInterval = serviceInterval;
		this.inspectionExpiryDate = inspectionExpiryDate;
		this.siteName = siteName;
		this.status = status;
		this.updatedAt = LocalDate.now();
		this.enabled = enabled ? 1:0;
	}
	
	public void deleteCarDto() {
		enabled = 0;
		updatedAt = LocalDate.now();
	}
	
	

}
