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
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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
	private Button btnClose;
	@FXML
	private Button buttonSignin;
	@FXML
	Label lblEmailAdress;
	@FXML
	Button btnSignin;

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

	@FXML
	public void close(ActionEvent event) {
		Node source = (Node) event.getSource();
		Stage stage = (Stage) source.getScene().getWindow();
		stage.close();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	private void closePage() {
		Stage stage = (Stage) btnSignin.getScene().getWindow();
		stage.close();
	}

}
