package application.controller;

import java.io.IOException;
import java.util.List;

import application.Main;
import application.alert.AlertMessage;
import application.dao.CarDao;
import application.dao.ReservationDao;
import application.dao.SiteDao;
import application.dto.CarDto;
import application.dto.EmployeeDto;
import application.dto.SiteDto;
import application.util.MaintenanceSorter;
import application.util.NextEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CarListItemFrameController {

	@FXML
	private ImageView ivCarPicture;

	@FXML
	private Label lblCar;

	@FXML
	private Label lblMileage;

	@FXML
	private Label lblIsReserved;

	@FXML
	private Label lblEmployee;

	@FXML
	private Label lblRemainKm;

	@FXML
	private MenuButton btnUpdateSite;

	@FXML
	private Button btnDeleteCar;
	
	@FXML
	private Button btnUpdateCar;

	@FXML
	private Button btnReserveDairy;

	@FXML
	private Button btnReserveForMaintenance;

	@FXML
	private Button btnMaintenanceList;

	@FXML
	private Label lblNextMaintenanceDate;

	private CarDto car;
	
	private CarHandlerFrameController carHandlerFrameController;
	
	public CarDto getCar() {
		return car;
	}
	
	public void setCarHandlerFrameController(CarHandlerFrameController controller) {
		carHandlerFrameController = controller;
	}

	public void setCar(CarDto car) {
		this.car = car;
		lblCar.setText(car.getLicensePlate() + " - " + car.getMake() + " " + car.getModel());
		lblMileage.setText(car.getMileage() + "");
		setNextEventLabels();
		setReservedStatus();
		fillMenuButtonUpdateSite();
		setBtnUpdateSiteLabel();
	}

	private void fillMenuButtonUpdateSite() {
	    btnUpdateSite.getItems().clear();

	    List<SiteDto> sites = new SiteDao().getAll();

	    for (SiteDto site : sites) {
	        MenuItem menuItem = new MenuItem(site.getNameSite());
	        menuItem.setOnAction(event -> handleSiteSelection(site));
	        btnUpdateSite.getItems().add(menuItem);
	    }
	    setBtnUpdateSiteLabel();
	}

	private void handleSiteSelection(SiteDto site) {
	    new CarDao().updateSite(car, site);
	    setBtnUpdateSiteLabel();
	    String newSiteMsg = "Az új telehely neve: " + site.getNameSite();
	    new AlertMessage().requiredFieldsEmpty("Telephely módosítás sikerült!", newSiteMsg);
	    setBtnUpdateSiteLabel();
	}

	private void setBtnUpdateSiteLabel() {
		btnUpdateSite.setText(car.getSiteName().getNameSite());
	}

	private void setReservedStatus() {
		ReservationDao rsvDao = new ReservationDao();
		EmployeeDto employeeDto = rsvDao.getEmployeeForNowReservedCar(car.getLicensePlate());
		if (employeeDto!=null) {
			lblIsReserved.setText("FOGLALT");
			lblEmployee.setText(employeeDto.getFullNameHun());
		}else {
			lblIsReserved.setText("SZABAD");
			lblEmployee.setText("");
		}
		
	}

	private void setNextEventLabels() {
		NextEvent nextEvent = new MaintenanceSorter().nextMaintenanceDateByCar(car);
		lblNextMaintenanceDate.setText(nextEvent.getDate().getValue()+"");
		lblRemainKm.setText(nextEvent.getRemainingDistance().get());
	}
	
	@FXML
	void updateCar(ActionEvent event) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/frame/CarNewFramePass.fxml"));
		AnchorPane root;
		try {
			root = (AnchorPane) loader.load();
			CarNewFrameController controller = loader.getController();
			controller.updateCarInitialization(car);
			controller.setCarHandlerFrameController(carHandlerFrameController);
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/application/util/application.css").toExternalForm());
			Stage stage = new Stage();
			stage.setTitle("New Car");
			stage.setScene(scene);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.initOwner(Main.getPrimaryStage());
			stage.getIcons().add(new Image("application/pictures/logo.png"));
			stage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void maintenanceList(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/frame/MaintenanceTable.fxml"));
		AnchorPane root = (AnchorPane) loader.load();
		MaintenanceTableController controller = loader.getController();
		controller.setMaintenanceData(car);
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/application/util/application.css").toExternalForm());
		Stage stage = new Stage();
		stage.setTitle(car + "Karbantartási lista");
		stage.getIcons().add(new Image("application/pictures/logo.png"));
		stage.setScene(scene);
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.initOwner(Main.getPrimaryStage());
		stage.showAndWait();
	}
	
	@FXML
    void bookACarSevice(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/frame/NewReservationFrame.fxml"));
		AnchorPane root = (AnchorPane) loader.load();
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/application/util/application.css").toExternalForm());
		Stage stage = new Stage();
		stage.setTitle("Új foglalás");
		stage.getIcons().add(new Image("application/pictures/logo.png"));
		stage.setScene(scene);
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.initOwner(Main.getPrimaryStage());
		stage.showAndWait();
    }

    @FXML
    void deleteCar(ActionEvent event) {
    	boolean isConfirmedDelete = new AlertMessage().isConfirmedDelete();
		if (isConfirmedDelete) {
			new CarDao().deleteById(car.getLicensePlate());
		}
		carHandlerFrameController.listItemFrameFillWithCarData();
    }


    @FXML
    void reserveCalendar(ActionEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/frame/ReservationListByCar.fxml"));
		AnchorPane root = (AnchorPane) loader.load();
		ReservationByCarTableController controller = loader.getController();
		controller.setReservationData(car);
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/application/util/application.css").toExternalForm());
		Stage stage = new Stage();
		stage.setTitle(car + "Foglalási lista");
		stage.getIcons().add(new Image("application/pictures/logo.png"));
		stage.setScene(scene);
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.initOwner(Main.getPrimaryStage());
		stage.showAndWait();
    }


}
