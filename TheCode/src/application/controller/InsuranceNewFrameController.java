package application.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import application.alert.AlertMessage;
import application.dao.CarDao;
import application.dao.InsuranceDao;
import application.dao.InsuranceTypeDao;
import application.dto.CarDto;
import application.dto.InsuranceDto;
import application.dto.InsuranceTypeDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class InsuranceNewFrameController implements Initializable{

    @FXML
    private ComboBox<CarDto> cmbLicensePlate;

    @FXML
    private ComboBox<InsuranceTypeDto> cmbInsuranceType;

    @FXML
    private TextField tfInsurerName;

    @FXML
    private TextField tfPrice;

    @FXML
    private TextField tfPayPeriod;

    @FXML
    private DatePicker dpExpireDate;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnNewInsuranceType;

    @FXML
    void cancel(ActionEvent event) {
    	Stage stage = (Stage) btnCancel.getScene().getWindow();
		stage.close();
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

    @FXML
    void newInsuranceType(ActionEvent event) {
    	TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Biztosítás típus felvétele");
        dialog.setHeaderText("Adjon meg egy új biztosítás típust:");
        dialog.setContentText("Megnevezés:");

        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()) {
            try {
            	String insuranceTypeName = result.get();
            	if (!insuranceTypeName.isEmpty()) {
            		InsuranceTypeDto newInsuranceType = new InsuranceTypeDto(insuranceTypeName);
            		InsuranceTypeDao insuranceTypeDao = new InsuranceTypeDao();
            		insuranceTypeDao.save(newInsuranceType);
            		cmbInsuranceTypeFill();
                    cmbInsuranceType.setValue(newInsuranceType);
				}else {
					new AlertMessage().emptyNameTextFieldAlert();
				}
			} catch (Exception e) {
				new AlertMessage().saveToDatabaseAlert();
			}
        }
    }

    @FXML
    void saveNewInsurance(ActionEvent event) {
    	if (!isFieldEmpty()) {
			try {
				InsuranceDto newInsurance = new InsuranceDto(
						cmbLicensePlate.getValue().getLicensePlate(), 
		    			cmbInsuranceType.getValue().getNameInsuranceType(),
		    			tfInsurerName.getText(),
		    			Integer.parseInt(tfPrice.getText()),
		    			dpExpireDate.getValue(),
		    			tfPayPeriod.getText(),
		    			LocalDate.now(), 
		    			null,
		    			true);
		    	InsuranceDao insuranceDao = new InsuranceDao();
		    		insuranceDao.save(newInsurance);
		    		new AlertMessage().insuranceSaveSuccess();
		    		cancel(null);
			
		    	
			} catch (Exception e) {
				new AlertMessage().saveToDatabaseAlert();
			}
		}else {
			new AlertMessage().emptyTextFieldAlert();
		}
    }
    
    private boolean isFieldEmpty() {
		if (cmbLicensePlate.getSelectionModel().getSelectedItem()==null||
				cmbInsuranceType.getSelectionModel().getSelectedItem()==null||
				tfInsurerName.getText().isEmpty()||
				tfPrice.getText().isEmpty()||
				dpExpireDate.getValue()==null ||
				tfPayPeriod.getText().isEmpty())
		{
			return true;
		}
		return false;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		cmbLicensePlateFill();
		cmbInsuranceTypeFill();
		
		
	}

	private void cmbInsuranceTypeFill() {
		cmbInsuranceType.getItems().clear();
		ObservableList<InsuranceTypeDto> items = FXCollections.observableArrayList();
		InsuranceTypeDao insuranceTypeDao = new InsuranceTypeDao();
		List<InsuranceTypeDto> insuranceTypes = insuranceTypeDao.getAll();
		if (!insuranceTypes.isEmpty()) {
			for (InsuranceTypeDto insuranceType: insuranceTypes) {
				items.add(insuranceType);
			}
		}
		cmbInsuranceType.setItems(items);
		cmbInsuranceType.getSelectionModel().select(-1);
	}

	private void cmbLicensePlateFill() {
		cmbLicensePlate.getItems().clear();
		ObservableList<CarDto> items = FXCollections.observableArrayList();
		CarDao carDao = new CarDao();
		List<CarDto> cars = carDao.getAll();
		if (!cars.isEmpty()) {
			for (CarDto car: cars) {
				items.add(car);
			}
		}
		cmbLicensePlate.setItems(items);
		cmbLicensePlate.getSelectionModel().select(-1);
	}

}
