package application.controller;



import java.net.URL;
import java.util.ResourceBundle;

import org.apache.commons.codec.digest.DigestUtils;

import application.dao.EmployeeDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController implements Initializable{


		@FXML private Label lblEmailAddress;
		@FXML private TextField tfEmailAddress;
		@FXML private Label lblPassword;
		@FXML private PasswordField pfPassword;
		@FXML private Button btnSignUp;
		@FXML private Button buttonSignin;
		@FXML Label lblEmailAdress;
		@FXML Button btnSignin;
		
		@FXML private void signin(ActionEvent event) {
			EmployeeDao userDaoObj = new EmployeeDao();
			 String email = tfEmailAddress.getText();
			 String password = DigestUtils.sha1Hex(pfPassword.getText());
			 Long result = userDaoObj.validateEmployeeByEmailAndPasswordLong(email, password);
			 if (result > 0) {
			     System.out.println("Beléphet :)");
			    } else {
			     System.out.println("Hibás felhasználónév/jelszó :(");
			    }
			}
		
		@FXML public void signUp(ActionEvent event) {
			
		}
		
		@Override
		public void initialize(URL location, ResourceBundle resources) {
			// TODO Auto-generated method stub
			
		}


		


		
}
