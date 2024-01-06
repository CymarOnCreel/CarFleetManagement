package application.controller;

import java.time.LocalDate;

import application.alert.AlertMessage;
import application.dao.ServiceCompanyDao;
import application.dao.SiteDao;
import application.dto.ServiceCompanyDto;
import application.dto.SiteDto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ServiceCompanyNewFrameController {

    @FXML
    private TextField tfNameServiceCompany;

    @FXML
    private TextField tfLocation;

    @FXML
    private TextField tfContactPerson;

    @FXML
    private TextField tfContactEmail;

    @FXML
    private TextField tfContactPhone;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnCancel;
    
    private MaintenanceNewFrameController maintenanceNewFrameController;

    @FXML
    void cancel(ActionEvent event) {
    	Stage stage = (Stage) btnCancel.getScene().getWindow();
		stage.close();
    }

    @FXML
    void saveNewServiceCompany(ActionEvent event) {
    	try {
        	if (!isFieldEmpty()) {
        		String nameServiceCompany = tfNameServiceCompany.getText();

        		ServiceCompanyDto newServiceCompany = new ServiceCompanyDto(nameServiceCompany, 
        				tfLocation.getText(),
        				tfContactPerson.getText(), 
        				tfContactEmail.getText(), 
        				tfContactPhone.getText(), 
        				LocalDate.now(), 
        				null, 
        				true );
        		ServiceCompanyDao saveServiceCompanyDao = new ServiceCompanyDao();
    			saveServiceCompanyDao.save(newServiceCompany);
    			maintenanceNewFrameController.setNewServiceCompanyInComboBox(newServiceCompany);
                cancel(null);
				
			}else {
				new AlertMessage().emptyTextFieldAlert();
			}
		} catch (Exception e) {
			new AlertMessage().saveToDatabaseAlert();
		}
    }


	private boolean isFieldEmpty() {
		return tfNameServiceCompany.getText().isEmpty();
	}

	public void setMaintenanceNewFrameController(MaintenanceNewFrameController controller) {
		maintenanceNewFrameController = controller;
		
	}

}
