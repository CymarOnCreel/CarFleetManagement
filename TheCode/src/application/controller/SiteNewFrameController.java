package application.controller;

import java.time.LocalDate;

import application.alert.AlertMessage;
import application.dao.SiteDao;
import application.dto.SiteDto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class SiteNewFrameController {

    @FXML
    private TextField tfNameSite;

    @FXML
    private TextField tfLocation;

    @FXML
    private TextField tfCapacity;

    @FXML
    private TextField tfContactPerson;

    @FXML
    private TextField tfContactEmail;

    @FXML
    private TextField tfContactPhone;

    @FXML
    private TextField tfDescription;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnCancel;
    
    private CarNewFrameController carNewFrameController;
    
    public void setCarNewFrameController(CarNewFrameController controller) {
		carNewFrameController = controller;
	}

    @FXML
    void cancel(ActionEvent event) {
    	Stage stage = (Stage) btnCancel.getScene().getWindow();
		stage.close();
    }

    @FXML
    void saveNewSite(ActionEvent event) {
    	try {
        	if (!isFieldEmpty()) {
        		String nameSite = tfNameSite.getText();

        		SiteDto newSite = new SiteDto(nameSite, 
        				tfLocation.getText(),
        				Integer.parseInt(tfCapacity.getText()), 
        				tfContactPerson.getText(), 
        				tfContactEmail.getText(), 
        				tfContactPhone.getText(), 
        				tfDescription.getText(), 
        				LocalDate.now(), 
        				null, 
        				true );
        		SiteDao siteDao = new SiteDao();
        		
        		if (!siteDao.isSiteNameExists(nameSite)) {
        			SiteDao saveSiteDto = new SiteDao();
        			saveSiteDto.save(newSite);
                    carNewFrameController.setNewSiteInComboBox(newSite);
                    cancel(null);
				}else {
					new AlertMessage().siteNameExistsAlert();
				}
			}else {
				new AlertMessage().emptyTextFieldAlert();
			}
		} catch (Exception e) {
			new AlertMessage().saveToDatabaseAlert();
		}
    }
    
    private boolean isFieldEmpty() {
		if (tfNameSite.getText().isEmpty() ||
				tfLocation.getText().isEmpty()||
				tfCapacity.getText().isEmpty())
		{
			return true;
		}
		return false;
	}
    
    @FXML
    void intTyped(KeyEvent event) {
    	String character = event.getCharacter();
		if (!isValidIntInput(character)) {
			event.consume();
		}
	}

	private boolean isValidIntInput(String character) {
		return character.matches("[0-9]");
	}

}
