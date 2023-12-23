package application.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.dao.MakeDao;
import application.dto.MakeDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

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
    void saveNewCar(ActionEvent event) {
//    	CarDto newCar = new CarDto("ABC-888", "Toyota", "Szédán", "Dízel", 4, 5,
//    			"Kézi", 12452, 10000, LocalDate.of(2024, 06, 05), "Iroda", "1", true);
//    	CarDao carDao = new CarDao();
//    	carDao.save(newCar);
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
    
    public void updateCar() {
//    	CarDto newCar = new CarDto("ABC-888", "Toyota", "Szédán", "Dízel", 4, 5,
//    			"Kézi", 12452, 10000, LocalDate.of(2025, 06, 05), "Raktár", "1", true);
//    	
//    	CarDao carDao = new CarDao();
//    	carDao.update(newCar);
    }
    
    public void deleteCar() {
		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		cmbMakeFill();
		
	}
    
}
