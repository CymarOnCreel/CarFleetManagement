package application.controller;

import application.dto.RoleDto;
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
	    private ComboBox<RoleDto> roleName;

	    @FXML
	    private Button update;

	    
	    @FXML
	    void close(ActionEvent event) {
	    	Node source=(Node) event.getSource();
	    	Stage stage=(Stage) source.getScene().getWindow();
	    	stage.close();
	    }

	    @FXML
	    void updateEmployee(ActionEvent event) {
	    	

	    }
}
