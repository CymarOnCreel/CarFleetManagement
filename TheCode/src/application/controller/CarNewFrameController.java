package application.controller;

import java.net.URL;
import java.util.List;
import java.util.Optional;
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
import javafx.scene.control.TextInputDialog;
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
    	TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Kategória felvétele");
        dialog.setHeaderText("Adjon meg egy új kategóriát:");
        dialog.setContentText("Megnevezés:");

        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()) {
            try {
            	String categoryName = result.get();
            	if (!categoryName.isEmpty()) {
//            		CategoryDto newCategory = new CategoryDto(categoryName, null);
//                    CategoryDao catDao = new CategoryDao();
//                    catDao.save(newCategory);
                    cmbCategory.setValue(categoryName);
				}else {
					new AlertMessage().emptyNameTextFieldAlert();
				}
			} catch (Exception e) {
				new AlertMessage().saveToDatabaseAlert();
			}
        }
    }

    @FXML
    void addFuel(ActionEvent event) {
    	TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Üzemanyag típus felvétele");
        dialog.setHeaderText("Adjon meg egy új üzemanyag típust:");
        dialog.setContentText("Megnevezés:");

        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()) {
            try {
            	String fuelName = result.get();
            	if (!fuelName.isEmpty()) {
//            		FuelDto newFuel = new FuelDto(fuelName);
//            		FuelDao fuelDao = new FuelDao();
//            		fuelDao.save(newFuel);
                    cmbFuel.setValue(fuelName);
				}else {
					new AlertMessage().emptyNameTextFieldAlert();
				}
			} catch (Exception e) {
				new AlertMessage().saveToDatabaseAlert();
			}
        }
    }


    @FXML
    void addMake(ActionEvent event) {
    	TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Új gyártmány felvétele");
        dialog.setHeaderText("Adjon meg egy új autó márkát:");
        dialog.setContentText("Megnevezés:");

        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()) {
            try {
            	String makeName = result.get();
            	if (!makeName.isEmpty()) {
//            		MakeDto newMake = new MakeDto(makeName, null);
//            		MakeDao makeDao = new MakeDao();
//            		makeDao.save(newMake);
                    cmbMake.setValue(makeName);
				}else {
					new AlertMessage().emptyNameTextFieldAlert();
				}
			} catch (Exception e) {
				new AlertMessage().saveToDatabaseAlert();
			}
        }
    }

    @FXML
    void addModel(ActionEvent event) {
    	TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Új modell felvétele");
        dialog.setHeaderText("Adjon meg egy új autó modellt:");
        dialog.setContentText("Megnevezés:");

        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()) {
            try {
            	String modelName = result.get();
            	String makeName = cmbMake.getValue();
            	if (!modelName.isEmpty()) {
//            		ModelDto newModel = new ModelDto(modelName, makeName);
//            		ModelDao modelDao = new ModelDao();
//            		modelDao.save(newModel);
                    cmbModel.setValue(modelName);
				}else {
					new AlertMessage().emptyNameTextFieldAlert();
				}
			} catch (Exception e) {
				new AlertMessage().saveToDatabaseAlert();
			}
        }
    }

    @FXML
    void addSite(ActionEvent event) {
    	TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Új telephely felvétele");
        dialog.setHeaderText("Adjon meg egy új telephelyet:");
        dialog.setContentText("Megnevezés:");

        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()) {
            try {
            	String siteName = result.get();
            	if (!siteName.isEmpty()) {
//            		SiteDto newSite = new SiteDto(siteName);
//            		SiteDao siteDao = new SiteDao();
//            		siteDao.save(newSite);
                    cmbSite.setValue(siteName);
				}else {
					new AlertMessage().emptyNameTextFieldAlert();
				}
			} catch (Exception e) {
				new AlertMessage().saveToDatabaseAlert();
			}
        }
    }

    @FXML
    void addTransmission(ActionEvent event) {
    	TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Új váltó típus felvétele");
        dialog.setHeaderText("Adjon meg egy új váltó típust:");
        dialog.setContentText("Megnevezés:");

        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()) {
            try {
            	String transmissionName = result.get();
            	if (!transmissionName.isEmpty()) {
//            		TransmissionDto newTransmission = new TransmissionDto(transmissionName);
//            		TransmissionDao transmissionDao = new TransmissionDao();
//            		transmissionDao.save(newTransmission);
                    cmbTransmission.setValue(transmissionName);
				}else {
					new AlertMessage().emptyNameTextFieldAlert();
				}
			} catch (Exception e) {
				new AlertMessage().saveToDatabaseAlert();
			}
        }
    }


    @FXML
    void updateCategory(ActionEvent event) {
    	String oldName = cmbCategory.getValue();
    	TextInputDialog dialog = new TextInputDialog(oldName);
    	dialog.setTitle("Kategória módosítása");
        dialog.setHeaderText("Adja meg a kategória új nevét:");
        dialog.setContentText("Megnevezés:");

        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()) {
            try {
            	String categoryName = result.get();
            	if (!categoryName.isEmpty()) {
//            		CategoryDto newCategory = new CategoryDto(categoryName, null);
//                  CategoryDao catDao = new CategoryDao();
//                  catDao.update(newCategory);
                    cmbCategory.setValue(categoryName);
				}else {
					new AlertMessage().emptyNameTextFieldAlert();
				}
			} catch (Exception e) {
				new AlertMessage().saveToDatabaseAlert();
			}
        }
    }

    @FXML
    void updateFuel(ActionEvent event) {
    	String oldName = cmbFuel.getValue();
    	TextInputDialog dialog = new TextInputDialog(oldName);
    	dialog.setTitle("Üzemanyag típus módosítása");
        dialog.setHeaderText("Adja meg az üzemanyag típus új nevét:");
        dialog.setContentText("Megnevezés:");

        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()) {
            try {
            	String fuelName = result.get();
            	if (!fuelName.isEmpty()) {
//            		FuelDto newFuel = new FuelDto(fuelName);
//                  FuelDao fuelDao = new FuelDao();
//                  fuelDao.update(newFuel);
                    cmbFuel.setValue(fuelName);
				}else {
					new AlertMessage().emptyNameTextFieldAlert();
				}
			} catch (Exception e) {
				new AlertMessage().saveToDatabaseAlert();
			}
        }
    }
  

    @FXML
    void updateMake(ActionEvent event) {
    	String oldName = cmbMake.getValue();
    	TextInputDialog dialog = new TextInputDialog(oldName);
    	dialog.setTitle("Gyártmány módosítása");
        dialog.setHeaderText("Adja meg az autó márkájának új nevét:");
        dialog.setContentText("Megnevezés:");

        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()) {
            try {
            	String makeName = result.get();
            	if (!makeName.isEmpty()) {
//            		MakeDto newMake = new MakeDto(makeName, null);
//                  MakeDao makeDao = new MakeDao();
//                  makeDao.update(newMake);
                    cmbMake.setValue(makeName);
				}else {
					new AlertMessage().emptyNameTextFieldAlert();
				}
			} catch (Exception e) {
				new AlertMessage().saveToDatabaseAlert();
			}
        }
    }

    @FXML
    void updateModel(ActionEvent event) {
    	String oldName = cmbModel.getValue();
    	TextInputDialog dialog = new TextInputDialog(oldName);
    	dialog.setTitle("Modell módosítása");
        dialog.setHeaderText("Adja meg a modell új nevét:");
        dialog.setContentText("Megnevezés:");

        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()) {
            try {
            	String modelName = result.get();
            	String makeName = cmbMake.getValue();
            	if (!modelName.isEmpty()) {
//            		ModelDto newModel = new ModelDto(modelName, makeName);
//                  ModelDao modelDao = new ModelDao();
//                  modelDao.update(newModel);
                    cmbModel.setValue(modelName);
				}else {
					new AlertMessage().emptyNameTextFieldAlert();
				}
			} catch (Exception e) {
				new AlertMessage().saveToDatabaseAlert();
			}
        }
    }

    @FXML
    void updateSite(ActionEvent event) {
    	String oldName = cmbSite.getValue();
    	TextInputDialog dialog = new TextInputDialog(oldName);
    	dialog.setTitle("Telephely módosítása");
        dialog.setHeaderText("Adja meg a telephely új nevét:");
        dialog.setContentText("Megnevezés:");

        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()) {
            try {
            	String siteName = result.get();
            	if (!siteName.isEmpty()) {
//            		SiteDto newSite = new SiteDto(siteName);
//                  SiteDao siteDao = new SiteDao();
//                  siteDao.update(newSite);
                    cmbSite.setValue(siteName);
				}else {
					new AlertMessage().emptyNameTextFieldAlert();
				}
			} catch (Exception e) {
				new AlertMessage().saveToDatabaseAlert();
			}
        }
    }

    @FXML
    void updateTransmission(ActionEvent event) {
    	String oldName = cmbTransmission.getValue();
    	TextInputDialog dialog = new TextInputDialog(oldName);
    	dialog.setTitle("Váltó típus módosítása");
        dialog.setHeaderText("Adja meg a váltó típus új nevét:");
        dialog.setContentText("Megnevezés:");

        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()) {
            try {
            	String transmissionName = result.get();
            	if (!transmissionName.isEmpty()) {
//            		TransmissionDto newTransmission = new TransmissionDto(transmissionName);
//                  TransmissionDao transmissionDao = new TransmissionDao();
//                  transmissionDao.update(newTransmission);
                    cmbTransmission.setValue(transmissionName);
				}else {
					new AlertMessage().emptyNameTextFieldAlert();
				}
			} catch (Exception e) {
				new AlertMessage().saveToDatabaseAlert();
			}
        }
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
		    	if (!carDao.isCarExist(tfLicensePlate.getText())) {
		    		carDao.save(newCar);
				}else {
					new AlertMessage().carExistAlert();
				}
		    	
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
//		cmbMakeFill();
//		cmbCategoryFill();
//		cmbFuelFill();
//		cmbTransmissionFill();
//		cmbSiteFill();
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
