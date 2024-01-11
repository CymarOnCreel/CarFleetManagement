package application.dto;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="employee")
public class EmployeeDto {

	@Column(name="id_employee")
	@Id
	private int idEmployee;
	
	@Column(name="last_name")
	private String lastName;
	
	@Column(name="first_name")
	private String firstName;
	
	@Column(name="email")
	private String email;
	
	@Column(name="password")
	private String password;
	
	@Column(name="driver_license")
	private String driverLicense;
	
	@Column(name="role_name")
	private String roleName;
	
	@Column(name="created_at")
	private LocalDate createdAt;
	
	@Column(name="updated_at")
	private LocalDate updatedAt;
	
	@Column(name="enabled")
	private boolean enabled;

	public EmployeeDto(int idEmployee, String lastName, String firstName, String email, String password,
			String driverLicense, String roleName, LocalDate createdAt, LocalDate updatedAt, boolean enabled) {
		this.idEmployee = idEmployee;
		this.lastName = lastName;
		this.firstName = firstName;
		this.email = email;
		this.password = password;
		this.driverLicense = driverLicense;
		this.roleName = roleName;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.enabled = enabled;
	}
	public EmployeeDto() {
		super();
	}


	public int getIdEmployee() {
		return idEmployee;
	}

	public String getLastName() {
		return lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public String getDriverLicense() {
		return driverLicense;
	}

	public String getRoleName() {
		return roleName;
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

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setDriverLicense(String driverLicense) {
		this.driverLicense = driverLicense;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public void setUpdatedAt(LocalDate updatedAt) {
		this.updatedAt = updatedAt;
	}
	@Override
	public String toString() {
		return "EmployeeDto [idEmployee=" + idEmployee + ", lastName=" + lastName + ", firstName=" + firstName
				+ ", email=" + email + ", password=" + password + ", driverLicense=" + driverLicense + ", roleName="
				+ roleName + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", enabled=" + enabled + "]";
	}
	
	public void updateEmployeeDto(String lastName, String firstName, String email, String password,
			String driverLicense, String roleName, LocalDate createdAt, LocalDate updatedAt, boolean enabled) {
		this.lastName = lastName;
		this.firstName = firstName;
		this.email = email;
		this.password = password;
		this.driverLicense = driverLicense;
		this.roleName = roleName;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.enabled = enabled;
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public void deleteEmployeeDto() {
		this.enabled = false;
		this.updatedAt = LocalDate.now();
	}
	
	public void activateEmployeeDto() {
		this.enabled = true;
		this.updatedAt = LocalDate.now();
	}
	public String getFullName() {
		return firstName+" "+lastName;
	}
	
	public String getFullNameHun() {
		return lastName + " " + firstName;
	}
	
	
	public String isEnabledToString() {
		return enabled ? "aktív":"törölve";
	}
	
	
}
