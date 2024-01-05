package application.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import application.alert.AlertMessage;
import application.dao.ReservationDao;
import application.dto.CarDto;
import application.dto.ReservationDto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ReservationDetailsController {

	@FXML
	private Button cancelReservation;

	@FXML
	private TextField carField;

	@FXML
	private Button changeCar;

	@FXML
	private Button close;

	@FXML
	private TextField descriptionField;

	@FXML
	private TextField employeeNameField;

	@FXML
	private DatePicker endDateField;

	@FXML
	private DatePicker startDateField;

	@FXML
	private Button update;
	@FXML
	private TextField showStartDate;
	@FXML
	private TextField showEndDate;
	private ReservationDao reservationDao;
	private int reservationId;
	private ListReservationsFrameController listFrameController;
	private ReservationDto reservation;

	public void setReservationListController(ListReservationsFrameController listFrameController) {
		this.listFrameController = listFrameController;
	}

	public ReservationDetailsController() {
		this.reservationDao = new ReservationDao();
	}

	@FXML
	void changeCar(ActionEvent event) {
		showCarPickerStage(reservation);
		}



	@FXML
	public void close(ActionEvent event) {
		Node source = (Node) event.getSource();
		Stage stage = (Stage) source.getScene().getWindow();
		stage.close();
	}

	@FXML
	void setReservationDeleted(ActionEvent event) {
		reservationDao.deleteById(reservationId);
		new AlertMessage().showConfirmationAlertMessage("Foglalás törlés", "A foglalást sikeresen törölted");
		reservation.setDeleted(true);
		Stage stage = (Stage) close.getScene().getWindow();
		stage.close();
		listFrameController.setTable();

	}

	@FXML
	void updateReservation(ActionEvent event) {

	}

	public void initialize(ReservationDto reservation) {
		LocalDateTime today=LocalDateTime.now();
		this.reservation = reservation;
		reservationId = reservation.getIdReservation();
		employeeNameField.setText(reservation.getEmployee().getLastName());
		carField.setText(reservation.getCar().getLicensePlate());
		showStartDate.setText(reservation.getStartDateTime().toString());
		showEndDate.setText(reservation.getEndDateTime().toString());
		descriptionField.setText(reservation.getDescription());
		if (reservation.isDeleted()) {
			setAllOptionsDisabledIfReservationIsNotUpdateble();
		} 
		else if(reservation.getStartDateTime().isBefore(today)){
			setAllOptionsDisabledIfReservationIsNotUpdateble();
		}else{
			startDateField.setDayCellFactory(createDayCellFactory(reservation));
			endDateField.setDayCellFactory(createDayCellFactory(reservation));
		}
	}

	private Callback<DatePicker, DateCell> createDayCellFactory(ReservationDto reservation) {
		return datePicker -> new DateCell() {
			@Override
			public void updateItem(LocalDate item, boolean empty) {
				super.updateItem(item, empty);
				if (reservation.getStartDateTime() != null && reservation.getEndDateTime() != null) {
					LocalDate startDate = reservation.getStartDateTime().toLocalDate();
					LocalDate endDate = reservation.getEndDateTime().toLocalDate();
					if (!item.isBefore(startDate) && !item.isAfter(endDate)) {
						setDisable(true);
						setStyle("-fx-background-color: #ff0000;");
					}
				}
			}
		};
	}

	private void setAllOptionsDisabledIfReservationIsNotUpdateble() {
		endDateField.setDisable(true);
		startDateField.setDisable(true);
		descriptionField.setDisable(true);
		update.setDisable(true);
		cancelReservation.setDisable(true);
		changeCar.setDisable(true);
	}
	
	private void showCarPickerStage(ReservationDto reservation) {
		FXMLLoader loader = new FXMLLoader(
				getClass().getResource("/application/frame/ChooseNewCarForReservationUpdate.FXML"));
	
	try {
		AnchorPane root = (AnchorPane) loader.load();	
		Scene scene=new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/application/util/application.css").toExternalForm());
		Stage stage=new Stage();
		stage.setTitle("Change car for reservation");
		stage.initModality(Modality.APPLICATION_MODAL);
		ChooseNewCarForReservationUpdateController controller=loader.getController();
		controller.setMainStage(stage);
		controller.setReservationForStage(reservation);
		stage.setScene(scene);
		stage.showAndWait();
		carField.setText(reservation.getCar().getLicensePlate());
		
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	}

//	public void changedCar(CarDto currentCar) {
//		  if (reservation != null) {
//		        reservation.setCar(currentCar);
//		    } else {
//		       System.out.println("no reservation");
//		    }
//		carField.setText(currentCar.getLicensePlate());
//	}
}
