package application.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.commons.codec.digest.DigestUtils;

import application.alert.AlertMessage;
import application.dao.EmployeeDao;
import application.dao.RoleDao;
import application.dto.EmployeeDto;
import application.dto.RoleDto;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddNewEmployeeBySuperAdmin implements Initializable {
	@FXML
	private Button btnClose;

	@FXML
	private Button btnSave;

	@FXML
	private TextField driverLicense;

	@FXML
	private TextField emailAdress;

	@FXML
	private TextField firstName;

	@FXML
	private TextField lastName;

	@FXML
	private PasswordField password;

	@FXML
	private ComboBox<String> role;
	private List<RoleDto> roles;
	private List<TextField> textfields=new ArrayList<>();;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	addAllDataToFrame();

	}
	
	private void addAllDataToFrame() {
		Platform.runLater(() -> {
			fillComboBoxWithRoles();
		});
			populateTextFieldsList();
	}

	@FXML
	void saveEmployee(ActionEvent event) {
		if (doAllFieldsContainData()) {
			EmployeeDao employeeDao = new EmployeeDao();
			employeeDao.save(getAllDataFromFields());
			new AlertMessage().showConfirmationAlertMessage("Sikeres hozzáadás", "Új felhasználó sikeresen mentve");
			closeWindow();
		} else {
			showEmptyFields();
			new AlertMessage().showUnknownError("Hiányzó adatok", "Kérem töltse ki az összes kötelező adatot!");
			
		}
	}

	private EmployeeDto getAllDataFromFields() {
		EmployeeDto newEmployee = new EmployeeDto(0, lastName.getText(), firstName.getText(), emailAdress.getText(),
				DigestUtils.sha1Hex(password.getText()), driverLicense.getText(),
				role.getSelectionModel().getSelectedItem(), LocalDate.now(), null, true);
		return newEmployee;
	}

	private void fillComboBoxWithRoles() {
		role.getItems().clear();
		ObservableList<String> roleNames = FXCollections.observableArrayList();
		RoleDao roleDao = new RoleDao();
		roles = roleDao.getAll();
		if (!roles.isEmpty()) {
			for (RoleDto roleDto : roles) {
				roleNames.add(roleDto.getNameRole());
			}
		}
		role.setItems(roleNames);
		role.getSelectionModel().selectLast();
	}

	private boolean doAllFieldsContainData() {
		for (TextField field : textfields) {
			if (isFieldEmpty(field))
				return false;
		}
		return true;
	}

	private void showEmptyFields() {
		clearAllBackgroundColoring();
		for (TextField textField : textfields) {
			if (isFieldEmpty(textField)) {
				textField.getStyleClass().add("wrong-input-background");
			} else {
				textField.getStyleClass().remove("wrong-input-background");
			}
		}

	}

	private void clearAllBackgroundColoring() {
		for (TextField textField : textfields) {
							textField.getStyleClass().remove("wrong-input-background");
		
		}
	}
	private void populateTextFieldsList() {
		textfields.add(firstName);
		textfields.add(lastName);
		textfields.add(emailAdress);
		textfields.add(driverLicense);
		textfields.add(password);
	}

	private boolean isFieldEmpty(TextField field) {
		return (field.getText().isEmpty() || field.getText().length() == 0);
	}

	@FXML
	private void closeFrame(ActionEvent event) {
		Node source = (Node) event.getSource();
		Stage stage = (Stage) source.getScene().getWindow();
		stage.close();
	}

	private void closeWindow() {
		Stage stage = (Stage) driverLicense.getScene().getWindow();
		stage.close();
	}
}
