package application.controller;

import java.io.IOException;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

public class ChooseNewCarForReservationUpdateController implements Initializable {
	@FXML
	private TilePane tilePane;
	private Stage mainStage;

	private List<ReservationDto> listOfReservations;
	private List<CarDto> listOfCars;
	private List<ReservationDto> filteredReservations;
	private List<CarDto> filteredCarsList;

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
//			System.out.println(listOfReservations.size());
			listOfCars = carDao.getAll();
//			System.out.println(listOfCars.size());
//			System.out.println(reservation.toString());
			filteredReservations = FXCollections.observableArrayList();
			updateFilteredListOfReservationsBetweenDates();

//			System.out.println("fil res: " + filteredReservations.size());
			getCarsNotInReservation();
//			System.out.println("cars: " + filteredCarsList.size());
//			filteredCarsList.stream().forEach(car -> System.out.println(reservation.getCar().getLicensePlate()));
			populateWithCarImagesAvailebleForChange();
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

		if (filteredCarsList != null && filteredCarsList.size() != 0) {
			for (int i = 0; i < filteredCarsList.size(); i++) {
				CarDto currentCar = filteredCarsList.get(i);
				ImageView imageView = new ImageView(new Image(getCarImageFilePath(filteredCarsList.get(i))));
				System.out.println(imageView);
				imageView.setFitWidth(200);
				imageView.setPreserveRatio(true);

				imageView.setOnMouseClicked(event -> {
					handleCarImageClick(currentCar);
				});
				if (tilePane != null) {
					tilePane.getChildren().add(imageView);
				} else {
					System.err.println("TilePane is null");
				}
			}
		} else {
			new AlertMessage().showConfirmationAlertMessage("No cars Availeble",
					"There are no cars availeble in the period of time selected!!!");
		}
	}

	private void handleCarImageClick(CarDto currentCar) {
		reservation.setCar(currentCar);
		Stage stage = (Stage) tilePane.getScene().getWindow();
		stage.close();
	}

	private String getCarImageFilePath(CarDto carChoosen) {
		PictureDao pictureDao = new PictureDao();
		String licensePlate = carChoosen.getLicensePlate();
		PictureDto imageOfCar = pictureDao.findImageByLicensePlate(licensePlate);
		if (imageOfCar != null) {
			return imageOfCar.getPicturePath();
		} else {
			return "/application/pictures/suv-removebg-preview.png";
		}
	}
}
