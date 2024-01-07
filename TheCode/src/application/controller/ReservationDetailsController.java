package application.controller;

import java.io.IOException;
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
	private Button updateMileage;
	@FXML
	private TextField descriptionField;

	@FXML
	private TextField employeeNameField;

	@FXML
	private TextField mileageAtEnd;
	
	@FXML
	private TextField mileageAtStart;
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
	private void updateReservation(ActionEvent event) {
		reservation.setDescription(descriptionField.getText());
		performeUpdateAndCloseWindow(event);
	}

	private void performeUpdateAndCloseWindow(ActionEvent event) {
		reservationDao.update(reservation);
		Node source = (Node) event.getSource();
		Stage stage = (Stage) source.getScene().getWindow();
		stage.close();
		new AlertMessage().showConfirmationAlertMessage("Foglalás frissítve",
				"A foglalás sikeresen frissítve");
		listFrameController.setTable();
	}
	
	@FXML
	private void updateMileageAtEndOfTrip(ActionEvent event) {
		int mileageUpdate=reservation.getCar().getMileage();
		if (!isMileageTextFieldEmpty()) {
			if (isMileageLesserThenStartMileage()) {
				new AlertMessage().showUnknownError("Hiba kilométerállás", "A végső kilométerállás nem lehet kevesebb\n mint a kezdeti kilométerállás");
				mileageAtEnd.getStyleClass().add("wrong-input-background");
			} else {
				CarDto carUpdate = reservation.getCar();
				mileageUpdate = Integer.parseInt(mileageAtEnd.getText());
				carUpdate.setMileage(mileageUpdate);
				mileageAtStart.setText(String.valueOf(mileageUpdate));
				mileageAtEnd.getStyleClass().remove("wrong-input-background");
			}
		}
	}
	private boolean isMileageTextFieldEmpty() {
		return mileageAtEnd.getText().isEmpty();
	}
	private boolean isMileageLesserThenStartMileage() {
		return Integer.parseInt(mileageAtEnd.getText()) < reservation.getCar().getMileage();
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
		mileageAtStart.setText(String.valueOf(reservation.getCar().getMileage()));
		if (reservation.isDeleted()) {
			setAllOptionsDisabledIfReservationIsNotUpdateble();
		} else if (reservation.getStartDateTime().isBefore(today)) {
			setAllOptionsDisabledIfReservationIsNotUpdateble();
		}
		setUpListeners();
	}

	private void setUpListeners() {
		mileageAtEnd.textProperty().addListener((observable, oldaValue, newValue) -> {
			if (!newValue.matches("\\d*")) {
				mileageAtEnd.setText(newValue.replaceAll("[^\\d]", ""));
			}
		});

	}

	private void setAllOptionsDisabledIfReservationIsNotUpdateble() {
		descriptionField.setDisable(true);
		mileageAtEnd.setDisable(true);
		update.setDisable(true);
		cancelReservation.setDisable(true);
		changeCar.setDisable(true);
		updateMileage.setDisable(true);
	}

	private void showCarPickerStage(ReservationDto reservation) {
		FXMLLoader loader = new FXMLLoader(
				getClass().getResource("/application/frame/ChooseNewCarForReservationUpdate.FXML"));

		try {
			AnchorPane anchorPane = (AnchorPane) loader.load();
			Scene scene = new Scene(anchorPane);
			scene.getStylesheets().add(getClass().getResource("/application/util/application.css").toExternalForm());
			Stage stage = new Stage();
			stage.setTitle("Kocsi cseréje");
			stage.initModality(Modality.APPLICATION_MODAL);
			ChooseNewCarForReservationUpdateController controller = loader.getController();
			controller.setMainStage(stage);
			controller.setReservationForStage(reservation);
			stage.setScene(scene);
			stage.showAndWait();
			carField.setText(reservation.getCar().getLicensePlate());
			mileageAtStart.setText(String.valueOf(reservation.getCar().getMileage()));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
