package application.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import application.alert.AlertMessage;
import application.dao.CarDao;
import application.dao.MaintenanceDao;
import application.dao.MaintenanceTypeDao;
import application.dao.ServiceCompanyDao;
import application.dto.CarDto;
import application.dto.MaintenanceDto;
import application.dto.MaintenanceTypeDto;
import application.dto.ServiceCompanyDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MaintenanceNewFrameController implements Initializable {

    @FXML
    private ComboBox<CarDto> cmbLicensePlate;

    @FXML
    private ComboBox<MaintenanceTypeDto> cmbMaintenanceType;

    @FXML
    private ComboBox<ServiceCompanyDto> cmbServiceCompany;

    @FXML
    private DatePicker dpDate;

    @FXML
    private TextField tfMileage;

    @FXML
    private TextArea tfDescription;

    @FXML
    private TextField tfAmount;

    @FXML
    private Button btnNewMaintenanceType;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnNewServiceCompany;

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
    void newMaintenanceType(ActionEvent event) {
    	TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Karbantartás típus felvétele");
        dialog.setHeaderText("Adjon meg egy új karbantartás típust:");
        dialog.setContentText("Megnevezés:");

        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()) {
            try {
            	String maintenanceTypeName = result.get();
            	if (!maintenanceTypeName.isEmpty()) {
            		MaintenanceTypeDto newMaintenanceType = new MaintenanceTypeDto(maintenanceTypeName);
            		MaintenanceTypeDao maintenanceTypeDao = new MaintenanceTypeDao();
            		maintenanceTypeDao.save(newMaintenanceType);
            		cmbMaintenanceTypeFill();
                    cmbMaintenanceType.setValue(newMaintenanceType);
				}else {
					new AlertMessage().emptyNameTextFieldAlert();
				}
			} catch (Exception e) {
				new AlertMessage().saveToDatabaseAlert();
			}
        }
    }

    @FXML
    void newServiceCompany(ActionEvent event) {
    	Stage newServiceCompanyStage = new Stage();
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../frame/ServiceCompanyNewFrame.fxml"));
			AnchorPane root = loader.load();
			ServiceCompanyNewFrameController controller = loader.getController();
			controller.setMaintenanceNewFrameController(this);
			Scene scene = new Scene(root, 600, 600);
			newServiceCompanyStage.setScene(scene);
			newServiceCompanyStage.setTitle("Serviz társaság hozzáadása");
			newServiceCompanyStage.setX(350);
			newServiceCompanyStage.initModality(Modality.APPLICATION_MODAL);
			newServiceCompanyStage.showAndWait();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @FXML
    void saveNewMaintenance(ActionEvent event) {
    	if (!isFieldEmpty()) {
			try {
				MaintenanceDto newMaintenance = new MaintenanceDto(
						cmbLicensePlate.getValue().getLicensePlate(), 
		    			cmbMaintenanceType.getValue().getNameMaintenanceType(),
		    			cmbServiceCompany.getValue().getNameServiceCompany(),
		    			dpDate.getValue(),
		    			Integer.parseInt(tfMileage.getText()),
		    			tfDescription.getText(),
		    			Integer.parseInt(tfAmount.getText()), 
		    			LocalDate.now(), 
		    			null);
		    	MaintenanceDao maintenanceDao = new MaintenanceDao();
		    		maintenanceDao.save(newMaintenance);
		    		new AlertMessage().maintenanceSaveSuccess();
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
				cmbMaintenanceType.getSelectionModel().getSelectedItem()==null||
				cmbServiceCompany.getSelectionModel().getSelectedItem()==null||
				dpDate.getValue()==null ||
				tfMileage.getText().isEmpty()||
				tfDescription.getText().isEmpty()||
				tfAmount.getText().isEmpty())
		{
			return true;
		}
		return false;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		cmbLicensePlateFill();
		cmbMaintenanceTypeFill();
		cmbServiceCompanyFill();	
	}
	
	@FXML
	private void setMileagePromptText(ActionEvent event) {
		String actualMileage = cmbLicensePlate.getValue().getMileage()+"";
		tfMileage.setPromptText("aktuális: " + actualMileage);
	}

	private void cmbServiceCompanyFill() {
		cmbServiceCompany.getItems().clear();
		ObservableList<ServiceCompanyDto> items = FXCollections.observableArrayList();
		ServiceCompanyDao serviceCompanyDao = new ServiceCompanyDao();
		List<ServiceCompanyDto> serviceCompanies = serviceCompanyDao.getAll();
		if (!serviceCompanies.isEmpty()) {
			for (ServiceCompanyDto serviceCompany: serviceCompanies) {
				items.add(serviceCompany);
			}
		}
		cmbServiceCompany.setItems(items);
		cmbServiceCompany.getSelectionModel().select(-1);
	}

	private void cmbMaintenanceTypeFill() {
		cmbMaintenanceType.getItems().clear();
		ObservableList<MaintenanceTypeDto> items = FXCollections.observableArrayList();
		MaintenanceTypeDao maintenanceTypeDao = new MaintenanceTypeDao();
		List<MaintenanceTypeDto> maintenanceTypes = maintenanceTypeDao.getAll();
		if (!maintenanceTypes.isEmpty()) {
			for (MaintenanceTypeDto maintenanceType: maintenanceTypes) {
				items.add(maintenanceType);
			}
		}
		cmbMaintenanceType.setItems(items);
		cmbMaintenanceType.getSelectionModel().select(-1);
		
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

	public void setNewServiceCompanyInComboBox(ServiceCompanyDto newServiceCompany) {
		cmbServiceCompany.getItems().add(newServiceCompany);
		cmbServiceCompany.getSelectionModel().select(newServiceCompany);
		
	}

}
