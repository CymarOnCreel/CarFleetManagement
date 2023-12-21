package application.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MainFrameController implements Initializable{
	
	@FXML
	ImageView logoImage;

	private Image image;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setImage("/application/pictures/logo.png", logoImage);
		
	}
	
	private void setImage(String imagePath, ImageView imageView) {
		image = new Image(getClass().getResourceAsStream(imagePath));
		imageView.setImage(image);
	}
	
	
	
}
