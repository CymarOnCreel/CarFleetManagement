package application.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "model")

public class ModelDto {

	@Column(name = "name_model")
	@Id
	private String nameModel;

	@Column(name = "make")
	private String make;

	public ModelDto(String nameModel, String make) {
		this.nameModel = nameModel;
		this.make = make;
	}

	public ModelDto() {
	}

	public String getNameModel() {
		return nameModel;
	}

	public String getMake() {
		return make;
	}

	public void setNameModel(String nameModel) {
		this.nameModel = nameModel;
	}

	@Override
	public String toString() {
		return "ModelDto [nameModel=" + nameModel + ", make=" + make + "]";
	}

}
