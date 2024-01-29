package test.testDao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import application.dao.CarDao;
import application.dto.CarDto;
import application.dto.CategoryDto;
import application.dto.MakeDto;
import application.dto.ModelDto;
import application.dto.SiteDto;

public class TestCarDao {
	
	CarDto testCar;
	
	@BeforeEach
	public void createTestCarDto() {
		testCar = new CarDto("TEST01", new MakeDto("Toyota", null), new ModelDto("Camry", null),
				new CategoryDto("Szedán", null), "Benzin", 4, 5, "Kézi", 50000, 10000, LocalDate.now().plusYears(1),
				new SiteDto("Raktár", "Budapest", 50, null, null, null, null, LocalDate.now(), null, true), "1", true);
	}

	public void saveTestCarToDatabase() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("carfleet_manager");
		EntityManager entityManager = factory.createEntityManager();

		entityManager.getTransaction().begin();
		entityManager.persist(testCar);
		entityManager.getTransaction().commit();
		entityManager.close();
		factory.close();
	}
	
	@AfterEach
	public void deleteTestCarFromDatabase() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("carfleet_manager");
		EntityManager entityManager = factory.createEntityManager();
		entityManager.getTransaction().begin();
		CarDto carDtoDelete = entityManager.find(CarDto.class, testCar.getLicensePlate());
		entityManager.remove(carDtoDelete);
		entityManager.getTransaction().commit();
		entityManager.close();
		factory.close();
	}

	@Test
	public void testSave() {
		CarDao carDaoSave = new CarDao();
		carDaoSave.save(testCar);
	}

	@Test
	public void testIsCarExist() {
		saveTestCarToDatabase();
		CarDao carDao = new CarDao();
		Assert.assertTrue(carDao.isCarExist(testCar.getLicensePlate()));
	}

	@Test
	public void testUpdate() {
		saveTestCarToDatabase();

		testCar.updateCarDto(new MakeDto("Ford", null), new ModelDto("F-150", null),
				new CategoryDto("Szedán", null), "Benzin", 4, 5, "Automata", 40000, 15000, LocalDate.now().plusYears(1),
				new SiteDto("Iroda", "Budapest", 50, null, null, null, null, LocalDate.now(), null, true), "1", true);

		CarDao carDaoUpdate = new CarDao();
		carDaoUpdate.update(testCar);
		
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("carfleet_manager");
		EntityManager entityManager = factory.createEntityManager();
		entityManager.getTransaction().begin();
		CarDto actual = entityManager.find(CarDto.class, testCar.getLicensePlate());
		System.out.println(testCar);
		System.out.println(actual);
		
		Assert.assertEquals(testCar.getUpdatedAt(), actual.getUpdatedAt());
	}

	@Test
	private void testFinfById() {
		saveTestCarToDatabase();
		CarDao findCar = new CarDao();
		CarDto actual = findCar.findById(testCar.getLicensePlate());
		Assert.assertEquals(testCar, actual);
	}

	@Test
	public void testDeleteById() {
		saveTestCarToDatabase();

		CarDao carDaoDelete = new CarDao();
		carDaoDelete.deleteById(testCar.getLicensePlate());

		EntityManagerFactory factory = Persistence.createEntityManagerFactory("carfleet_manager");
		EntityManager entityManager = factory.createEntityManager();
		entityManager.getTransaction().begin();
		CarDto actual = entityManager.find(CarDto.class, testCar.getLicensePlate());


		Assert.assertFalse(actual.isEnabled());
	}

	@Test
	public void testGetAll() {
		CarDao carDaoBefore = new CarDao();
		List<CarDto> carsBefore = carDaoBefore.getAll();
		int countBefore = carsBefore.size();
		
		saveTestCarToDatabase();

		CarDao carDaoAfter = new CarDao();
		List<CarDto> carsAfter = carDaoAfter.getAll();
		int countAfter = carsAfter.size();

		assertEquals(countBefore + 1, countAfter);
	}

}
