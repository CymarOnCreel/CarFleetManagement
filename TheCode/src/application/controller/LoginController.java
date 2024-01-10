package application.controller;

import java.io.IOException;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LoginController implements Initializable {

	@FXML private Label lblEmailAddress;
	@FXML private TextField tfEmailAddress;
	@FXML private Label lblPassword;
	@FXML private PasswordField pfPassword;
	@FXML private Label lblEmailAdress;
	@FXML Button btnSignin;
	@FXML Button btnSignUp;

	private void signin(ActionEvent event) {
		EmployeeDao userDaoObj = new EmployeeDao();
		String email = tfEmailAddress.getText();
		String password = DigestUtils.sha1Hex(pfPassword.getText());
		Long result = userDaoObj.validateEmployeeByEmailAndPasswordLong(email, password);
		if (result > 0) {
			EmployeeDto employeLoggedIn = userDaoObj.getEmployeeByEmail(email);
			new AlertMessage().showConfirmationAlertMessage("Belépés Sikeres",
					"Üdvözöllek " + employeLoggedIn.getFullName());

			UserSession.setUserId(employeLoggedIn.getIdEmployee());
			closePage();
		} else {
			new AlertMessage().showConfirmationAlertMessage("Belépési Hiba", "A jelszó/email nem megfelelõ!!!");
		}
	}

	@FXML
	public void createSignUpStage(ActionEvent event) {
		Stage signUpStage = new Stage();
		signUpStage.setTitle("Regisztráció");
		try {
			AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("frame/SignUpFrame.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("util/application.css").toExternalForm());
			signUpStage.setScene(scene);
			signUpStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	private void closePage() {
		Stage stage = (Stage) btnSignin.getScene().getWindow();
		stage.close();
	}

	@FXML public void signin() {}

}
