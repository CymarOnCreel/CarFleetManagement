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
		userId= UserSession.getUserId();
		System.out.println(userId);
		employeeLoggedIn=employeeDao.findById(userId);
		System.out.println(employeeLoggedIn.toString());
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
		System.out.println(oldPassword);
		String newPasswordFirstTime = newPasswordFirst.getText();
		String newPasswordSecondTime = newPasswordSecond.getText();
		if (areTextFieldsFilled()) {
			if (isOldPasswordCorrect(oldPassword)
					&& isNewPasswordFirstSameAsNewPasswordSecond(newPasswordFirstTime, newPasswordSecondTime)) {
				updateUsersPassword(newPasswordFirstTime);
			} else if (!isOldPasswordCorrect(oldPassword)
					&& !isNewPasswordFirstSameAsNewPasswordSecond(newPasswordFirstTime, newPasswordSecondTime)) {
				sendErrorAllDataIsWrong();
			} else if (!isNewPasswordFirstSameAsNewPasswordSecond(newPasswordFirstTime, newPasswordSecondTime)) {

				sendErrorAboutNewPasswordsNotMatchToUser();
			} else if (!isOldPasswordCorrect(oldPassword)) {
				sendErrorAboutOldPasswordNotMatchToUser();
			}
		} else {
			sendErrorAllDataIsWrong();
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
		System.out.println(employeeLoggedIn.toString());
		employeeDao=new EmployeeDao();
		employeeDao.update(employeeLoggedIn);
		new AlertMessage().showConfirmationAlertMessage("Password Changed", "Your password changed succesfully");
		closeWindow();
	}

	private void sendErrorAllDataIsWrong() {
		new AlertMessage().showUnknownError("All Data Error",
				"Old Password is not correct \nNew passwords must match!!!");
		newPasswordFirst.setStyle("wrong-input-background");
		newPasswordSecond.setStyle("wrong-input-background");
		oldPasswordTf.setStyle("wrong-input-background");
	}

	private void sendErrorAboutNewPasswordsNotMatchToUser() {
		new AlertMessage().showUnknownError("Password dont match", "New passwords must match!!!");
		oldPasswordTf.getStyleClass().remove("wrong-input-background");
		newPasswordFirst.setStyle("wrong-input-background");
		newPasswordSecond.setStyle("wrong-input-background");
	}

	private void sendErrorAboutOldPasswordNotMatchToUser() {
		new AlertMessage().showUnknownError("Password dont match", "The old password is not correct!!!");
		oldPasswordTf.setStyle("wrong-input-background");
		newPasswordFirst.getStyleClass().remove("wrong-input-background");
		newPasswordSecond.getStyleClass().remove("wrong-input-background");
	}

	private boolean isOldPasswordCorrect(String oldPassword) {
		System.out.println("oldpassw "+employeeLoggedIn.getPassword());
		System.out.println(DigestUtils.sha1Hex(oldPasswordTf.getText()));
		System.out.println(employeeLoggedIn.getPassword().equals(DigestUtils.sha1Hex(oldPasswordTf.getText())));
		return employeeLoggedIn.getPassword().equals(oldPassword);
	}

	private boolean isNewPasswordFirstSameAsNewPasswordSecond(String firstNewPassword, String secondNewPassword) {
		System.out.println(employeeLoggedIn.toString());
		return firstNewPassword.equals(secondNewPassword);
	}

	private void closeWindow() {
		Stage stage = (Stage) oldPasswordTf.getScene().getWindow();
		stage.close();
	}
}
