package application.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.apache.commons.codec.digest.DigestUtils;

import application.dto.EmployeeDto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class SignUpController implements Initializable {
	@FXML private Label lblLastName;
	@FXML private TextField tfLastName;
	@FXML private Label lblFirstName;
	@FXML private TextField tfFirstName;
	@FXML private Label lblEmail;
	@FXML private TextField tfEmail;
	@FXML private Label lblPassword;
	@FXML private PasswordField pfPassword;
	@FXML private Label lblDriverLicenseNumber;
	@FXML private TextField tfDriverLicenseNumber;
	@FXML private Button btnSignUp;
	@FXML private Label lblResult;
	

	@FXML private void signUp(ActionEvent event) {
		String lastName = lblLastName.getText();
		String firstName = lblFirstName.getText();
		String email = tfEmail.getText();
		String password = DigestUtils.sha1Hex(pfPassword.getText());
		String driverLicenseNumber = tfDriverLicenseNumber.getText();
		
		EmployeeDto employeeDtoObj = 
	}
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
}
