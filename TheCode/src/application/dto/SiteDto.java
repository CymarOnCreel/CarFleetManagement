package application.dto;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "site")
public class SiteDto {

	@Column(name = "name_site")
	@Id
	private String nameSite;
	
	@Column(name = "location")
	private String location;
	
	@Column(name="capacity")
	private int capacity;
	
	@Column(name = "contact_person")
	private String contactPerson;
	
	@Column(name = "contact_email")
	private String contactEmail;
	
	@Column(name = "contact_phone")
	private String contactPhone;
	
	@Column(name="description")
	private String description;
	
	@Column(name="created_at")
	private LocalDate createdAt;
	
	@Column(name="updated_at")
	private LocalDate updatedAt;
	
	@Column(name="enabled")
	private boolean enabled;

	public SiteDto(String nameSite, String location, int capacity, String contactPerson, String contactEmail,
			String contactPhone, String description, LocalDate createdAt, LocalDate updatedAt, boolean enabled) {
		this.nameSite = nameSite;
		this.location = location;
		this.capacity = capacity;
		this.contactPerson = contactPerson;
		this.contactEmail = contactEmail;
		this.contactPhone = contactPhone;
		this.description = description;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.enabled = enabled;
	}

	public SiteDto() {
		
	}

	public String getNameSite() {
		return nameSite;
	}

	public String getLocation() {
		return location;
	}

	public int getCapacity() {
		return capacity;
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

	public String getDescription() {
		return description;
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
		return nameSite;
	}
	
	public void updateSiteDto(String location, int capacity, String contactPerson, String contactEmail,
			String contactPhone, String description, LocalDate createdAt, LocalDate updatedAt, boolean enabled) {
		this.location = location;
		this.capacity = capacity;
		this.contactPerson = contactPerson;
		this.contactEmail = contactEmail;
		this.contactPhone = contactPhone;
		this.description = description;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.enabled = enabled;
	}
	public void deleteSiteDto() {
		int enabled = 0;
		LocalDate updatedAt = LocalDate.now();
	}
}
