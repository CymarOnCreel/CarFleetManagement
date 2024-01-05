package application.controller;

import java.io.IOException;
import java.time.LocalDateTime;

import application.alert.AlertMessage;
import application.dao.ReservationDao;
import application.dto.ReservationDto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

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
		reservation.setDescription(descriptionField.getText());
		reservationDao.update(reservation);
		Node source = (Node) event.getSource();
		Stage stage = (Stage) source.getScene().getWindow();
		stage.close();
		new AlertMessage().showConfirmationAlertMessage("Reservation Update Succesfull", "The Reservation was succesfully updated");
		listFrameController.setTable();
	}

	public void initialize(ReservationDto reservation) {
		LocalDateTime today = LocalDateTime.now();
		this.reservation = reservation;
		reservationId = reservation.getIdReservation();
		employeeNameField.setText(reservation.getEmployee().getLastName());
		carField.setText(reservation.getCar().getLicensePlate());
		showStartDate.setText(reservation.getStartDateTime().toLocalDate().toString());
		showEndDate.setText(reservation.getEndDateTime().toLocalDate().toString());
		descriptionField.setText(reservation.getDescription());
		if (reservation.isDeleted()) {
			setAllOptionsDisabledIfReservationIsNotUpdateble();
		} else if (reservation.getStartDateTime().isBefore(today)) {
			setAllOptionsDisabledIfReservationIsNotUpdateble();
		}
	}

	private void setAllOptionsDisabledIfReservationIsNotUpdateble() {
		descriptionField.setDisable(true);
		update.setDisable(true);
		cancelReservation.setDisable(true);
		changeCar.setDisable(true);
	}

	private void showCarPickerStage(ReservationDto reservation) {
		FXMLLoader loader = new FXMLLoader(
				getClass().getResource("/application/frame/ChooseNewCarForReservationUpdate.FXML"));

		try {
			AnchorPane anchorPane = (AnchorPane) loader.load();
			Scene scene = new Scene(anchorPane);
			scene.getStylesheets().add(getClass().getResource("/application/util/application.css").toExternalForm());
			Stage stage = new Stage();
			stage.setTitle("Change car for reservation");
			stage.initModality(Modality.APPLICATION_MODAL);
			ChooseNewCarForReservationUpdateController controller = loader.getController();
			controller.setMainStage(stage);
			controller.setReservationForStage(reservation);
			stage.setScene(scene);
			stage.showAndWait();
			carField.setText(reservation.getCar().getLicensePlate());

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
