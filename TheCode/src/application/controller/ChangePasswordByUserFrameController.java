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
import javafx.stage.Stage;

public class ChangePasswordByUserFrameController implements Initializable {
	@FXML
	private Button changePassword;

	@FXML
	private Button close;

	@FXML
	private Label newPasswordDontMatch;

	@FXML
	private PasswordField newPasswordFirst;

	@FXML
	private PasswordField newPasswordSecond;

	@FXML
	private PasswordField oldPasswordTf;

	private int userId;
	private EmployeeDao employeeDao;
	private EmployeeDto employeeLoggedIn;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		employeeDao = new EmployeeDao();
		userId = UserSession.getUserId();
		employeeLoggedIn = employeeDao.findById(userId);
	}

	@FXML
	private void Close(ActionEvent event) {
		Node source = (Node) event.getSource();
		Stage stage = (Stage) source.getScene().getWindow();
		stage.close();
	}

	@FXML
	private void changePassword(ActionEvent event) {
		String oldPassword = DigestUtils.sha1Hex(oldPasswordTf.getText());
		String newPasswordFirstTime = newPasswordFirst.getText();
		String newPasswordSecondTime = newPasswordSecond.getText();
		if (areTextFieldsFilled()) {
			if (isOldPasswordCorrect(oldPassword)
					&& isNewPasswordFirstSameAsNewPasswordSecond(newPasswordFirstTime, newPasswordSecondTime)
					&& !isOldPasswordAndNewPasswordSame(oldPassword, newPasswordFirstTime, newPasswordSecondTime)) {
				updateUsersPassword(newPasswordFirstTime);
			} else if (!isOldPasswordCorrect(oldPassword)
					&& !isNewPasswordFirstSameAsNewPasswordSecond(newPasswordFirstTime, newPasswordSecondTime)) {
				sendErrorAllDataIncorrect();
			} else if (!isNewPasswordFirstSameAsNewPasswordSecond(newPasswordFirstTime, newPasswordSecondTime)) {

				sendErrorAboutNewPasswordsNotMatchToUser();
			} else if (!isOldPasswordCorrect(oldPassword)) {
				sendErrorAboutOldPasswordNotMatchToUser();
			} else if (isOldPasswordAndNewPasswordSame(oldPassword, newPasswordFirstTime, newPasswordSecondTime)) {
				sendErrorOldAndNewPasswordCannotBeSame();
			}
		} else {
			sendErrorFieldsEmpty();
		}
	}

	private boolean areTextFieldsFilled() {
		boolean areFilled = false;
		if (oldPasswordTf.getText().length() != 0 && !oldPasswordTf.getText().isEmpty()
				&& newPasswordFirst.getText().length() != 0 && !newPasswordFirst.getText().isEmpty()
				&& newPasswordSecond.getText().length() != 0 && !newPasswordSecond.getText().isEmpty()) {
			areFilled = true;
		}
		return areFilled;
	}

	private void updateUsersPassword(String password) {
		String newPassword = DigestUtils.sha1Hex(password);
		employeeLoggedIn.setPassword(newPassword);
		employeeDao = new EmployeeDao();
		employeeDao.update(employeeLoggedIn);
		new AlertMessage().showConfirmationAlertMessage("Jelszó módositva", "A jelszó sikeresen módositva :)");
		closeWindow();
	}

	private void sendErrorAllDataIncorrect() {
		new AlertMessage().showUnknownError("Adatok nem megfelelőek",
				"Az új jelszavaknak meg kell egyezniük!!!\nA régi jelszó nem megfelelő!!!");
		addWrongDastaStyle(newPasswordFirst);
		addWrongDastaStyle(newPasswordSecond);
		addWrongDastaStyle(oldPasswordTf);

	}

	private void sendErrorFieldsEmpty() {
		new AlertMessage().showUnknownError("Üres mezők", "Mezők kitöltése kötelező!!!");
		if (newPasswordFirst.getText().isEmpty() || newPasswordFirst.getText().length() == 0) {
			addWrongDastaStyle(newPasswordFirst);
		} else {
			removeWrongDastaStyle(newPasswordFirst);
		}
		if (newPasswordSecond.getText().isEmpty() || newPasswordSecond.getText().length() == 0) {
			addWrongDastaStyle(newPasswordSecond);
		} else {
			removeWrongDastaStyle(newPasswordSecond);
		}
		if (oldPasswordTf.getText().isEmpty() || oldPasswordTf.getText().length() == 0) {
			addWrongDastaStyle(oldPasswordTf);
		} else {
			removeWrongDastaStyle(oldPasswordTf);
		}
	}

	private void sendErrorAboutNewPasswordsNotMatchToUser() {
		new AlertMessage().showUnknownError("Új jelszó hiba", "Az új jelszavaknak egyezniük kell!!!");
		removeWrongDastaStyle(oldPasswordTf);
		addWrongDastaStyle(newPasswordFirst);
		addWrongDastaStyle(newPasswordSecond);
	}

	private void sendErrorAboutOldPasswordNotMatchToUser() {
		new AlertMessage().showUnknownError("Régi jelszó hiba", "A régi jelszó hibás!!!");
		addWrongDastaStyle(oldPasswordTf);
		removeWrongDastaStyle(newPasswordFirst);
		removeWrongDastaStyle(newPasswordSecond);
	}

	private void sendErrorOldAndNewPasswordCannotBeSame() {
		new AlertMessage().showUnknownError("Azonos jelszó", "Az új jelszó nem lehet a régi jelszó!.");
		addWrongDastaStyle(oldPasswordTf);
		addWrongDastaStyle(newPasswordFirst);
		addWrongDastaStyle(newPasswordSecond);
	}

	private boolean isOldPasswordCorrect(String oldPassword) {
		return employeeLoggedIn.getPassword().equals(oldPassword);
	}

	private boolean isNewPasswordFirstSameAsNewPasswordSecond(String firstNewPassword, String secondNewPassword) {
		return firstNewPassword.equals(secondNewPassword);
	}

	private boolean isOldPasswordAndNewPasswordSame(String old, String newFirst, String newSecond) {
		String hashedPassword = DigestUtils.sha1Hex(newFirst);
		return (old.equals(hashedPassword) && isNewPasswordFirstSameAsNewPasswordSecond(newFirst, newSecond));
	}

	private void addWrongDastaStyle(PasswordField passwordfield) {
		passwordfield.getStyleClass().add("wrong-input-background");
	}

	private void removeWrongDastaStyle(PasswordField passwordfield) {
		passwordfield.getStyleClass().remove("wrong-input-background");
	}

	private void closeWindow() {
		Stage stage = (Stage) oldPasswordTf.getScene().getWindow();
		stage.close();
	}
}
