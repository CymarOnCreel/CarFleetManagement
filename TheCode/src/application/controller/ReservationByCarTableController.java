package application.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import application.dao.ReservationDao;
import application.dto.CarDto;
import application.dto.EmployeeDto;
import application.dto.ReservationDto;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ReservationByCarTableController {

    @FXML
    private TableView<ReservationTableEntry> reservationTable;

    @FXML
    private TableColumn<ReservationTableEntry, String> startDateColumn;

    @FXML
    private TableColumn<ReservationTableEntry, String> endDateColumn;

    @FXML
    private TableColumn<ReservationTableEntry, String> employeeColumn;

    @FXML
    private TableColumn<ReservationTableEntry, String> descriptionColumn;

    private ObservableList<ReservationTableEntry> data = FXCollections.observableArrayList();

    public void setReservationData(CarDto car) {
        reservationTable.getItems().clear();
        List<ReservationDto> reservationData = new ReservationDao().getReservationsByCarLicencePlate(car.getLicensePlate());
        if (reservationData != null) {
            data.addAll(convertToReservationTableEntries(reservationData));
            reservationTable.setItems(data);
        }
    }

    private ObservableList<ReservationTableEntry> convertToReservationTableEntries(List<ReservationDto> reservations) {
        ObservableList<ReservationTableEntry> entries = FXCollections.observableArrayList();
        for (ReservationDto reservation : reservations) {
            entries.add(new ReservationTableEntry(reservation));
        }
        return entries;
    }

    public static class ReservationTableEntry {
        private final SimpleStringProperty formattedStartDate;
        private final SimpleStringProperty formattedEndDate;
        private final SimpleStringProperty employeeName;
        private final String description;

        public ReservationTableEntry(ReservationDto reservation) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd.");

            this.formattedStartDate = new SimpleStringProperty(reservation.getStartDateTime().format(formatter));
            this.formattedEndDate = new SimpleStringProperty(reservation.getEndDateTime().format(formatter));
            this.employeeName = new SimpleStringProperty(reservation.getEmployee().getFullNameHun());
            this.description = reservation.getDescription();
        }

        public String getFormattedStartDate() {
            return formattedStartDate.get();
        }

        public String getFormattedEndDate() {
            return formattedEndDate.get();
        }

        public String getEmployeeName() {
            return employeeName.get();
        }

        public String getDescription() {
            return description;
        }
    }
}
