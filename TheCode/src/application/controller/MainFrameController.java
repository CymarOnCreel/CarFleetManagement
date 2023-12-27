package application.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainFrameController implements Initializable {

	@FXML
	private MenuItem addCarMaintenance;

	@FXML
	private MenuItem addNewCar;

	@FXML
	private MenuButton cars;

	@FXML
	private MenuItem changePassword;

	@FXML
	private Button home;

	@FXML
	private MenuItem listUserReservation;

	@FXML
	private MenuItem login;

	@FXML
	private MenuItem logout;

	@FXML
	private MenuItem newReservation;

	@FXML
	private MenuButton profile;

	@FXML
	private MenuItem registration;

	@FXML
	private MenuButton reservation;

	@FXML
	private Button search;

	@FXML
	void addNewCarToDatabase(ActionEvent event) {
		// Create scene + methods to add new car to database
	}

	@FXML
	void changeUserPassword(ActionEvent event) {
		// Create a scene + methods for changing the password
	}

	@FXML
	void createNewCarReservation(ActionEvent event) {
		// TO-DO create Scene + methods for user to make a car reservation for a period
		// of time
	}

	@FXML
	void listAllReservationOfUser(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/frame/ListReservationFrame.fxml"));
		AnchorPane reservationsRoot = (AnchorPane) loader.load();
		Scene reservationsScene = new Scene(reservationsRoot);
		reservationsScene.getStylesheets()
				.add(getClass().getResource("/application/util/application.css").toExternalForm());
		Stage reservationsStage = new Stage();
		reservationsStage.setTitle("List Reservations");
		reservationsStage.setScene(reservationsScene);
		reservationsStage.initModality(Modality.APPLICATION_MODAL);
		reservationsStage.showAndWait();

	}

	@FXML
	void login(ActionEvent event) {
		// TO-DO Login scene+methods
	}

	@FXML
	void logout(ActionEvent event) {
		// To-Do logout the user
	}

	@FXML
	void registerNewUser(ActionEvent event) {
		// To-Do create registration scene+methods to save user
	}

	@FXML
	void reserveCarForMaintenance(ActionEvent event) {
		// To-DO create Scene + methods for reserving a car for maintenance
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

}
