package application.dto;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="car")
public class CarDto {
	


	@Column(name="license_plate")
	@Id
	private String licensePlate;
	
	@ManyToOne
    @JoinColumn(name = "make", nullable = false)
	private MakeDto make;
	
	@ManyToOne
    @JoinColumn(name = "model", nullable = true)
	private ModelDto model;
	
	@ManyToOne
    @JoinColumn(name = "category", nullable = false)
	private CategoryDto category;

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
	
	@ManyToOne
    @JoinColumn(name = "site_name", nullable = true)
	private SiteDto siteName;
	
	@Column(name="status")
	private String status;
	
	@Column(name="created_at")
	private LocalDate createdAt;
	
	@Column(name="updated_at")
	private LocalDate updatedAt;
	
	@Column(name="enabled")
	private int enabled;

	public CarDto(String licensePlate, MakeDto make, ModelDto model, CategoryDto category, String fuel, int doors, int seats,
			String transmissionType, int mileage, int serviceInterval, LocalDate inspectionExpiryDate, SiteDto siteName,
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

	public MakeDto getMake() {
		return make;
	}
	
	public ModelDto getModel() {
		return model;
	}

	public CategoryDto getCategory() {
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

	public SiteDto getSiteName() {
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
		return licensePlate + " - " + make;
	}
	
	public void updateCarDto(MakeDto make, ModelDto model, CategoryDto category, String fuel, int doors, int seats,
			String transmissionType, int mileage, int serviceInterval, LocalDate inspectionExpiryDate, SiteDto siteName,
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
	public void setMileage(int mileage) {
		this.mileage = mileage;
	}
	public void deleteCarDto() {
		enabled = 0;
		updatedAt = LocalDate.now();
	}
}
