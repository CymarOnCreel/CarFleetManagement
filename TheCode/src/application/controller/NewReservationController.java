package application.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import application.alert.AlertMessage;
import application.dto.CarDto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.util.Callback;

public class NewReservationController implements Initializable {
	@FXML
	private ChoiceBox<String> carBrand;

	@FXML
	private ChoiceBox<String> carModel;

	@FXML
	private Button close;

	@FXML
	private DatePicker endDate;

	@FXML
	private ChoiceBox<String> fuelType;

	@FXML
	private ChoiceBox<Integer> numberOfSeats;

	@FXML
	private Button search;

	@FXML
	private DatePicker startDate;

	@FXML
	private ChoiceBox<String> transmissionType;

	@FXML
	private TableView<CarDto> availabelCarsTable;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		LocalDate today = LocalDate.now();
		startDate.setDayCellFactory(disableInvalidDays(today));
		endDate.setDayCellFactory(disableInvalidDays(today));
		startDate.valueProperty().addListener((obs, oldDate, newDate) -> {
			endDate.setDayCellFactory(disableInvalidDays(newDate));
			if (isDatePicked(endDate) && endDate.getValue().isBefore(newDate)) {
				endDate.setValue(newDate);
			}
		});
	}

	@FXML
	void close(ActionEvent event) {
		Node source = (Node) event.getSource();
		Stage stage = (Stage) source.getScene().getWindow();
		stage.close();
	}

	@FXML
	void setCarPickerTable(ActionEvent event) {

		// To-DO
		// start date can start only after or same day as presentday -- DONE
		// end-date can only be start day or after it --- DONE
		// populate choiceboxes depending on date chossen.
		// get cars in a table depending on filtering
		// add a alertbox if a car is chosen from table asking for a description
		// save the reservation in database
		// return to main page
		if (isDatePicked(startDate) && isDatePicked(endDate) && (startDate.getValue().isBefore(endDate.getValue())
				|| startDate.getValue().isEqual(endDate.getValue()))) {
			new AlertMessage().showConfirmationAlertMessage("Kocsiválasztás", "ide kell a kocsiválasztás átirányitás");
		} else {
			new AlertMessage().requiredFieldsEmpty("Hiányos Adatok", "A kötelező adatok nem lettek kitöltve");
		}
	}

	private Callback<DatePicker, DateCell> disableInvalidDays(LocalDate date) {
		return datePicker -> new DateCell() {
			@Override
			public void updateItem(LocalDate item, boolean empty) {
				super.updateItem(item, empty);
				if (item.isBefore(date)) {
					setDisable(true);
					setStyle("-fx-background-color: #ff0000;");
				}
			}
		};
	}

	private boolean isDatePicked(DatePicker date) {
		return date.getValue() != null;
	}

}
