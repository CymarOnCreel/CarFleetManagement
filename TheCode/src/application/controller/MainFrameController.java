package application.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import application.alert.AlertMessage;
import application.dao.EmployeeDao;
import application.dto.EmployeeDto;
import application.util.UserSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainFrameController implements Initializable {

	@FXML
	private Button home;

	@FXML
	private MenuButton reservation;
	@FXML
	private MenuItem newReservation;
	@FXML
	private MenuItem listUserReservation;

	@FXML
	private MenuButton admin;
	@FXML
	private SplitMenuButton cars;
	@FXML
	private MenuItem addCarMaintenance;
	@FXML
	private MenuItem addNewCarToDatabase;
	@FXML
	private MenuItem addNewInsurance;
	@FXML
	private SplitMenuButton users;
	@FXML
	private MenuItem addUser;
	@FXML
	private MenuItem listUsers;

	@FXML
	private MenuButton profile;
	@FXML
	private MenuItem login;
	@FXML
	private MenuItem registration;
	@FXML
	private MenuItem changePassword;
	@FXML
	private MenuItem logout;

	@FXML
	private Button search;
	private int employeeId;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
//		setUpMenuForEmployeeRoleAdmin();
		setUpMenuForEmployeeRoleSuperAdmin();
//		setupMenuForEmployeeRoleUser();
//		setUpMenuForNoEmployeeLogedIn();

	}

	@FXML
	void addNewCarToDatabase(ActionEvent event) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/frame/CarNewFramePass.fxml"));
		AnchorPane root;
		try {
			root = (AnchorPane) loader.load();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/application/util/application.css").toExternalForm());
			Stage stage = new Stage();
			stage.setTitle("New Car");
			stage.setScene(scene);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.initOwner(Main.getPrimaryStage());
			stage.getIcons().add(new Image("application/pictures/logo.png"));
			stage.showAndWait();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	 @FXML
	private  void addNewInsurance(ActionEvent event) {
		 FXMLLoader loader=new FXMLLoader(getClass().getResource("/application/frame/InsuranceNewFrame.fxml"));
			AnchorPane root;
			try {
				root = (AnchorPane)loader.load();
				Scene scene=new Scene(root);
				scene.getStylesheets().add(getClass().getResource("/application/util/application.css").toExternalForm());
				Stage stage=new Stage();
				stage.setTitle("Biztosítás hozzáadása");
				stage.setScene(scene);
				stage.initModality(Modality.APPLICATION_MODAL);
				stage.initOwner(Main.getPrimaryStage());
				stage.showAndWait();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	 
	@FXML
	void createNewCarReservation(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/frame/NewReservationFrame.fxml"));
		AnchorPane root = (AnchorPane) loader.load();
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/application/util/application.css").toExternalForm());
		Stage stage = new Stage();
		stage.setTitle("New Reservation");
		stage.getIcons().add(new Image("application/pictures/logo.png"));
		stage.setScene(scene);
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.initOwner(Main.getPrimaryStage());
		stage.showAndWait();
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
		reservationsStage.getIcons().add(new Image("application/pictures/logo.png"));

		reservationsStage.setScene(reservationsScene);
		reservationsStage.initModality(Modality.APPLICATION_MODAL);
		reservationsStage.showAndWait();

	}

	private void setupMenuByEmployeeRole(int employeeId) {
		EmployeeDao employeeDao = new EmployeeDao();
		EmployeeDto employeeLoggedIn = employeeDao.findById(employeeId);
		System.out.println(employeeLoggedIn.toString());
		String employeeRole = employeeLoggedIn.getRoleName();
		System.out.println(employeeRole);
		if (employeeRole.equalsIgnoreCase("user")) {
			setupMenuForEmployeeRoleUser();
		} else if (employeeRole.equalsIgnoreCase("admin")) {
			setUpMenuForEmployeeRoleAdmin();
		} else if (employeeRole.equalsIgnoreCase("superadmin")) {
			setUpMenuForEmployeeRoleSuperAdmin();
		} else {
			new AlertMessage().showUnknownError("Error", "An unkonown error occured,\nPlease contact support!");
		}

	}

	@FXML
	void changeUserPassword(ActionEvent event) {
		// Create a scene + methods for changing the password
	}

	@FXML
	void addNewUser(ActionEvent event) {

	}

	@FXML
	void listUsers(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/frame/ListUsersFrame.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			Scene scene=new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/application/util/application.css").toExternalForm());
			Stage stage= new Stage();
			stage.setTitle("List Of Users");
			stage.setScene(scene);
			stage.getIcons().add(new Image("application/pictures/logo.png"));

			stage.initModality(Modality.APPLICATION_MODAL);
			stage.showAndWait();			
	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	void login(ActionEvent event) {

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/frame/Login.fxml"));
			GridPane loginRoot = (GridPane) loader.load();

			Scene loginsScene = new Scene(loginRoot);
			loginsScene.getStylesheets()
					.add(getClass().getResource("/application/util/application.css").toExternalForm());
			Stage reservationsStage = new Stage();
			reservationsStage.setTitle("Login");
			reservationsStage.setScene(loginsScene);
			reservationsStage.initModality(Modality.APPLICATION_MODAL);
			reservationsStage.getIcons().add(new Image("application/pictures/logo.png"));

			reservationsStage.showAndWait();
			employeeId = UserSession.getUserId();
			System.out.println(employeeId + " az id");
			setupMenuByEmployeeRole(employeeId);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	void logout(ActionEvent event) {
		employeeId = -1;
		UserSession.setUserId(employeeId);
		new AlertMessage().showConfirmationAlertMessage("Bye", "Hope you use our app soon:)");
		setUpMenuForNoEmployeeLogedIn();
	}

	@FXML
	void registerNewUser(ActionEvent event) {
		// To-Do create registration scene+methods to save user
	}

	private void setupMenuForEmployeeRoleUser() {
		reservation.setDisable(false);
		newReservation.setDisable(false);
		listUserReservation.setDisable(false);
		admin.setDisable(true);
//		addCarMaintenance.setDisable(false);
//		addNewCarToDatabase.setDisable(false);
//		addUser.setDisable(true);
//		listUsers.setDisable(false);
		profile.setDisable(false);
		login.setDisable(true);
		logout.setDisable(false);
		changePassword.setDisable(false);
		registration.setDisable(true);
		search.setDisable(false);
	}

	@FXML
	void reserveCarForMaintenance(ActionEvent event) {
		FXMLLoader loader=new FXMLLoader(getClass().getResource("/application/frame/MaintenanceNewFrame.fxml"));
		AnchorPane root;
		try {
			root = (AnchorPane)loader.load();
			Scene scene=new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/application/util/application.css").toExternalForm());
			Stage stage=new Stage();
			stage.setTitle("Karbantartás hozzáadása");
			stage.setScene(scene);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.initOwner(Main.getPrimaryStage());
			stage.showAndWait();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

	private void setUpMenuForNoEmployeeLogedIn() {
		reservation.setDisable(true);
//		newReservation.setDisable(true);
//		listUserReservation.setDisable(true);
		admin.setDisable(true);
//		addCarMaintenance.setDisable(true);
//		addNewCarToDatabase.setDisable(true);
//		addUser.setDisable(true);
//		listUsers.setDisable(true);
		profile.setDisable(false);
		login.setDisable(false);
		logout.setDisable(true);
		changePassword.setDisable(true);
		registration.setDisable(false);
		search.setDisable(true);
	}

	private void setUpMenuForEmployeeRoleAdmin() {
		reservation.setDisable(false);
		newReservation.setDisable(false);
		listUserReservation.setDisable(false);
		admin.setDisable(false);
		addCarMaintenance.setDisable(false);
		addNewCarToDatabase.setDisable(false);
		addUser.setDisable(true);
		listUsers.setDisable(false);
		profile.setDisable(false);
		login.setDisable(true);
		logout.setDisable(false);
		changePassword.setDisable(false);
		registration.setDisable(true);
		search.setDisable(false);
	}

	private void setUpMenuForEmployeeRoleSuperAdmin() {
		reservation.setDisable(false);
		newReservation.setDisable(false);
		listUserReservation.setDisable(false);
		admin.setDisable(false);
		addCarMaintenance.setDisable(false);
		addNewCarToDatabase.setDisable(false);
		addUser.setDisable(false);
		listUsers.setDisable(false);
		profile.setDisable(false);
		login.setDisable(true);
		logout.setDisable(false);
		changePassword.setDisable(false);
		registration.setDisable(true);
		search.setDisable(false);
	}

}
