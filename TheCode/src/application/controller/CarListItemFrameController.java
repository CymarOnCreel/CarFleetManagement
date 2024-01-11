package application.controller;

import java.time.LocalDate;
import java.util.List;

import application.alert.AlertMessage;
import application.dao.CarDao;
import application.dao.ReservationDao;
import application.dto.CarDto;
import application.dto.EmployeeDto;
import application.util.MaintenanceSorter;
import application.util.NextEvent;
import javafx.beans.property.ObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
	private Button btnUpdateSite;

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
    void modifySite(ActionEvent event) {
		new AlertMessage().requiredFieldsEmpty(null, "hamarosan...");
    }

    @FXML
    void reserveCalendar(ActionEvent event) {
		new AlertMessage().requiredFieldsEmpty(null, "hamarosan...");
    }


}
