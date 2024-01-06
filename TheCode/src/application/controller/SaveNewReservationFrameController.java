package application.controller;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import application.alert.AlertMessage;
import application.dao.EmployeeDao;
import application.dao.PictureDao;
import application.dao.ReservationDao;
import application.dto.CarDto;
import application.dto.EmployeeDto;
import application.dto.PictureDto;
import application.dto.ReservationDto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class SaveNewReservationFrameController implements Initializable {
	@FXML
	private ImageView carImage;

	@FXML
	private Label carMake;

	@FXML
	private Label carModel;

	@FXML
	private Button close;

	@FXML
	private Label endDate;

	@FXML
	private Label fuelType;

	@FXML
	private Button save;

	@FXML
	private Label seatsNumber;

	@FXML
	private Label site;

	@FXML
	private Label startDate;

	@FXML
	private Label transmissionType;

	@FXML
	private TextField descriptionField;

	private Stage mainStage;

	private CarDto carChoosen;

	private LocalDateTime startDateData;
	private ReservationDao reservationDao = new ReservationDao();
	private LocalDateTime endDateData;

	public void setMainStage(Stage mainStage) {
		this.mainStage = mainStage;
	}

	public void setDataForStage(CarDto carChoosen, LocalDateTime startDateData, LocalDateTime endDateData) {
		this.carChoosen = carChoosen;
		this.startDateData = startDateData;
		this.endDateData = endDateData;
		System.out.println(startDateData.toString());
		System.out.println(carChoosen.toString());
		updateLabelsAndImage();
	}

	@FXML
	private void closeScene(ActionEvent event) {
		Node source = (Node) event.getSource();
		Stage stage = (Stage) source.getScene().getWindow();
		stage.close();
	}

	@FXML
	private void saveReservation(ActionEvent event) {
		saveReservationToDatabase(carChoosen, descriptionField.getText());
		new AlertMessage().showConfirmationAlertMessage("Reservation succesfull",
					"Reservation succesfull for:\n " + carChoosen.getLicensePlate() + " car \nat the interval of \n"
							+ startDateData+ "  \n " + endDateData);
		closeSaveFrame();
	}
	
	
	private void closeSaveFrame() {
		Stage stage = (Stage) save.getScene().getWindow();
		stage.close();	
		
	}

	private void saveReservationToDatabase(CarDto carChoosen, String description) {
		EmployeeDao empDao = new EmployeeDao();
		EmployeeDto employee = empDao.findById(1);
		ReservationDto reservationTosave = new ReservationDto(employee, carChoosen, startDateData, endDateData,
				description, LocalDateTime.now(), LocalDateTime.now(), false);
		reservationDao.save(reservationTosave);
	}

	private void updateLabelsAndImage() {
		carMake.setText(carMake.getText() + " " + carChoosen.getMake());
		carModel.setText(carModel.getText() + " " + carChoosen.getModel());
		startDate.setText(startDate.getText() + " " + startDateData.toLocalDate());
		endDate.setText(endDate.getText() + " " + endDateData.toLocalDate());
		fuelType.setText(fuelType.getText() + " " + carChoosen.getFuel());
		seatsNumber.setText(seatsNumber.getText() + " " + String.valueOf(carChoosen.getSeats()));
		site.setText(site.getText() + " " + carChoosen.getSiteName());
		transmissionType.setText(transmissionType.getText() + " " + carChoosen.getTransmissionType());
		setCarImage(carChoosen);
	}

	private void setCarImage(CarDto carChoosen) {
		PictureDao pictureDao = new PictureDao();
		String licensePlate = carChoosen.getLicensePlate();
		PictureDto imageOfCar = pictureDao.findImageByLicensePlate(licensePlate);
		if (imageOfCar != null) {
			Image image = new Image(imageOfCar.getPicturePath());
			carImage.setImage(image);
			carImage.setPreserveRatio(true);
	        carImage.setFitWidth(655.0);
	        carImage.setFitHeight(275.0);
		} else {
			Image image = new Image("/application/pictures/cancel.jpg");
			carImage.setImage(image);
			carImage.setPreserveRatio(true);
	        carImage.setFitWidth(655.0); 
	        carImage.setFitHeight(275.0);
		}
		 carImage.setSmooth(true);
		    carImage.setCache(true);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		save.setDisable(true);
		descriptionField.textProperty().addListener((observable, oldValue, newValue) -> {
			save.setDisable(newValue.trim().isEmpty());
		});


	}

}
