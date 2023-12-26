package application.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import application.dao.ReservationDao;
import application.dto.ReservationDto;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class ListReservationsFrameController {
	@FXML
	private Button closeWindow;

	@FXML
	private DatePicker intervalEndDate;

	@FXML
	private DatePicker intervalStartDate;

	@FXML
	private TableView<ReservationDto> reservationsTable;

	@FXML
	private Button search;

	@FXML
	public void closeWindow(ActionEvent event) {
		Node source = (Node) event.getSource();
		Stage stage = (Stage) source.getScene().getWindow();
		stage.close();
	}

	@FXML
	void getAllReservationsBetweenDates(ActionEvent event) {
		// TO-DO create method to list all reservationsByUser in a table format
		// add datefilter

		LocalDate localStartDate = intervalStartDate.getValue(); //
		LocalDate localEndDate = intervalEndDate.getValue(); 
		ReservationDao reservationDao = new ReservationDao();
		List<ReservationDto> reservations = reservationDao.getReservationsByUserId(1l);// get user id here
		List<ReservationDto> reservationsFilteredByDate;
		if (localStartDate != null && localEndDate != null) {
		Date startDate = Date.from(localStartDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
		Date endDate = Date.from(localEndDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
			reservationsFilteredByDate = reservations.stream()
			    .filter(date -> date.getStartDate().after(startDate) || date.getStartDate().equals(startDate))
			    .filter(date -> date.getEndDate().before(endDate) || date.getEndDate().equals(endDate))
			    .collect(Collectors.toList());		}
		else {
			reservationsFilteredByDate=reservations;
		}
		ObservableList<ReservationDto> reservationData = FXCollections.observableList(reservationsFilteredByDate);
		reservationsTable.setItems(reservationData);
		addColumns();
		reservationsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		styleTableView();
		reservationsTable.setVisible(true);
		reservationsTable.setManaged(true);
	}

	@SuppressWarnings("unchecked")
	private void addColumns() {
		reservationsTable.getColumns().clear();
		TableColumn<ReservationDto, Integer> idColumn = new TableColumn<>("Reservation ID");
		idColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getId()));
		TableColumn<ReservationDto, String> nameColumn = new TableColumn<>("Employee Name");
		nameColumn.setCellValueFactory(
				cellData -> new SimpleStringProperty(cellData.getValue().getEmployee().getLastName()));
		TableColumn<ReservationDto, String> plateColumn = new TableColumn<>("Car");
		plateColumn.setCellValueFactory(
				cellData -> new SimpleStringProperty(cellData.getValue().getCar().getLicensePlate()));
		TableColumn<ReservationDto, Date> startDateColumn=new TableColumn<>("Start Date");
		 startDateColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getStartDate()));
		    startDateColumn.setCellFactory(col -> new TableCell<ReservationDto, Date>() {
		        private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
		        @Override
		        protected void updateItem(Date date, boolean empty) {
		            super.updateItem(date, empty);
		            if (empty || date == null) {
		                setText(null);
		            } else {
		                setText(sdf.format(date));
		            }
		        }
		    });
		reservationsTable.getColumns().addAll(idColumn, nameColumn, plateColumn, startDateColumn);
	}

	private void styleTableView() {
		reservationsTable.setStyle("-fx-font-size: 14px;");
		reservationsTable.getStyleClass().add("my-table");
	}
}
