package application.dto;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "car_picture")
public class PictureDto {

	@Column(name = "picture_path")
	@Id
	private String picturePath;
	
	@Column(name = "license_plate")
	private String licensePlate;

	public PictureDto(String picturePath, String licensePlate) {
		this.picturePath = picturePath;
		this.licensePlate = licensePlate;
	}
	public PictureDto() {
		
	}
	public String getPicturePath() {
		return picturePath;
	}
	public String getLicensePlate() {
		return licensePlate;
	}
	@Override
	public String toString() {
		return "PictureDto [picturePath=" + picturePath + ", licensePlate=" + licensePlate + "]";
	}
	
	public void updatePictureDto(String licensePlate) {
		this.licensePlate = licensePlate;
	}

	public void deletePictureDto() {
		int enabled = 0;
		LocalDate updatedAt = LocalDate.now();
	}
	
	
	
	
	
	
	
}
