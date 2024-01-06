package application.dto;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "service_company")
public class ServiceCompanyDto {

	@Column(name = "name_service_company")
	@Id
	private String nameServiceCompany;
	
	@Column(name = "location")
	private String location;
	
	@Column(name = "contact_person")
	private String contactPerson;
	
	@Column(name = "contact_email")
	private String contactEmail;
	
	@Column(name = "contact_phone")
	private String contactPhone;
	
	@Column(name="created_at")
	private LocalDate createdAt;
	
	@Column(name="updated_at")
	private LocalDate updatedAt;
	
	@Column(name="enabled")
	private boolean enabled;

	public ServiceCompanyDto(String nameServiceCompany, String location, String contactPerson, String contactEmail,
			String contactPhone, LocalDate createdAt, LocalDate updatedAt, boolean enabled) {
		this.nameServiceCompany = nameServiceCompany;
		this.location = location;
		this.contactPerson = contactPerson;
		this.contactEmail = contactEmail;
		this.contactPhone = contactPhone;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.enabled = enabled;
	}

	public ServiceCompanyDto() {
		
	}

	public String getNameServiceCompany() {
		return nameServiceCompany;
	}

	public String getLocation() {
		return location;
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public String getContactPhone() {
		return contactPhone;
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
		return nameServiceCompany;
	}
	
	public void updateServiceCompanyDto(String location, String contactPerson, String contactEmail,
			String contactPhone, LocalDate createdAt, LocalDate updatedAt, boolean enabled) {
		this.location = location;
		this.contactPerson = contactPerson;
		this.contactEmail = contactEmail;
		this.contactPhone = contactPhone;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.enabled = enabled;
	}
	public void deleteServiceCompanyDto() {
		int enabled = 0;
		LocalDate updatedAt = LocalDate.now();
	}
	
	
}
