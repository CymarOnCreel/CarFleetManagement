package application.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.apache.commons.codec.digest.DigestUtils;

import application.alert.AlertMessage;
import application.dao.EmployeeDao;
import application.dto.EmployeeDto;
import application.util.UserSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class LoginController implements Initializable {

	@FXML
	private Label lblEmailAddress;
	@FXML
	private TextField tfEmailAddress;
	@FXML
	private Label lblPassword;
	@FXML
	private PasswordField pfPassword;
	@FXML
	private Button buttonSignin;
	@FXML
	Label lblEmailAdress;
	@FXML
	Button btnSignin;
	@FXML
	private Button btnClose;
	@FXML
	private Button registration;

	@FXML
	private void signin(ActionEvent event) {
		EmployeeDao userDaoObj = new EmployeeDao();
		String email = tfEmailAddress.getText();
		String password = DigestUtils.sha1Hex(pfPassword.getText());
		Long result = userDaoObj.validateEmployeeByEmailAndPasswordLong(email, password);
		if (result > 0) {
			EmployeeDto employeLoggedIn = userDaoObj.getEmployeeByEmail(email);
			new AlertMessage().showConfirmationAlertMessage("Belépés Sikeres",
					"Üdvözöllek " + employeLoggedIn.getFullNameHun());

			UserSession.setUserId(employeLoggedIn.getIdEmployee());
			closePage();
		} else {
			new AlertMessage().showConfirmationAlertMessage("Belépés Hiba", "A jelszó/email nem megfelelő!!!");
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	private void closePage() {
		Stage stage = (Stage) btnSignin.getScene().getWindow();
		stage.close();
	}

	@FXML
	void close(ActionEvent event) {
		Node source=(Node) event.getSource();
		Stage stage=(Stage) source.getScene().getWindow();
		stage.close();
	}

	@FXML
	private void switchToRegistration(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/frame/RegistrationFrame.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/application/util/application.css").toExternalForm());
			Stage stage = new Stage();
			stage.setTitle("Regisztráció");
			stage.setScene(scene);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.getIcons().add(new Image("application/pictures/logo.png"));
			stage.showAndWait();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
