package application.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.commons.codec.digest.DigestUtils;

import application.alert.AlertMessage;
import application.dao.EmployeeDao;
import application.dto.EmployeeDto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegistrationFrameController implements Initializable {
	@FXML
	private Button close;

	@FXML
	private TextField emailAddress;

	@FXML
	private TextField firstName;

	@FXML
	private TextField lastName;

	@FXML
	private Label lblEmail;

	@FXML
	private Label lblFirstName;

	@FXML
	private Label lblLastName;

	@FXML
	private Label lblPasswordFirstTime;

	@FXML
	private Label lblPasswordSecondTime;
	@FXML
	private Label lblErrorPassword;
	@FXML
	private PasswordField passwordFirstTime;

	@FXML
	private PasswordField passwordSecondTime;

	@FXML
	private Button saveRegistration;

	private List<TextField> fields = new ArrayList<>();

	@FXML
	void closeFrame(ActionEvent event) {
		Node source = (Node) event.getSource();
		Stage stage = (Stage) source.getScene().getWindow();
		stage.close();
	}

	void closeFrame() {
		Stage stage = (Stage) saveRegistration.getScene().getWindow();
		stage.close();
	}
	@FXML
	void saveRegistration(ActionEvent event) {
		if (!areFieldsFilled()) {
			colorTextFieldsAcourdingToDataEntered();
			new AlertMessage().emptyTextFieldAlert();
		} else if (!isPasswordFirstAndPasswordTwiceIdentical()) {
			new AlertMessage().showUnknownError("Jelszo hiba", "A két jelszónak meg kell egyeznie!!!");
			colorPasswordFields(false);
		} else {
			saveEmployee();
			new AlertMessage().showConfirmationAlertMessage("Sikeres Mentés",
					"Sikeres regisztráció.\nJóváhagyás után használhatod az aplikációt.");
		closeFrame();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		addFieldsToList();

	}

	private void saveEmployee() {
		String password = DigestUtils.sha1Hex(passwordFirstTime.getText());
		EmployeeDto employeeDtoObj = new EmployeeDto(firstName.getText(), lastName.getText(), emailAddress.getText(),
				password, null, "user", LocalDate.now(), false);
		EmployeeDao employeeDao = new EmployeeDao();
		employeeDao.save(employeeDtoObj);
	}

	private boolean areFieldsFilled() {
		boolean areFilled = true;
		for (TextField textField : fields) {
			if (textField.getText().isEmpty() || textField.getText().length() == 0)
				areFilled = false;
		}
		return areFilled;
	}

	private void removeColoringOfFields() {
		for (TextField textField : fields) {
			textField.getStylesheets().remove("text-field-missing-data");
			textField.getStylesheets().remove("text-field-data-correct");

		}
	}

	private void colorTextFieldsAcourdingToDataEntered() {
		removeColoringOfFields();
		for (TextField textField : fields) {
			if (textField.getText().isEmpty() || textField.getText().length() == 0) {
				textField.getStyleClass().add("text-field-missing-data");
			} else {
				textField.getStyleClass().add("text-field-data-correct");
			}
		}
		if (isPasswordFirstAndPasswordTwiceIdentical()) {
			colorPasswordFields(true);
		} else {
			colorPasswordFields(false);
		}
	}

	private boolean isPasswordFirstAndPasswordTwiceIdentical() {
		return (passwordFirstTime.getText().equals(passwordSecondTime.getText())
				&& !passwordFirstTime.getText().isEmpty() && passwordFirstTime.getText().length() != 0);
	}

	private void colorPasswordFields(boolean isPasswordsIdentical) {
		if (isPasswordsIdentical) {
			passwordFirstTime.getStyleClass().removeAll("text-field-missing-data");
			passwordSecondTime.getStyleClass().removeAll("text-field-missing-data");
			passwordFirstTime.getStyleClass().add("text-field-data-correct");
			passwordSecondTime.getStyleClass().add("text-field-data-correct");
			lblErrorPassword.setVisible(false);
		} else {
			passwordFirstTime.getStyleClass().removeAll("text-field-data-correct");
			;
			passwordSecondTime.getStyleClass().removeAll("text-field-data-correct");
			passwordFirstTime.getStyleClass().add("text-field-missing-data");
			passwordSecondTime.getStyleClass().add("text-field-missing-data");
			lblErrorPassword.setVisible(true);
		}
	}

	private List<TextField> addFieldsToList() {
		fields.add(firstName);
		fields.add(lastName);
		fields.add(emailAddress);
		fields.add(passwordFirstTime);
		fields.add(passwordSecondTime);

		return fields;
	}

}
