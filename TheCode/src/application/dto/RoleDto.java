package application.dto;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "category")
public class RoleDto {

	@Column(name = "name_role")
	@Id
	private String nameRole;
	
	@Column(name="description")
	private String description;

	public RoleDto(String nameRole, String description) {
		this.nameRole = nameRole;
		this.description = description;
	}

	public RoleDto() {
		
	}

	public String getNameRole() {
		return nameRole;
	}

	public String getDescription() {
		return description;
	}

	@Override
	public String toString() {
		return "RoleDto [nameRole=" + nameRole + ", description=" + description + "]";
	}
	
	public void updateRoleDto(String description) {
		this.description = description;
	}

	public void deleteRoleDto() {
		int enabled = 0;
		LocalDate updatedAt = LocalDate.now();
	}
	
}
