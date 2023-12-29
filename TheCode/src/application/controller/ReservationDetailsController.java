package application.controller;

import java.time.LocalDate;

import application.alert.AlertMessage;
import application.dao.ReservationDao;
import application.dto.ReservationDto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
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

	}

	@FXML
	public void close(ActionEvent event) {
		Node source = (Node) event.getSource();
		Stage stage = (Stage) source.getScene().getWindow();
		stage.close();
	}

	@FXML
	void setReservationDeleted(ActionEvent event) {
		System.out.println(reservationId);
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
		this.reservation=reservation;
		reservationId = reservation.getId();
		employeeNameField.setText(reservation.getEmployee().getLastName());
		carField.setText(reservation.getCar().getLicensePlate());
		showStartDate.setText(reservation.getStartDate().toString());
		showEndDate.setText(reservation.getEndDate().toString());
		descriptionField.setText(reservation.getDescription());
		if (reservation.isDeleted()) {
			setAllOptionsDisabledIfReservationIsDeleted();
		} else {
			startDateField.setDayCellFactory(createDayCellFactory(reservation));
			endDateField.setDayCellFactory(createDayCellFactory(reservation));
		}
	}

	private Callback<DatePicker, DateCell> createDayCellFactory(ReservationDto reservation) {
		return datePicker -> new DateCell() {
			@Override
			public void updateItem(LocalDate item, boolean empty) {
				super.updateItem(item, empty);
				if (reservation.getStartDate() != null && reservation.getEndDate() != null) {
					LocalDate startDate = reservation.getStartDate().toLocalDate();
					LocalDate endDate = reservation.getEndDate().toLocalDate();
					if (!item.isBefore(startDate) && !item.isAfter(endDate)) {
						setDisable(true);
						setStyle("-fx-background-color: #ff0000;");
					}
				}
			}
		};
	}

	private void setAllOptionsDisabledIfReservationIsDeleted() {
		endDateField.setDisable(true);
		startDateField.setDisable(true);
		descriptionField.setDisable(true);
		update.setDisable(true);
		cancelReservation.setDisable(true);
		changeCar.setDisable(true);
	}
}
