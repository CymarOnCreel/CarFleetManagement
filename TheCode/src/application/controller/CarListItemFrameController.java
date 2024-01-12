package application.controller;

import java.util.List;

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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;

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
		ReservationDao srvDao = new ReservationDao();
		EmployeeDto employeeDto = srvDao.getEmployeeForNowReservedCar(car.getLicensePlate());
		if (employeeDto!=null) {
			lblIsReserved.setText("FOGLALT");
			lblEmployee.setText(employeeDto.getFullNameHun());
		}else {
			lblIsReserved.setText("SZABAD");
			lblEmployee.setText("");
		}
		
	}

	private void setNextEventLabels() {
		List<NextEvent> nextEvents = new MaintenanceSorter().nextMaintenanceDates();
		for (NextEvent nextEvent : nextEvents) {
			if (nextEvent.getLicensePlate().get().equals(car.getLicensePlate())) {
				lblNextMaintenanceDate.setText(nextEvent.getDate().getValue()+"");
				lblRemainKm.setText(nextEvent.getRemainingDistance().get());
			}
		}
	}

	@FXML
	void maintenanceList(ActionEvent event) {
		new AlertMessage().requiredFieldsEmpty(null, "hamarosan...");
	}
	
	@FXML
    void bookACarSevice(ActionEvent event) {
		new AlertMessage().requiredFieldsEmpty(null, "hamarosan...");
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
    void reserveCalendar(ActionEvent event) {
		new AlertMessage().requiredFieldsEmpty(null, "hamarosan...");
    }


}
