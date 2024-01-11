package application.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.codec.digest.DigestUtils;

import application.alert.AlertMessage;
import application.dao.EmployeeDao;
import application.dao.RoleDao;
import application.dto.EmployeeDto;
import application.dto.RoleDto;
import application.util.UserSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class UpdateEmployeeFrameController {
	@FXML
	private Button cancel;

	@FXML
	private TextField driverLicence;

	@FXML
	private TextField firstName;

	@FXML
	private TextField lastName;

	@FXML
	private PasswordField password;

	@FXML
	private ComboBox<String> roleName;

	@FXML
	private Button update;

	private List<RoleDto> roleNames;
	private String loggedInUserRole;
	private Map<String, List<String>> allowedTransitions = new HashMap<>();
	private EmployeeDto employeToUpdate;
	private ListUsersFrameController listUsersController;
	private List<TextField> textfields=new ArrayList<>();

	public void setListUsersController(ListUsersFrameController listUsersController) {
		this.listUsersController = listUsersController;
	}

	@FXML
	void close(ActionEvent event) {
		Node source = (Node) event.getSource();
		Stage stage = (Stage) source.getScene().getWindow();
		stage.close();
	}

	@FXML
	void updateEmployee(ActionEvent event) {
		if (checkIfFieldsHaveData()) {
			setDataFromTextFields();
			updateEmployeeToDatabase();
			listUsersController.setTable();
			closeWindow();
		} else {
			new AlertMessage().showUnknownError("Missing Data", "Nem lehet űres mező!!!");
			colorEmptyFields();
		}

	}

	private void updateEmployeeToDatabase() {
		EmployeeDao employeeDao = new EmployeeDao();
		employeeDao.update(employeToUpdate);
		new AlertMessage().showConfirmationAlertMessage("Update Succesfull",
				"A felhasználó adatai sikeresen frissítve!");
	}

	private void setDataFromTextFields() {
		String updatedFirstName = firstName.getText();
		String updatedLastName = lastName.getText();
		String updatedDriverLicence = driverLicence.getText();
		String updatedPassword = DigestUtils.sha1Hex(firstName.getText());
		String updatedRoleName = roleName.getSelectionModel().getSelectedItem();
		employeToUpdate.setFirstName(updatedFirstName);
		employeToUpdate.setLastName(updatedLastName);
		employeToUpdate.setDriverLicense(updatedDriverLicence);
		employeToUpdate.setPassword(updatedPassword);
		employeToUpdate.setRoleName(updatedRoleName);
		employeToUpdate.setUpdatedAt(LocalDate.now());
		System.out.println(employeToUpdate.toString());
	}

	public void initialize(EmployeeDto employee) {
	addTextfieldsToList();
		employeToUpdate = employee;
		firstName.setText(employee.getFirstName());
		lastName.setText(employee.getLastName());
		driverLicence.setText(employee.getDriverLicense());
		password.setText("*********");
		RoleDao roledao = new RoleDao();
		roleNames = roledao.getAll();
		allowedTransitions.put("admin", Arrays.asList("user", "admin"));
		allowedTransitions.put("superadmin", Arrays.asList("admin", "superadmin", "user"));
		EmployeeDao employeeDao = new EmployeeDao();
		loggedInUserRole = employeeDao.findById(UserSession.getUserId()).getRoleName();
		List<String> allowedRoles = allowedTransitions.get(loggedInUserRole);
		Map<String, String> allowedRoleMap = roleNames.stream()
				.filter(role -> allowedRoles.contains(role.getNameRole()))
				.collect(Collectors.toMap(RoleDto::getNameRole, RoleDto::getDescription));

		setUpComboBox(roleName, allowedRoleMap);
		roleName.getSelectionModel().select(employee.getRoleName());
	}

	private void addTextfieldsToList() {
		textfields.add(password);
		textfields.add(driverLicence);
		textfields.add(lastName);
		textfields.add(firstName);
	}
	private void setUpComboBox(ComboBox<String> roleNameComboBox, Map<String, String> allowedRoles) {
		roleNameComboBox.getItems().addAll(allowedRoles.keySet());
	}

	private boolean isFieldEmpty(TextField field) {
		return (field.getText().isEmpty() || field.getText().length() == 0);
	}

	private void colorEmptyFields() {
		for (TextField textField : textfields) {
			if(isFieldEmpty(textField)) {
				textField.getStyleClass().add("wrong-input-background");
			} else {
				textField.getStyleClass().remove("wrong-input-background");
			}
		}

	}

	private boolean checkIfFieldsHaveData() {
		for (TextField textField : textfields) {
			if(isFieldEmpty(textField)) {
				return false;
			}
		}
		return true;
	}

	private void closeWindow() {
		Stage stage = (Stage) password.getScene().getWindow();
		stage.close();
	}
}
