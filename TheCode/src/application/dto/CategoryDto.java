package application.dto;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "category")
public class CategoryDto {

	@Column(name = "name_category")
	@Id
	private String nameCategory;

	@Column(name = "picture_path_category")
	private String picturePathCategory;

	public CategoryDto(String nameCategory, String picturePathCategory) {
		this.nameCategory = nameCategory;
		this.picturePathCategory = picturePathCategory;
	}

	public CategoryDto() {

	}

	public String getNameCategory() {
		return nameCategory;
	}

	public String getPicturePathCategory() {
		return picturePathCategory;
	}

	@Override
	public String toString() {
		return "CategoryDto [nameCategory=" + nameCategory + ", picturePathCategory=" + picturePathCategory + "]";
	}

	public void updateCategoryDto(String nameCategory, String picturePathCategory) {
		this.nameCategory = nameCategory;
		this.picturePathCategory = picturePathCategory;
	}

	public void deleteCategoryDto() {
		int enabled = 0;
		LocalDate updatedAt = LocalDate.now();
	}

}
