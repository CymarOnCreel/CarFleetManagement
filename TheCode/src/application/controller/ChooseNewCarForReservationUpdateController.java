package application.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import application.alert.AlertMessage;
import application.dao.CarDao;
import application.dao.PictureDao;
import application.dao.ReservationDao;
import application.dto.CarDto;
import application.dto.PictureDto;
import application.dto.ReservationDto;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class ChooseNewCarForReservationUpdateController implements Initializable {
	@FXML
	private GridPane gridPane;
	private Stage mainStage;

	private List<ReservationDto> listOfReservations;
	private List<CarDto> listOfCars;
	private List<ReservationDto> filteredReservations;
	private List<CarDto> filteredCarsList;
	private static final double CAR_IMAGE_WIDTH = 300;
	private static final double CAR_IMAGE_HEIGHT = 300;
	private static final String DEFAULT_IMAGE_PATH = "application/pictures/suv-removebg-preview.png";
	private static final double TEXT_BOX_HEIGHT = 50;

	private int tileColumns = 3;
	private ReservationDao reservationDao = new ReservationDao();
	private CarDao carDao;

	private ReservationDto reservation;

	public void setMainStage(Stage stage) {
		this.mainStage = stage;

	}

	public void setReservationForStage(ReservationDto reservation) {
		this.reservation = reservation;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setUpDaoObjects();
	}

	private void setUpDaoObjects() {
		reservationDao = new ReservationDao();
		carDao = new CarDao();
		Platform.runLater(() -> {
			listOfReservations = reservationDao.getAll();
			listOfCars = carDao.getAll();
			filteredReservations = FXCollections.observableArrayList();
			updateFilteredListOfReservationsBetweenDates();
			getCarsNotInReservation();
			if (filteredCarsList != null && filteredCarsList.size() != 0) {
				setWindowSizeByFilteredCarsNumber();
				centerStageOnScreen();
				populateWithCarImagesAvailebleForChange();
				makeStageUnresizable();
			} else {
				Stage stage = (Stage) gridPane.getScene().getWindow();
				stage.close();
				new AlertMessage().showConfirmationAlertMessage("Nincs szabad kocsi",
						"A megadott időszakra nincs szabad kocsi!!!");
			}
		});

	}

	private void updateFilteredListOfReservationsBetweenDates() {
		LocalDate startDatePicked = reservation.getStartDateTime().toLocalDate();
		LocalDate endDatePicked = reservation.getEndDateTime().toLocalDate();
		if (filteredReservations != null)
			filteredReservations.clear();
		filteredReservations = listOfReservations.stream()
				.filter(reservation -> isReservationBetweenDates(reservation, startDatePicked, endDatePicked))
				.collect(Collectors.toList());
	}

	private boolean isReservationBetweenDates(ReservationDto reservation, LocalDate startDate, LocalDate endDate) {
		LocalDate reservationStartDate = reservation.getStartDateTime().toLocalDate();
		LocalDate reservationEndDate = reservation.getEndDateTime().toLocalDate();
		return (reservationEndDate.isAfter(startDate) || reservationEndDate.isEqual(startDate))
				&& (reservationStartDate.isBefore(endDate) || reservationStartDate.isEqual(endDate));
	}

	private void getCarsNotInReservation() {
		filteredCarsList = listOfCars.stream()
				.filter(car -> car.isEnabled() && !filteredReservations.stream()
						.anyMatch(reservation -> reservation.getCar().getLicensePlate().equals(car.getLicensePlate())))
				.collect(Collectors.toList());
	}

	private void populateWithCarImagesAvailebleForChange() {
		for (int i = 0; i < filteredCarsList.size(); i++) {
			CarDto currentCar = filteredCarsList.get(i);
			String imagePath = getCarImageFilePath(currentCar);
			ImageView imageView = new ImageView(new Image(imagePath));
			imageView.setFitWidth(CAR_IMAGE_WIDTH);
			imageView.setPreserveRatio(true);
			VBox carInfoBox = new VBox();
			carInfoBox.setAlignment(Pos.CENTER);
			carInfoBox.getChildren().add(imageView);
			Text carInfoText = new Text(currentCar.getMake() + " " + currentCar.getModel() + "\n" + "Váltó: "
					+ currentCar.getTransmissionType() + "\n" + "Üzemanyag: " + currentCar.getFuel() + "\n" + "ülések száma: "
					+ currentCar.getSeats());
			carInfoText.getStyleClass().add("text-center-white");
			carInfoBox.getChildren().add(carInfoText);
//			Bounds bounds = carInfoText.getLayoutBounds();
//			double textBoxHeight=bounds.getHeight();
			carInfoBox.setOnMouseClicked(event -> {
				handleCarImageClick(currentCar);
			});
			int rowIndex = i / tileColumns;
			int colIndex = i % tileColumns;

			if (gridPane != null) {
				gridPane.add(carInfoBox, colIndex, rowIndex);
			} else {
			}
		}
	}

	private void setWindowSizeByFilteredCarsNumber() {
		int totalCarsAvaileble = filteredCarsList.size();
		if (totalCarsAvaileble < tileColumns)
			tileColumns = totalCarsAvaileble;
		int numberOfRows = (int) Math.ceil((double) totalCarsAvaileble / tileColumns);
		double windowWidth = tileColumns * CAR_IMAGE_WIDTH;
		double windowHeight = numberOfRows * (CAR_IMAGE_HEIGHT+TEXT_BOX_HEIGHT);
		Stage stage = (Stage) gridPane.getScene().getWindow();
		stage.setWidth(windowWidth);
		stage.setHeight(windowHeight);
		gridPane.getRowConstraints().clear();
		for (int i = 0; i < numberOfRows; i++) {
			RowConstraints rowConstraints = new RowConstraints();
			rowConstraints.setPrefHeight(CAR_IMAGE_HEIGHT);
			rowConstraints.setMaxHeight(CAR_IMAGE_HEIGHT);
			rowConstraints.setMinHeight(CAR_IMAGE_HEIGHT);
			gridPane.getRowConstraints().add(rowConstraints);
		}
	}

	private void handleCarImageClick(CarDto currentCar) {
		reservation.setCar(currentCar);
		Stage stage = (Stage) gridPane.getScene().getWindow();
		stage.close();
	}

	private String getCarImageFilePath(CarDto carChoosen) {
		PictureDao pictureDao = new PictureDao();
		String licensePlate = carChoosen.getLicensePlate();
		PictureDto imageOfCar = pictureDao.findImageByLicensePlate(licensePlate);
		if (imageOfCar != null) {
			return imageOfCar.getPicturePath();
		} else {
			return DEFAULT_IMAGE_PATH;
		}
	}

	private void makeStageUnresizable() {
		Stage stage = (Stage) gridPane.getScene().getWindow();
		stage.setResizable(false);
	}

	private void centerStageOnScreen() {
		try {
			Stage primaryStage = (Stage) gridPane.getScene().getWindow();
			double centerXPosition = Screen.getPrimary().getVisualBounds().getMinX()
					+ Screen.getPrimary().getVisualBounds().getWidth() / 2;
			double centerYPosition = Screen.getPrimary().getVisualBounds().getMinY()
					+ Screen.getPrimary().getVisualBounds().getHeight() / 2;
			primaryStage.setX(centerXPosition - primaryStage.getWidth() / 2);
			primaryStage.setY(centerYPosition - primaryStage.getHeight() / 2);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
