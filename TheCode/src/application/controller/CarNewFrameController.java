package application.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import application.alert.AlertMessage;
import application.dao.CarDao;
import application.dao.CategoryDao;
import application.dao.FuelDao;
import application.dao.MakeDao;
import application.dao.ModelDao;
import application.dao.SiteDao;
import application.dao.TransmissionDao;
import application.dto.CarDto;
import application.dto.CategoryDto;
import application.dto.FuelDto;
import application.dto.MakeDto;
import application.dto.ModelDto;
import application.dto.SiteDto;
import application.dto.TransmissionDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class CarNewFrameController implements Initializable{

	@FXML
    private TextField tfLicensePlate;

    @FXML
    private TextField tfDoors;

    @FXML
    private TextField tfSeats;

    @FXML
    private TextField tfMileage;

    @FXML
    private TextField tfServiceInterval;

    @FXML
    private DatePicker dpInspectionExpiryDate;

    @FXML
    private ComboBox<String> cmbMake;

    @FXML
    private ComboBox<String> cmbModel;

    @FXML
    private ComboBox<String> cmbCategory;

    @FXML
    private ComboBox<String> cmbFuel;

    @FXML
    private ComboBox<String> cmbTransmission;

    @FXML
    private ComboBox<String> cmbSite;

    @FXML
    private Button btnAddMake;

    @FXML
    private Button btnUpdateMake;

    @FXML
    private Button btnAddModel;
    
    @FXML
    private Button btnUpdateModel;

    @FXML
    private Button btnAddCategory;

    @FXML
    private Button btnUpdateCategory;

    @FXML
    private Button btnAddFuel;

    @FXML
    private Button btnUpdateFuel;

    @FXML
    private Button btnAddTransmission;

    @FXML
    private Button btnAddTransmisson;

    @FXML
    private Button btnAddSite;

    @FXML
    private Button btnUpdateSite;
    
    @FXML
    private Button btnSaveNewCar;
    
    @FXML
    void addCategory(ActionEvent event) {

    }

    @FXML
    void addFuel(ActionEvent event) {

    }

    @FXML
    void addMake(ActionEvent event) {

    }

    @FXML
    void addModel(ActionEvent event) {

    }

    @FXML
    void addSite(ActionEvent event) {

    }

    @FXML
    void addTransmission(ActionEvent event) {

    }


    @FXML
    void updateCategory(ActionEvent event) {

    }

    @FXML
    void updateFuel(ActionEvent event) {

    }

    @FXML
    void updateMake(ActionEvent event) {

    }

    @FXML
    void updateModel(ActionEvent event) {

    }

    @FXML
    void updateSite(ActionEvent event) {

    }

    @FXML
    void updateTransmission(ActionEvent event) {

    }

    @FXML
    private void saveNewCar(ActionEvent event) {
    	if (!isFieldEmpty()) {
			try {
				CarDto newCar = new CarDto(tfLicensePlate.getText().toUpperCase(), 
		    			cmbMake.getValue(),
		    			cmbModel.getValue(),
		    			cmbCategory.getValue(),
		    			cmbFuel.getValue(),
		    			Integer.parseInt(tfDoors.getText()), 
		    			Integer.parseInt(tfSeats.getText()),
		    			cmbTransmission.getValue(), 
		    			Integer.parseInt(tfMileage.getText()), 
		    			Integer.parseInt(tfServiceInterval.getText()), 
		    			dpInspectionExpiryDate.getValue(), 
		    			cmbSite.getValue(), "1", true);
		    	CarDao carDao = new CarDao();
		    	carDao.save(newCar);
			} catch (Exception e) {
				new AlertMessage().saveToDatabaseAlert();
			}
		}else {
			new AlertMessage().emptyTextFieldAlert();
		}
    	
    }
    
    private boolean isFieldEmpty() {
		if (tfLicensePlate.getText().isEmpty() ||
				tfDoors.getText().isEmpty()||
				tfMileage.getText().isEmpty()||
				tfSeats.getText().isEmpty()||
				tfServiceInterval.getText().isEmpty()||
				cmbCategory.getSelectionModel().getSelectedIndex()==0||
				cmbFuel.getSelectionModel().getSelectedIndex()==0||
				cmbMake.getSelectionModel().getSelectedIndex()==0||
				cmbModel.getSelectionModel().getSelectedIndex()==0||
				cmbSite.getSelectionModel().getSelectedIndex()==0||
				cmbTransmission.getSelectionModel().getSelectedIndex()==0||
				dpInspectionExpiryDate.getValue()==null)
		{
			return true;
		}
		return false;
	}
    
    private void cmbMakeFill() {
    	ObservableList<String> items = FXCollections.observableArrayList();
		MakeDao makeDao = new MakeDao();
		List<MakeDto> makes = makeDao.getAll();
		if (!makes.isEmpty()) {
			for (MakeDto make: makes) {
				items.add(make.getNameMake());
			}
		}
		cmbMake.setItems(items);
		cmbMake.getSelectionModel().select(0);
    }
    
    @FXML
    private void makeSelected(ActionEvent event) {
    	cmbModel.setDisable(false);
    	btnAddModel.setDisable(false);
    	btnUpdateModel.setDisable(false);
    	cmbModelFill();
    }
    
    private void cmbModelFill() {
    	ObservableList<String> items = FXCollections.observableArrayList();
		ModelDao modelDao = new ModelDao();
		List<ModelDto> models = modelDao.getAll();
		if (!models.isEmpty()) {
			for (ModelDto model: models) {
				if (model.getMake().equals(cmbMake.getValue())) {
					items.add(model.getNameModel());
				}
			}
		}
		cmbModel.setItems(items);
		cmbModel.getSelectionModel().select(0);
    }
    
//    public void updateCar() {
//    	CarDto newCar = new CarDto(tfLicensePlate.getText().toUpperCase(), 
//    			cmbMake.getValue(),
//    			cmbModel.getValue(),
//    			cmbCategory.getValue(),
//    			cmbFuel.getValue(),
//    			Integer.parseInt(tfDoors.getText()), 
//    			Integer.parseInt(tfSeats.getText()),
//    			cmbTransmission.getValue(), 
//    			Integer.parseInt(tfMileage.getText()), 
//    			Integer.parseInt(tfServiceInterval.getText()), 
//    			dpInspectionExpiryDate.getValue(), 
//    			cmbSite.getValue(), "1", true);
//    	
//    	CarDao carDao = new CarDao();
//    	carDao.update(newCar);
//    }
    
//    public void deleteCar() {
//		new CarDao().deleteById(tfLicensePlate.getText().toUpperCase());
//	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		cmbMakeFill();
		cmbCategoryFill();
		cmbFuelFill();
		cmbTransmissionFill();
		cmbSiteFill();
	}

	private void cmbSiteFill() {
		ObservableList<String> items = FXCollections.observableArrayList();
		SiteDao siteDao = new SiteDao();
		List<SiteDto> sites = siteDao.getAll();
		if (!sites.isEmpty()) {
			for (SiteDto site: sites) {
				items.add(site.getNameModel());
			}
		}
		cmbSite.setItems(items);
		cmbSite.getSelectionModel().select(0);
    }

	private void cmbTransmissionFill() {
		ObservableList<String> items = FXCollections.observableArrayList();
		TransmissionDao transmissionDao = new TransmissionDao();
		List<TransmissionDto> transmissions = transmissionDao.getAll();
		if (!transmissions.isEmpty()) {
			for (TransmissionDto transmission : transmissions) {
				items.add(transmission.getTransmissionType());
			}
		}
		cmbTransmission.setItems(items);
		cmbTransmission.getSelectionModel().select(0);
	}

	private void cmbFuelFill() {
		ObservableList<String> items = FXCollections.observableArrayList();
		FuelDao fuelDao = new FuelDao();
		List<FuelDto> fuels = fuelDao.getAll();
		if (!fuels.isEmpty()) {
			for (FuelDto fuel : fuels) {
				items.add(fuel.getFuelType());
			}
		}
		cmbFuel.setItems(items);
		cmbFuel.getSelectionModel().select(0);
	}

	private void cmbCategoryFill() {
		ObservableList<String> items = FXCollections.observableArrayList();
		CategoryDao categoryDao = new CategoryDao();
		List<CategoryDto> categories = categoryDao.getAll();
		if (!categories.isEmpty()) {
			for (CategoryDto category : categories) {
				items.add(category.getNameCategory());
			}
		}
		cmbCategory.setItems(items);
		cmbCategory.getSelectionModel().select(0);
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
