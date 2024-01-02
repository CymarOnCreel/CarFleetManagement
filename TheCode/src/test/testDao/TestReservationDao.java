package test.testDao;

import static org.junit.Assert.assertNotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.transaction.Transactional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import application.dao.ReservationDao;
import application.dto.CarDto;
import application.dto.EmployeeDto;
import application.dto.ReservationDto;

public class TestReservationDao {
	private EntityManager entityManager;
	private ReservationDao reservationDao;

	@BeforeEach
	public void setUp() {
		entityManager = Persistence.createEntityManagerFactory("carfleet_manager").createEntityManager();
		reservationDao = new ReservationDao();
		reservationDao.setEntityManager(entityManager);
	}

	@AfterEach
	public void tearDown() {
		entityManager.close();
	}

	@Test
	@Transactional
	public void testGetReservationByUserId() {
		insertTestData();

		Long userId = -8L;
		List<ReservationDto> reservations = reservationDao.getReservationsByUserId(userId);
		assertNotNull(reservations);
	}

	private void insertTestData() {
		EmployeeDto employee = new EmployeeDto(-8, "Smith", "John", "johnsmith@gmail.com", "password", "11111111",
				"USER", LocalDate.now().minusYears(1), LocalDate.now().minusMonths(2), true);

		CarDto car = new CarDto("Test118", "Toyota", "Camry", "Szédán", "Benzin", 4, 5, "Automata", 50000, 10000,
				LocalDate.now().plusYears(1), "Test Site", "1", true);

		ReservationDto reservation = new ReservationDto();
		reservation.setEmployee(employee);
		reservation.setCar(car);
		reservation.setStartDateTime(LocalDateTime.now().minusDays(5));
		reservation.setEndDateTime(LocalDateTime.now().minusDays(3));
		reservation.setDescription("Test reservation");
		reservation.setCreatedAt(LocalDateTime.now().minusDays(6));

		entityManager.persist(employee);
		entityManager.persist(car);
		entityManager.persist(reservation);
	}

}
