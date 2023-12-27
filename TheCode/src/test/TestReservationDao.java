package test;

import static org.junit.Assert.assertNotNull;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

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
	        // Create an entity manager for testing
	        entityManager = Persistence.createEntityManagerFactory("carfleet_manager").createEntityManager();
	        reservationDao = new ReservationDao();
	        reservationDao.setEntityManager(entityManager);
	        entityManager.getTransaction().begin();
	        EmployeeDto employee=new EmployeeDto(1,"John");
//	        employee.setLastName("John");
//	        EmployeeDto employee = new EmployeeDto(
//	        		1,
//	        		"Smith",
//	        		"John",
//	        		"johnsmith@gmail.com",
//	        		"password",
//	        		"11111111",
//	        		"USER",
//	        		LocalDate.now().minusYears(1),
//	        		LocalDate.now().minusMonths(2),
//	        		1);
	        
//	        id_employee int(11) AI PK 
//	        last_name varchar(45) 
//	        first_name varchar(45) 
//	        email varchar(45) 
//	        password varchar(45) 
//	        driver_license varchar(45) 
//	        role_name varchar(45) 
//	        created_at date 
//	        updated_at date 
//	        enabled tinyint(1)
	        CarDto car = new CarDto(
	                "ABC123",
	                "Toyota",
	                "Camry",
	                "Sedan",
	                "Gasoline",
	                4,
	                5,
	                "Automatic",
	                50000,
	                10000,
	                LocalDate.now().plusYears(1),
	                "Test Site",
	                "Available",
	                true
	        );

	        ReservationDto reservation = new ReservationDto();
	        reservation.setEmployee(employee);
	        reservation.setCar(car);
	        reservation.setStartDate(new Date());
	        reservation.setEndDate(new Date());
	        reservation.setDescription("Test reservation");
	        reservation.setCreatedAt(new Date());
	        entityManager.persist(employee);
	        entityManager.persist(car);
	        entityManager.persist(reservation);

	        entityManager.getTransaction().commit();
	    }

	    @AfterEach
	    public void tearDown() {
	        entityManager.getTransaction().begin();
	        entityManager.createQuery("DELETE FROM ReservationDto").executeUpdate();
	        entityManager.createQuery("DELETE FROM CarDto").executeUpdate();
	        entityManager.createQuery("DELETE FROM EmployeeDto").executeUpdate();
	        entityManager.getTransaction().commit();
	        entityManager.close();
	    }
	    
	    @Test
	    public void testGetReservationByUserId() {
	    	Long userId=1l;
	    	List<ReservationDto> reservations = reservationDao.getReservationsByUserId(userId);
	    	assertNotNull(reservations);
	    }
}
