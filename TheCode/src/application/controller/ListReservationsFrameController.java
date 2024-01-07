package application.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import application.dao.ReservationDao;
import application.dto.ReservationDto;
import application.util.UserSession;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ListReservationsFrameController implements Initializable {
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
	private ReservationDao reservationDao;
	List<ReservationDto> reservations;
	private ObservableList<ReservationDto> reservationData = FXCollections.observableArrayList();
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	public ListReservationsFrameController() {
		this.reservationDao = new ReservationDao();
	}

	private Long loggedInEmployeeId = (long) UserSession.getUserId();
//	private Long loggedInEmployeeId = 1L;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setTable();

	}

	public void setTable() {
		Platform.runLater(() -> {
			clearTable();
			updateTableView(getAllReservationsOfUserBetweenDates(loggedInEmployeeId));
		});
	}

	public void updateTableView(List<ReservationDto> filteredReservations) {
		populateTableView(filteredReservations);
		updateColumnWidths();
		initializeTableView();
	}

	private void populateTableView(List<ReservationDto> reservations) {
		reservationData.clear();
		reservationData.addAll(reservations);

		reservationsTable.setItems(reservationData);
	}

	private void initializeTableView() {
		addColumns();
		setupTableClickEvent();
		setTableHeight();
		reservationsTable.setVisible(true);

	}

	@FXML
	public void closeWindow(ActionEvent event) {
		Node source = (Node) event.getSource();
		Stage stage = (Stage) source.getScene().getWindow();
		stage.close();
	}

	@FXML
	public void handleSearchButton(ActionEvent event) {
		clearTable();
		updateTableView(getAllReservationsOfUserBetweenDates(loggedInEmployeeId));
	}

	public void clearTable() {
		reservationData.clear();
		reservationsTable.getItems().clear();
		reservationsTable.refresh();
	}

	public List<ReservationDto> getAllReservationsOfUserBetweenDates(Long userId) {
		LocalDate localStartDate = intervalStartDate.getValue();
		LocalDate localEndDate = intervalEndDate.getValue();
		reservations = reservationDao.getReservationsByUserId(userId); 
		List<ReservationDto> reservationsFilteredByDate;
		if (localStartDate != null && localEndDate != null) {
			LocalDateTime startDate = localStartDate.atStartOfDay();
			LocalDateTime endDate = localEndDate.atTime(23, 59, 59);
			reservationsFilteredByDate = reservations.stream()
					.filter(reservation -> reservation.getStartDateTime().isAfter(startDate)
							|| reservation.getStartDateTime().isEqual(startDate))
					.filter(reservation -> reservation.getEndDateTime().isBefore(endDate)
							|| reservation.getEndDateTime().isEqual(endDate))
					.collect(Collectors.toList());
		} else {
			reservationsFilteredByDate = reservations;
		}
		return reservationsFilteredByDate;
	}

	@SuppressWarnings("unchecked")
	private void addColumns() {
		reservationsTable.getColumns().clear();
		TableColumn<ReservationDto, String> nameColumn = new TableColumn<>("Felhaználó neve:");
		nameColumn.setCellValueFactory(
				cellData -> new SimpleStringProperty(cellData.getValue().getEmployee().getFullName()));
		TableColumn<ReservationDto, String> plateColumn = new TableColumn<>("kocsi");
		plateColumn.setCellValueFactory(
				cellData -> new SimpleStringProperty(cellData.getValue().getCar().getLicensePlate()));
		TableColumn<ReservationDto, LocalDateTime> startDateColumn = new TableColumn<>("Foglalás kezdete");
		startDateColumn
				.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getStartDateTime()));
		startDateColumn.setCellFactory(col -> new TableCell<ReservationDto, LocalDateTime>() {
			@Override
			protected void updateItem(LocalDateTime date, boolean empty) {
				super.updateItem(date, empty);
				if (empty || date == null) {
					setText(null);
				} else {
					setText(formatDate(date));
				}
			}

		});
		TableColumn<ReservationDto, LocalDateTime> endDateColumn = new TableColumn<>("Foglalás vége");
		endDateColumn
				.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getEndDateTime()));
		endDateColumn.setCellFactory(col -> new TableCell<ReservationDto, LocalDateTime>() {
			@Override
			protected void updateItem(LocalDateTime date, boolean empty) {
				super.updateItem(date, empty);
				if (empty || date == null) {
					setText(null);
				} else {
					setText(formatDate(date));
				}
			}

		});
		TableColumn<ReservationDto, String> descriptionColumn = new TableColumn<>("Megjegyzés");
		descriptionColumn
				.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));
		TableColumn<ReservationDto, String> statusColumn = new TableColumn<>("Státusz");
		statusColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().isDeletedToString()));
		reservationsTable.getColumns().addAll(nameColumn, plateColumn, startDateColumn, endDateColumn,
				descriptionColumn, statusColumn);
		reservationsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

	}

	private TableCell<ReservationDto, LocalDateTime> createDateTimeCell(DateTimeFormatter formatter) {
		return new TableCell<ReservationDto, LocalDateTime>() {
			@Override
			protected void updateItem(LocalDateTime date, boolean empty) {
				super.updateItem(date, empty);
				if (empty || date == null) {
					setText(null);
				} else {
					setText(formatter.format(date));
				}
			}
		};
	}

	private String formatDate(LocalDateTime date) {
		return formatter.format(date);
	}

	private void setTableHeight() {
		double rowHeight = 30.0;
		double tableHeight = Math.min(reservationsTable.getItems().size() + 1, 10) * rowHeight;
		reservationsTable.setPrefHeight(tableHeight);
		reservationsTable.setMaxHeight(tableHeight);
	}

	private void updateColumnWidths() {
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
				double cellWidth = text.getBoundsInLocal().getWidth() + 5;
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
		detailsController.setReservationListController(this);
		detailsController.initialize(reservation);
		Scene scene=new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/application/util/application.css").toExternalForm());
		Stage detailsStage = new Stage();
		detailsStage.setScene(scene);
		detailsStage.setTitle("Foglalás részletei");
		detailsStage.initModality(Modality.APPLICATION_MODAL);
		detailsStage.showAndWait();

	}

}
