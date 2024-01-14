package application.controller;

import java.time.LocalDate;
import java.util.List;

import application.dao.MaintenanceDao;
import application.dto.CarDto;
import application.dto.MaintenanceDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class MaintenanceTableController {

    @FXML
    private TableView<MaintenanceDto> maintenanceTable;

    @FXML
    private TableColumn<MaintenanceDto, String> maintenanceTypeColumn;

    @FXML
    private TableColumn<MaintenanceDto, String> serviceCompanyColumn;

    @FXML
    private TableColumn<MaintenanceDto, LocalDate> dateColumn;

    @FXML
    private TableColumn<MaintenanceDto, Integer> mileageColumn;

    @FXML
    private TableColumn<MaintenanceDto, String> descriptionColumn;

    @FXML
    private TableColumn<MaintenanceDto, Integer> amountColumn;

    private ObservableList<MaintenanceDto> data = FXCollections.observableArrayList();

    
    public void setMaintenanceData(CarDto car) {
        maintenanceTable.getItems().clear();
        List<MaintenanceDto> maintenanceData = new MaintenanceDao().getAllByCar(car.getLicensePlate());
        if (maintenanceData != null) {
            data.addAll(maintenanceData);
            maintenanceTable.setItems(data);
        } else {
            System.err.println("A karbantartási adatok lekérése null-t adott eredményt.");
        }
    }
}
