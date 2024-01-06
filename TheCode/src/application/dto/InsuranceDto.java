package application.dto;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name="insurance")
public class InsuranceDto {

	@Column(name="insurance_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private int insuranceId;
	
	@Column(name="license_plate")
	private String licensePlate;
	
	@Column(name="insurance_type")
	private String insuranceType;
	
	@Column(name="insurer_name")
	private String insurerName;
	
	@Column(name="price")
	private int price;
	
	@Column(name="expire_date")
	private LocalDate expireDate;
	
	@Column(name="pay_period")
	private String payPeriod;
	
	@Column(name="created_at")
	private LocalDate createdAt;
	
	@Column(name="updated_at")
	private LocalDate updatedAt;
	
	@Column(name="enabled")
	private boolean enabled;

	public InsuranceDto(String licensePlate, String insuranceType, String insurerName, int price,
			LocalDate expireDate, String payPeriod, LocalDate createdAt, LocalDate updatedAt, boolean enabled) {
		this.licensePlate = licensePlate;
		this.insuranceType = insuranceType;
		this.insurerName = insurerName;
		this.price = price;
		this.expireDate = expireDate;
		this.payPeriod = payPeriod;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.enabled = enabled;
	}

	public InsuranceDto() {
		
	}

	public int getInsuranceId() {
		return insuranceId;
	}

	public String getLicensePlate() {
		return licensePlate;
	}

	public String getInsuranceType() {
		return insuranceType;
	}

	public String getInsurerName() {
		return insurerName;
	}

	public int getPrice() {
		return price;
	}

	public LocalDate getExpireDate() {
		return expireDate;
	}

	public String getPayPeriod() {
		return payPeriod;
	}

	public LocalDate getCreatedAt() {
		return createdAt;
	}

	public LocalDate getUpdatedAt() {
		return updatedAt;
	}

	public boolean isEnabled() {
		return enabled;
	}

	@Override
	public String toString() {
		return "InsuranceDto [insuranceId=" + insuranceId + ", licensePlate=" + licensePlate + ", insuranceType="
				+ insuranceType + ", insurerName=" + insurerName + ", price=" + price + ", expireDate=" + expireDate
				+ ", payPeriod=" + payPeriod + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", enabled="
				+ enabled + "]";
	}
	
	public void updateInsuranceDto(String licensePlate, String insuranceType, String insurerName, int price,
			LocalDate expireDate, String payPeriod, LocalDate createdAt, LocalDate updatedAt, boolean enabled) {
		this.licensePlate = licensePlate;
		this.insuranceType = insuranceType;
		this.insurerName = insurerName;
		this.price = price;
		this.expireDate = expireDate;
		this.payPeriod = payPeriod;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.enabled = enabled;
	}
	
	public void deleteInsuranceDto() {
		enabled = false;
		updatedAt = LocalDate.now();
	}
}
