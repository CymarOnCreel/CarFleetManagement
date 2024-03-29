package application.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import application.alert.AlertMessage;
import application.dao.EmployeeDao;
import application.dto.EmployeeDto;
import application.util.UserSession;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainFrameController implements Initializable {

	@FXML
	private Button aboutUs;

	@FXML
	private MenuItem carHandler;

	@FXML
	private MenuItem addCarMaintenance;

	@FXML
	private MenuItem addNewCarToDatabase;

	@FXML
	private MenuItem addNewInsurance;

	@FXML
	private MenuItem addUser;

	@FXML
	private MenuButton admin;

	@FXML
	private MenuButton cars;

	@FXML
	private MenuItem changePassword;

	@FXML
	private MenuItem listUserReservation;

	@FXML
	private MenuItem listUsers;

	@FXML
	private MenuItem login;

	@FXML
	private MenuItem logout;

	@FXML
	private MenuItem newReservation;

	@FXML
	private MenuItem nextEvent;

	@FXML
	private MenuButton profile;

	@FXML
	private MenuItem registration;

	@FXML
	private MenuButton reservation;
	private int employeeId;
	
	@FXML
	private Label loadLabel;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
//		setUpMenuForEmployeeRoleAdmin();
//		setUpMenuForEmployeeRoleSuperAdmin();
//		setupMenuForEmployeeRoleUser();
		Platform.runLater(() -> {
			setUpMenuForNoEmployeeLogedIn();// --at start this should be run
		});

	}

	@FXML
	private void adminCarHandler() {
	    loadLabel.setVisible(true);
	    
	    adminCarListFill();
			    
	}
	
	public void hideLoadLabel() {
		loadLabel.setVisible(false);
		
	}
	    
	    private void adminCarListFill() {
	    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/frame/CarHandlerFrame.fxml"));
			AnchorPane root;
			try {
				root = (AnchorPane) loader.load();
				CarHandlerFrameController controller = loader.getController();
				controller.setMainFrameController(this);
				controller.listItemFrameFillWithCarData();
				Scene scene = new Scene(root);
				scene.getStylesheets().add(getClass().getResource("/application/util/application.css").toExternalForm());
				Stage stage = new Stage();
				stage.setTitle("Admin autó lista");
				stage.setScene(scene);
				stage.initModality(Modality.APPLICATION_MODAL);
				stage.initOwner(Main.getPrimaryStage());
				stage.getIcons().add(new Image("application/pictures/logo.png"));
				stage.showAndWait();
			} catch (IOException e) {
				e.printStackTrace();
			}
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
			e.printStackTrace();
		}

	}

	@FXML
	private void addNewInsurance(ActionEvent event) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/frame/InsuranceNewFrame.fxml"));
		AnchorPane root;
		try {
			root = (AnchorPane) loader.load();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/application/util/application.css").toExternalForm());
			Stage stage = new Stage();
			stage.setTitle("Biztosítás hozzáadása");
			stage.setScene(scene);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.initOwner(Main.getPrimaryStage());
			stage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void nextEvent(ActionEvent event) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/frame/NextEventFrame.fxml"));
		AnchorPane root;
		try {
			root = (AnchorPane) loader.load();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/application/util/application.css").toExternalForm());
			Stage stage = new Stage();
			stage.setTitle("Közeledő események");
			stage.setScene(scene);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.initOwner(Main.getPrimaryStage());
			stage.showAndWait();
		} catch (IOException e) {
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
		stage.setTitle("Új foglalás");
		stage.getIcons().add(new Image("application/pictures/logo.png"));
		stage.setScene(scene);
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.initOwner(Main.getPrimaryStage());
		stage.showAndWait();
	}

	@FXML
	void listAllReservationOfUser(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/frame/ListReservationFramePASS.fxml"));
		AnchorPane reservationsRoot = (AnchorPane) loader.load();
		Scene reservationsScene = new Scene(reservationsRoot);
		reservationsScene.getStylesheets()
				.add(getClass().getResource("/application/util/application.css").toExternalForm());
		Stage reservationsStage = new Stage();
		reservationsStage.setTitle("Foglalások listája");
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
			new AlertMessage().showUnknownError("Error", "Váratlan hiba,\nVegye fel a kapcsolatot a supporttal!");
		}

	}

	@FXML
	void changeUserPassword(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(
					getClass().getResource("/application/frame/ChangePasswordByUserFrame.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/application/util/application.css").toExternalForm());
			Stage stage = new Stage();
			stage.setTitle("Jelszó módosítás");
			stage.getIcons().add(new Image("application/pictures/logo.png"));
			stage.setScene(scene);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void addNewUser(ActionEvent event) {

		try {
			FXMLLoader loader = new FXMLLoader(
					getClass().getResource("/application/frame/AddNewEmployeeBySuperAdmin.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/application/util/application.css").toExternalForm());
			Stage stage = new Stage();
			stage.setTitle("Új felhasználó hozzáadása");
			stage.setScene(scene);
			stage.getIcons().add(new Image("application/pictures/logo.png"));
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.showAndWait();

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@FXML
	void listUsers(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/frame/ListUsersFrame.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/application/util/application.css").toExternalForm());
			Stage stage = new Stage();
			stage.setTitle("Felhasználók listája");
			stage.setScene(scene);
			stage.getIcons().add(new Image("application/pictures/logo.png"));
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.showAndWait();
		} catch (IOException e) {
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
			Stage stage = new Stage();
			stage.setTitle("Belépés");
			stage.setScene(loginsScene);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.getIcons().add(new Image("application/pictures/logo.png"));
			stage.showAndWait();
			if (UserSession.getUserId() != 0) {
				employeeId = UserSession.getUserId();
				setupMenuByEmployeeRole(employeeId);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void logout(ActionEvent event) {
		employeeId = -1;
		UserSession.setUserId(employeeId);
		new AlertMessage().showConfirmationAlertMessage("Viszlát", "Reméljük hamarosan újra találkozunk :)");
		setUpMenuForNoEmployeeLogedIn();
	}

	@FXML
	void registerNewUser(ActionEvent event) {
		try {
			FXMLLoader loader=new FXMLLoader(getClass().getResource("/application/frame/RegistrationFrame.fxml"));
			AnchorPane root=(AnchorPane) loader.load();
			Scene scene=new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/application/util/application.css").toExternalForm());
			Stage stage=new Stage();
			stage.setTitle("Regisztráció");
			stage.setScene(scene);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.getIcons().add(new Image("application/pictures/logo.png"));
			stage.showAndWait();

			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

	private void setupMenuForEmployeeRoleUser() {
		reservation.setDisable(false);
		newReservation.setDisable(false);
		listUserReservation.setDisable(false);
		admin.setDisable(true);
		profile.setDisable(false);
		login.setDisable(true);
		logout.setDisable(false);
		changePassword.setDisable(false);
		registration.setDisable(true);
		aboutUs.setDisable(false);
	}

	@FXML
	private void aboutUs() {
		new AlertMessage().showConfirmationAlertMessage("Rólunk", "Hamarosan:)");
	}

	@FXML
	void reserveCarForMaintenance(ActionEvent event) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/frame/MaintenanceNewFrame.fxml"));
		AnchorPane root;
		try {
			root = (AnchorPane) loader.load();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/application/util/application.css").toExternalForm());
			Stage stage = new Stage();
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
		admin.setDisable(true);
		login.setDisable(false);
		logout.setDisable(true);
		changePassword.setDisable(true);
		registration.setDisable(false);
		aboutUs.setDisable(false);
		cars.setDisable(true);
	}

	private void setUpMenuForEmployeeRoleAdmin() {
		reservation.setDisable(false);
		cars.setDisable(false);
		admin.setDisable(false);
		addUser.setDisable(true);
		login.setDisable(true);
		logout.setDisable(false);
		changePassword.setDisable(false);
		registration.setDisable(true);
		aboutUs.setDisable(false);
	}

	private void setUpMenuForEmployeeRoleSuperAdmin() {
		reservation.setDisable(false);
		cars.setDisable(false);
		admin.setDisable(false);
		addUser.setDisable(false);
		login.setDisable(true);
		logout.setDisable(false);
		changePassword.setDisable(false);
		registration.setDisable(true);
		aboutUs.setDisable(false);
	}

	

}
