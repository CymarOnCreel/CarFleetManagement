package application.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Function;
import java.util.stream.Collectors;
import application.alert.AlertMessage;
import application.dao.CarDao;
import application.dao.ReservationDao;
import application.dto.CarDto;
import application.dto.ReservationDto;
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
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

public class NewReservationController implements Initializable {
	@FXML
	private ChoiceBox<String> carMake;

	@FXML
	private ChoiceBox<String> carCategory;

	@FXML
	private Button close;

	@FXML
	private DatePicker endDate;

	@FXML
	private ChoiceBox<String> fuelType;

	@FXML
	private ChoiceBox<Integer> numberOfSeats;

	@FXML
	private Button search;

	@FXML
	private DatePicker startDate;

	@FXML
	private ChoiceBox<String> transmissionType;

	@FXML
	private TableView<CarDto> availabelCarsTable;

	@FXML
	private Button clearFuelChoiceButton;

	@FXML
	private Button clearMakeChoiceButton;

	@FXML
	private Button clearCategoryChoiceButton;

	@FXML
	private Button clearSeatChoiceButton;

	@FXML
	private Button clearTransmissionChoiceButton;

	private ReservationDao reservationDao;
	private CarDao carDao;

	private ObservableList<ReservationDto> listOfReservations;
	private ObservableList<CarDto> listOfCars;
	private ObservableList<ReservationDto> filteredReservations;
	private List<CarDto> currentFilteredCarsList;
	private Map<ChoiceBox<?>, Object> selectedValues = new HashMap<>();
	private Image image = new Image("application/pictures/cancel.jpg");
	private ImageView imageView = new ImageView(image);

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setUpDatePickers();
		setUpDaoObjects();
		Platform.runLater(() -> {
			setUpChoiceBoxeDataAvailebleBetweenDates();
			setCancelButtonsImage();
		});
		setUpListeners();
	}

	private void setUpDatePickers() {
		LocalDate today = LocalDate.now();
		startDate.setValue(today);
		endDate.setValue(today);
		startDate.setDayCellFactory(disableInvalidDays(today));
		endDate.setDayCellFactory(disableInvalidDays(today));
	}

	private void setUpDaoObjects() {
		reservationDao = new ReservationDao();
		carDao = new CarDao();
		Platform.runLater(() -> {
			listOfReservations = FXCollections.observableArrayList(reservationDao.getAll());
			listOfCars = FXCollections.observableArrayList(carDao.getAll());
			setUpDataForComboboxes();
		});
	}
	private void setCancelButtonsImage() {
		setButtonImage(clearMakeChoiceButton);
		setButtonImage(clearFuelChoiceButton);
		setButtonImage(clearCategoryChoiceButton);
		setButtonImage(clearTransmissionChoiceButton);
		setButtonImage(clearSeatChoiceButton);
	}
	
	private void setUpDataForComboboxes() {
		updateFilteredListOfReservationsBetweenDates();
		updateCarsListOutsideOfDates();
		setUpChoiceBoxeDataAvailebleBetweenDates();
	}

	private void setUpChoiceBoxeDataAvailebleBetweenDates() {
		selectedValues.put(carMake, null);
		selectedValues.put(carCategory, null);
		selectedValues.put(fuelType, null);
		selectedValues.put(transmissionType, null);
		selectedValues.put(numberOfSeats, null);
		setUpChoiceBoxData(carMake, CarDto::getMake);
		setUpChoiceBoxData(carCategory, CarDto::getCategory);
		setUpChoiceBoxData(fuelType, CarDto::getFuel);
		setUpChoiceBoxData(transmissionType, CarDto::getTransmissionType);
		setUpChoiceBoxData(numberOfSeats, CarDto::getSeats);

	}

	private <T> void setUpChoiceBoxData(ChoiceBox<T> choiceBox, Function<CarDto, T> mapper) {
		List<T> data = currentFilteredCarsList.stream().map(mapper).distinct().collect(Collectors.toList());
		choiceBox.getItems().setAll(data);
	}

	private void setUpListeners() {
		startDate.valueProperty().addListener((obs, oldDate, newDate) -> {

			endDate.setDayCellFactory(disableInvalidDays(newDate));
			if (isDatePicked(endDate) && endDate.getValue().isBefore(newDate)) {
				endDate.setValue(newDate);
			}
			updateAllDataForComboboxesAfterDateChange();

		});
		endDate.valueProperty().addListener((obs, oldDate, newDate) -> {
			updateAllDataForComboboxesAfterDateChange();
		});
		carMake.valueProperty().addListener((obs, oldValue, newValue) -> {
			if (newValue != null) {
				onCarMakeSelected();
			}
		});
		carCategory.valueProperty().addListener((obs, oldValue, newValue) -> {
			if (newValue != null) {
				onCarCategorySelected();
			}
		});
		fuelType.valueProperty().addListener((obs, oldValue, newValue) -> {
			onFuelTypeSelected();
		});
		transmissionType.valueProperty().addListener((obs, oldValue, newValue) -> {
			onTransmissionSelected();
		});
		numberOfSeats.valueProperty().addListener((obs, oldValue, newValue) -> {
			onSeatsSelected();
		});

	}

	private void onCarMakeSelected() {
		String selectedMake = carMake.getValue();
		if (selectedMake != null) {
			currentFilteredCarsList = filterCarsByMake(currentFilteredCarsList, selectedMake);
			selectedValues.put(carMake, selectedMake);
			if (transmissionType.getValue() == null)
				updateTransmissionChoiceBoxData();
			if (carCategory.getValue() == null)
				updateCategoryTypeChoiceBoxData();
			if (fuelType.getValue() == null)
				updateFuelTypeChoiceBoxData();
			if (numberOfSeats.getValue() == null)
				updateSeatsChoiceBoxData();
		}
		carMake.setDisable(true);
	}

	private void onCarCategorySelected() {
		String selectedCategory = carCategory.getValue();
		if (selectedCategory != null) {
			currentFilteredCarsList = filterCarsByCategory(currentFilteredCarsList, selectedCategory);
			selectedValues.put(carCategory, selectedCategory);
			if (carMake.getValue() == null)
				updateMakeChoiceBoxData();
			if (transmissionType.getValue() == null)
				updateTransmissionChoiceBoxData();
			if (fuelType.getValue() == null)
				updateFuelTypeChoiceBoxData();
			if (numberOfSeats.getValue() == null)
				updateSeatsChoiceBoxData();
		}
		carCategory.setDisable(true);
	}

	private void onFuelTypeSelected() {
		String selectedFuelType = fuelType.getValue();
		if (selectedFuelType != null) {
			currentFilteredCarsList = filterCarsByFuelType(currentFilteredCarsList, selectedFuelType);
			selectedValues.put(fuelType, selectedFuelType);

			if (carMake.getValue() == null)
				updateMakeChoiceBoxData();
			if (carCategory.getValue() == null)
				updateCategoryTypeChoiceBoxData();
			if (transmissionType.getValue() == null)
				updateTransmissionChoiceBoxData();
			if (numberOfSeats.getValue() == null)
				updateSeatsChoiceBoxData();
		}
		fuelType.setDisable(true);
	}

	private void onTransmissionSelected() {
		String selecTedTransmission = transmissionType.getValue();
		if (selecTedTransmission != null) {
			currentFilteredCarsList = filterCarsByTransmissionType(currentFilteredCarsList, selecTedTransmission);
			selectedValues.put(transmissionType, selecTedTransmission);
			if (carMake.getValue() == null)
				updateMakeChoiceBoxData();
			if (carCategory.getValue() == null)
				updateCategoryTypeChoiceBoxData();
			if (fuelType.getValue() == null)
				updateFuelTypeChoiceBoxData();
			if (numberOfSeats.getValue() == null)
				updateSeatsChoiceBoxData();
		}
		transmissionType.setDisable(true);
	}

	private void onSeatsSelected() {
		Integer selecTedSeatsNumber = numberOfSeats.getValue();
		if (selecTedSeatsNumber != null) {
			currentFilteredCarsList = filterCarsByseats(currentFilteredCarsList, selecTedSeatsNumber);
			selectedValues.put(numberOfSeats, selecTedSeatsNumber);

			if (carMake.getValue() == null)
				updateMakeChoiceBoxData();
			if (carCategory.getValue() == null)
				updateCategoryTypeChoiceBoxData();
			if (fuelType.getValue() == null)
				updateFuelTypeChoiceBoxData();
			if (numberOfSeats.getValue() == null)
				updateTransmissionChoiceBoxData();
		}
		numberOfSeats.setDisable(true);

	}

	private void updateAllDataForComboboxesAfterDateChange() {
		updateFilteredListOfReservationsBetweenDates();
		updateCarsListOutsideOfDates();
		carMake.setDisable(false);
		carCategory.setDisable(false);
		transmissionType.setDisable(false);
		fuelType.setDisable(false);
		numberOfSeats.setDisable(false);
		setUpChoiceBoxeDataAvailebleBetweenDates();
	}

	private List<CarDto> filterCarsByMake(List<CarDto> cars, String make) {
		return cars.stream().filter(car -> car.getMake().equals(make)).collect(Collectors.toList());
	}

	private void updateMakeChoiceBoxData() {
		updateChoiceBox(currentFilteredCarsList, carMake, CarDto::getMake);
		if (selectedValues.containsKey(carMake) && selectedValues.get(carMake) != null) {
			carMake.setValue(selectedValues.get(carMake).toString());
		}

	}

	private void updateCategoryTypeChoiceBoxData() {
		updateChoiceBox(currentFilteredCarsList, carCategory, CarDto::getCategory);
		if (selectedValues.containsKey(carCategory) && selectedValues.get(carCategory) != null) {
			carCategory.setValue(selectedValues.get(carCategory).toString());
		}
	}

	private List<CarDto> filterCarsByCategory(List<CarDto> cars, String selectedCategory) {
		return cars.stream().filter(car -> car.getCategory().equals(selectedCategory)).collect(Collectors.toList());
	}

	private void updateTransmissionChoiceBoxData() {
		updateChoiceBox(currentFilteredCarsList, transmissionType, CarDto::getTransmissionType);
		if (selectedValues.containsKey(transmissionType) && selectedValues.get(transmissionType) != null) {
			transmissionType.setValue(selectedValues.get(transmissionType).toString());
		}
	}

	private List<CarDto> filterCarsByTransmissionType(List<CarDto> cars, String selecTedTransmission) {
		return cars.stream().filter(car -> car.getTransmissionType().equals(selecTedTransmission))
				.collect(Collectors.toList());
	}

	private void updateFuelTypeChoiceBoxData() {
		updateChoiceBox(currentFilteredCarsList, fuelType, CarDto::getFuel);
		if (selectedValues.containsKey(fuelType) && selectedValues.get(fuelType) != null) {
			fuelType.setValue(selectedValues.get(fuelType).toString());
		}
	}

	private List<CarDto> filterCarsByFuelType(List<CarDto> cars, String selectedFuelType) {
		return cars.stream().filter(car -> car.getFuel().equals(selectedFuelType)).collect(Collectors.toList());
	}

	private void updateSeatsChoiceBoxData() {
		updateChoiceBox(currentFilteredCarsList, numberOfSeats, CarDto::getSeats);
		if (selectedValues.containsKey(numberOfSeats) && selectedValues.get(numberOfSeats) != null) {
			numberOfSeats.setValue((Integer) selectedValues.get(String.valueOf(numberOfSeats)));
		}
	}

	private List<CarDto> filterCarsByseats(List<CarDto> cars, Integer selecTedSeatsNumber) {
		return cars.stream().filter(car -> car.getSeats() == (selecTedSeatsNumber)).collect(Collectors.toList());
	}

	private <T> void updateChoiceBox(List<CarDto> cars, ChoiceBox<T> choiceBox, Function<CarDto, T> mapper) {
		List<T> data = cars.stream().map(mapper).distinct().collect(Collectors.toList());
		choiceBox.getItems().setAll(data);
	}

	private void updateFilteredListOfReservationsBetweenDates() {
		LocalDate startDatePicked = startDate.getValue();
		LocalDate endDatePicked = endDate.getValue();
		if (filteredReservations != null)
			filteredReservations.removeAll();
		filteredReservations = listOfReservations
				.filtered(reservation -> isReservationBetweenDates(reservation, startDatePicked, endDatePicked));
	}

	private boolean isReservationBetweenDates(ReservationDto reservation, LocalDate startDate, LocalDate endDate) {
		LocalDate reservationStartDate = reservation.getStartDateTime().toLocalDate();
		LocalDate reservationEndDate = reservation.getEndDateTime().toLocalDate();
		return (reservationEndDate.isAfter(startDate) || reservationEndDate.isEqual(startDate))
				&& (reservationStartDate.isBefore(endDate) || reservationStartDate.isEqual(endDate));
	}

	@FXML
	void setCarPickerTable(ActionEvent event) {

		if (isDatePicked(startDate) && isDatePicked(endDate) && (startDate.getValue().isBefore(endDate.getValue())
				|| startDate.getValue().isEqual(endDate.getValue()))) {
			Platform.runLater(() -> {
				populateTableView(currentFilteredCarsList);
				updateColumnWidths();
				initializeTableView();
			});
		} else {
			new AlertMessage().requiredFieldsEmpty("Hiányos Adatok", "A kötelező adatok nem lettek kitöltve");
		}
	}

	private void populateTableView(List<CarDto> listOfFilteredCars) {
		ObservableList<CarDto> filteredCars = FXCollections.observableArrayList();
		filteredCars.clear();
		filteredCars.addAll(listOfFilteredCars);
		availabelCarsTable.setItems(filteredCars);
	}

	private void initializeTableView() {
		addColumnsToTable();
		setupTableClickEvent();
		setTableHeight();
		availabelCarsTable.setVisible(true);

	}

	private void setupTableClickEvent() {
		availabelCarsTable.setOnMouseClicked(mouseEvent -> {
			if (mouseEvent.getClickCount() == 1) {
				CarDto carChoosen = availabelCarsTable.getSelectionModel().getSelectedItem();
				if (carChoosen != null) {
					showSaveFramePage(carChoosen);
				}
			}
		});

	}

	private void showSaveFramePage(CarDto carChosen) {
		try {
			FXMLLoader loader = new FXMLLoader(
					getClass().getResource("/application/frame/SaveNewReservationFrame.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/application/util/application.css").toExternalForm());
			Stage saveReservationStage = new Stage();
			saveReservationStage.setTitle("Save reservation");
			saveReservationStage.initModality(Modality.APPLICATION_MODAL);
			SaveNewReservationFrameController saveReservationController = loader.getController();
			saveReservationController.setMainStage(saveReservationStage);
			saveReservationController.setDataForStage(carChosen, startDate.getValue().atStartOfDay(),
					endDate.getValue().atStartOfDay());
			saveReservationStage.setScene(scene);
			saveReservationStage.showAndWait();
			refreshTableData();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@SuppressWarnings({ "rawtypes", "unused" })
	private void addColumnsToTable() {
		availabelCarsTable.getColumns().clear();
		TableColumn<CarDto, String> licencePlate = new TableColumn<>("Licence Plate");
		licencePlate.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLicensePlate()));
		TableColumn<CarDto, String> make = new TableColumn<>("Car Make");
		make.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMake()));
		TableColumn<CarDto, String> category = new TableColumn<>("Car Category");
		category.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCategory()));
		TableColumn<CarDto, String> transmission = new TableColumn<>("Transmission");
		transmission
				.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTransmissionType()));
		TableColumn<CarDto, String> fuelType = new TableColumn<>("Fuel");
		fuelType.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFuel()));
		TableColumn<CarDto, Integer> numberOfSeats = new TableColumn<>("Seat numbers");
		numberOfSeats.setCellValueFactory(cellData -> new SimpleObjectProperty(cellData.getValue().getSeats()));
		availabelCarsTable.getColumns().addAll(licencePlate, make, category, transmission, fuelType, numberOfSeats);
		availabelCarsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	}

	private void setTableHeight() {
		double rowHeight = 30.0;
		double tableHeight = Math.min(availabelCarsTable.getItems().size() + 1, 10) * rowHeight;
		availabelCarsTable.setPrefHeight(tableHeight);
		availabelCarsTable.setMaxHeight(tableHeight);
	}

	private void updateColumnWidths() {
		for (TableColumn<CarDto, ?> column : availabelCarsTable.getColumns()) {
			column.setPrefWidth(computeColumnWidth(column));
		}
	}

	private <T> double computeColumnWidth(TableColumn<CarDto, T> column) {
		double maxWidth = 0.0;

		for (CarDto item : availabelCarsTable.getItems()) {
			TableCell<CarDto, T> cell = column.getCellFactory().call(column);
			if (cell != null) {
				Callback<TableColumn<CarDto, T>, TableCell<CarDto, T>> cellFactory = column.getCellFactory();
				TableCell<CarDto, T> currentCell = cellFactory.call(column);
				currentCell.itemProperty().setValue((T) item);

				Text text = new Text(String.valueOf(currentCell.getText()));
				double cellWidth = text.getBoundsInLocal().getWidth() + 5;
				maxWidth = Math.max(maxWidth, cellWidth);

			}
		}

		return maxWidth + 10.0;
	}

	private Callback<DatePicker, DateCell> disableInvalidDays(LocalDate date) {
		return datePicker -> new DateCell() {
			@Override
			public void updateItem(LocalDate item, boolean empty) {
				super.updateItem(item, empty);
				if (item.isBefore(date)) {
					setDisable(true);
					setStyle("-fx-background-color: #ff0000;");
				}
			}
		};
	}

	private boolean isDatePicked(DatePicker date) {
		return date.getValue() != null;
	}

	private void updateCarsListOutsideOfDates() {
		currentFilteredCarsList = listOfCars.stream()
				.filter(car -> car.isEnabled() &&  !filteredReservations.stream()
						.anyMatch(reservation -> reservation.getCar().getLicensePlate().equals(car.getLicensePlate())))
				.collect(Collectors.toList());
	}

	@FXML
	void clearFuelChoice(ActionEvent event) {
		fuelType.setValue(null);
		selectedValues.put(fuelType, null);
		updateCarsListWithSelectedChoices();
		updateChoiceBox(currentFilteredCarsList, fuelType, CarDto::getFuel);
		fuelType.setDisable(false);

	}

	@FXML
	void clearMakeChoice(ActionEvent event) {
		carMake.setValue(null);
		selectedValues.put(carMake, null);
		updateCarsListWithSelectedChoices();
		updateChoiceBox(currentFilteredCarsList, carMake, CarDto::getMake);
		carMake.setDisable(false);
	}

	@FXML
	void clearCategoryChoice(ActionEvent event) {
		carCategory.setValue(null);
		selectedValues.put(carCategory, null);
		updateCarsListWithSelectedChoices();
		updateChoiceBox(currentFilteredCarsList, carCategory, CarDto::getCategory);
		carCategory.setDisable(false);
	}

	@FXML
	void clearSeatChoice(ActionEvent event) {
		numberOfSeats.setValue(null);
		selectedValues.put(numberOfSeats, null);
		updateCarsListWithSelectedChoices();
		updateChoiceBox(currentFilteredCarsList, numberOfSeats, CarDto::getSeats);
		numberOfSeats.setDisable(false);
	}

	@FXML
	private void clearTransmissionChoice(ActionEvent event) {
		transmissionType.setValue(null);
		selectedValues.put(transmissionType, null);
		updateCarsListWithSelectedChoices();
		updateChoiceBox(currentFilteredCarsList, transmissionType, CarDto::getTransmissionType);
		transmissionType.setDisable(false);
	}

	private void updateCarsListWithSelectedChoices() {

		updateCarsListOutsideOfDates();
		List<CarDto> updatedList = new ArrayList<>(currentFilteredCarsList);
		if (selectedValues.get(carMake) != null) {
			updatedList = filterCarsByMake(updatedList, selectedValues.get(carMake).toString());
		}
		if (selectedValues.get(carCategory) != null) {
			updatedList = filterCarsByCategory(updatedList, selectedValues.get(carCategory).toString());
		}
		if (selectedValues.get(transmissionType) != null) {
			updatedList = filterCarsByTransmissionType(updatedList, selectedValues.get(transmissionType).toString());
		}
		if (selectedValues.get(numberOfSeats) != null) {
			updatedList = filterCarsByseats(updatedList, (Integer) selectedValues.get(numberOfSeats));
		}
		if (selectedValues.get(fuelType) != null) {
			updatedList = filterCarsByFuelType(updatedList, selectedValues.get(fuelType).toString());
		}
		currentFilteredCarsList = updatedList;
	}

	private void setButtonImage(Button button) {
		ImageView buttonImageView = new ImageView(image);
		buttonImageView.setFitHeight(button.getPrefHeight());
		buttonImageView.setFitWidth(button.getPrefWidth());
		Circle clip = new Circle(button.getPrefWidth() / 2, button.getPrefHeight() / 2,
				Math.min(button.getPrefWidth(), button.getPrefHeight()) / 2);

		imageView.setClip(clip);
		button.setGraphic(buttonImageView);
	}

	public void refreshTableData() {
		listOfReservations = FXCollections.observableArrayList(reservationDao.getAll());
		listOfCars = FXCollections.observableArrayList(carDao.getAll());
		updateFilteredListOfReservationsBetweenDates();
		updateCarsListOutsideOfDates();
		setUpChoiceBoxeDataAvailebleBetweenDates();
		Platform.runLater(() -> {
			populateTableView(currentFilteredCarsList);
			updateColumnWidths();
			initializeTableView();
		});
	}
	@FXML
	void close(ActionEvent event) {
		Node source = (Node) event.getSource();
		Stage stage = (Stage) source.getScene().getWindow();
		stage.close();
	}
}
// To-DO
// start date can start only after or same day as presentday -- DONE
// end-date can only be start day or after it --- DONE
// populate choiceboxes depending on date chossen. --Done
// get cars in a table depending on filtering -Done
// add a alertbox if a car is chosen from table asking for a description -Done
// save the reservation in database -Done
// return to main page - Done