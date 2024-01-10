package application.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

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

	@FXML
	void close(ActionEvent event) {
		Node source = (Node) event.getSource();
		Stage stage = (Stage) source.getScene().getWindow();
		stage.close();
	}

	@FXML
	void updateEmployee(ActionEvent event) {
		
		
	}

	public void initialize(EmployeeDto employee) {
		firstName.setText(employee.getFirstName());
		lastName.setText(employee.getLastName());
		driverLicence.setText(employee.getDriverLicense());
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

	private void setUpComboBox(ComboBox<String> roleNameComboBox, Map<String, String> allowedRoles) {
		roleNameComboBox.getItems().addAll(allowedRoles.keySet());
	}
	
	private boolean isFieldEmpty(TextField field) {
		return field.getText().isEmpty();
	}

}
