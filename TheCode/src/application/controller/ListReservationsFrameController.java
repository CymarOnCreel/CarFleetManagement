package application.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

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
		LocalDate localStartDate = intervalStartDate.getValue();
		LocalDate localEndDate = intervalEndDate.getValue();
		ReservationDao reservationDao = new ReservationDao();
		List<ReservationDto> reservations = reservationDao.getReservationsByUserId(1L); // get user id here
		List<ReservationDto> reservationsFilteredByDate;
		if (localStartDate != null && localEndDate != null) {
			LocalDateTime startDate = localStartDate.atStartOfDay();
			LocalDateTime endDate = localEndDate.atTime(23, 59, 59); 
			reservationsFilteredByDate = reservations.stream()
					.filter(reservation -> reservation.getStartDate().isAfter(startDate)
							|| reservation.getStartDate().isEqual(startDate))
					.filter(reservation -> reservation.getEndDate().isBefore(endDate)
							|| reservation.getEndDate().isEqual(endDate))
					.collect(Collectors.toList());
		} else {
			reservationsFilteredByDate = reservations;
		}
		ObservableList<ReservationDto> reservationData = FXCollections.observableList(reservationsFilteredByDate);
		reservationsTable.setItems(reservationData);
		for (ReservationDto reservationDto : reservationData) {
			System.out.println(reservationDto.toStringWithNames());
		}

	
		addColumns();
		setupTableClickEvent();
		double rowHeight = 30.0; 
		double tableHeight = Math.min(reservationData.size() + 1, 10) * rowHeight;
		System.out.println(reservationData.size());
		System.out.println(tableHeight);
		reservationsTable.setPrefHeight(tableHeight);
		reservationsTable.setMaxHeight(tableHeight);
		System.out.println(reservationsTable.getPrefHeight()+" pref "+reservationsTable.getMaxHeight()+" max");
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
		TableColumn<ReservationDto, LocalDateTime> startDateColumn = new TableColumn<>("Start Date");
		startDateColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getStartDate()));
		startDateColumn.setCellFactory(col -> new TableCell<ReservationDto, LocalDateTime>() {
			private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

			@Override
			protected void updateItem(LocalDateTime date, boolean empty) {
				super.updateItem(date, empty);
				if (empty || date == null) {
					setText(null);
				} else {
					setText(formatter.format(date));
				}
			}
		});
		
		reservationsTable.getColumns().addAll(idColumn, nameColumn, plateColumn, startDateColumn);
	    reservationsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		for (TableColumn<ReservationDto, ?> column : reservationsTable.getColumns()) {
			column.setPrefWidth(computeColumnWidth(column));
		}

	}

	private <T> double computeColumnWidth(TableColumn<ReservationDto, T> column) {
		double maxWidth = 0.0;

		for (ReservationDto item : reservationsTable.getItems()) {
			TableCell<ReservationDto, T> cell = column.getCellFactory().call(column);
			if (cell != null) {
				Callback<TableColumn<ReservationDto, T>, TableCell<ReservationDto, T>> cellFactory = column
						.getCellFactory();
				TableCell<ReservationDto, T> currentCell = cellFactory.call(column);
				currentCell.itemProperty().setValue((T) item);

				Text text = new Text(String.valueOf(currentCell.getText()));
				double cellWidth = text.getBoundsInLocal().getWidth()+5;
				maxWidth = Math.max(maxWidth, cellWidth);
				
			}
		}

		return maxWidth + 10.0;
	}
	private void setupTableClickEvent() {
		reservationsTable.setOnMouseClicked(mouseEvent -> {
			if (mouseEvent.getClickCount() == 1) {
				ReservationDto selectedReservation = reservationsTable.getSelectionModel().getSelectedItem();
				if (selectedReservation != null) {
					openDetailsView(selectedReservation);
				}
			}
		});
	}

	private void openDetailsView(ReservationDto reservation) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/frame/ReservationDetailsFrame.fxml"));
		Parent root;
		try {
			root = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		ReservationDetailsController detailsController = loader.getController();
		detailsController.initialize(reservation);

		Stage detailsStage = new Stage();
		detailsStage.setScene(new Scene(root));
		detailsStage.setTitle("Reservation Details");
		detailsStage.show();
	}
}
