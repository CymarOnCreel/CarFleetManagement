package application.dto;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="make")
public class MakeDto {
	
	@Column(name="name_make")
	@Id
	private String nameMake;
	
	@Column(name="picture_path_make")
	private String picturePathMake;
	
	public MakeDto(String nameMake, String picturePathMake) {
		this.nameMake = nameMake;
		this.picturePathMake = picturePathMake;
	}
	public MakeDto() {
		
	}

	public String getNameMake() {
		return nameMake;
	}
	public String getPicturePathMake() {
		return picturePathMake;
	}
	@Override
	public String toString() {
		return nameMake;
	}

	public void updateMakeDto(String picturePathMake) {
		this.picturePathMake = picturePathMake;
	}
	
	public void deleteMakeDto() {
		int enabled = 0;
		LocalDate updatedAt = LocalDate.now();
	}
}
