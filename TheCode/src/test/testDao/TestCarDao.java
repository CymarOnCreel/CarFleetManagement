package test.testDao;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.jupiter.api.Test;

import application.dao.CarDao;
import application.dto.CarDto;

public class TestCarDao {
	
	@Test
	public void testSave() {
		CarDto testCar = new CarDto("TEST01",
                "Toyota",
                "Camry",
                "Szédán",
                "Benzin",
                4,
                5,
                "Kézi",
                50000,
                10000,
                LocalDate.now().plusYears(1),
                "Raktár",
                "1",
                true);
		CarDao carDaoSave = new CarDao();
		carDaoSave.save(testCar);
		
		CarDao carDaoExist = new CarDao();
		assertTrue(carDaoExist.isCarExist(testCar.getLicensePlate()));
		
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("carfleet_manager");
		EntityManager entityManager = factory.createEntityManager();
	    entityManager.getTransaction().begin();
	    CarDto carDtoDelete = entityManager.find(CarDto.class, testCar.getLicensePlate());
	    entityManager.remove(carDtoDelete);
        entityManager.getTransaction().commit();
	    entityManager.close();
	    factory.close();

	    CarDao carDaoExist2 = new CarDao();
	    assertFalse(carDaoExist2.isCarExist(testCar.getLicensePlate()));
	}
	
	@Test
	public void testIsCarExist() {
		CarDao carDao = new CarDao();
		assertTrue(carDao.isCarExist("ABC123"));
	}
	
	@Test
	public void testUpdate() {
		CarDto testCar = new CarDto("TEST01",
                "Toyota",
                "Camry",
                "Szédán",
                "Benzin",
                4,
                5,
                "Kézi",
                50000,
                10000,
                LocalDate.now().plusYears(1),
                "Raktár",
                "1",
                true);
		CarDao carDaoSave = new CarDao();
		carDaoSave.save(testCar);
		
		CarDto testCar2 = new CarDto("TEST01",
                "Ford",
                "F-150",
                "Szédán",
                "Benzin",
                4,
                5,
                "Automata",
                40000,
                15000,
                LocalDate.now().plusYears(1),
                "Iroda",
                "1",
                true);
		
		CarDao carDaoUpdate = new CarDao();
		carDaoUpdate.update(testCar2);
		
		CarDao carDaoFind = new CarDao();
		CarDto updatedCar = carDaoFind.findById(testCar.getLicensePlate());
		
		assertEquals(updatedCar.getUpdatedAt(), LocalDate.now());
		
		assertEquals(testCar2.getLicensePlate(), updatedCar.getLicensePlate());
		assertEquals(testCar2.getMake(), updatedCar.getMake());
		assertEquals(testCar2.getModel(), updatedCar.getModel());
		assertEquals(testCar2.getCategory(), updatedCar.getCategory());
		assertEquals(testCar2.getFuel(), updatedCar.getFuel());
		assertEquals(testCar2.getDoors(), updatedCar.getDoors());
		assertEquals(testCar2.getSeats(), updatedCar.getSeats());
		assertEquals(testCar2.getTransmissionType(), updatedCar.getTransmissionType());
		assertEquals(testCar2.getMileage(), updatedCar.getMileage());
		assertEquals(testCar2.getServiceInterval(), updatedCar.getServiceInterval());
		assertEquals(testCar2.getInspectionExpiryDate(), updatedCar.getInspectionExpiryDate());
		assertEquals(testCar2.getSiteName(), updatedCar.getSiteName());
		assertEquals(testCar2.getStatus(), updatedCar.getStatus());
		assertEquals(testCar2.isEnabled(), updatedCar.isEnabled());
		
		
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("carfleet_manager");
		EntityManager entityManager = factory.createEntityManager();
	    entityManager.getTransaction().begin();
	    CarDto carDtoDelete = entityManager.find(CarDto.class, testCar.getLicensePlate());
	    entityManager.remove(carDtoDelete);
        entityManager.getTransaction().commit();
	    entityManager.close();
	    factory.close();

	    CarDao carDaoExist2 = new CarDao();
	    assertFalse(carDaoExist2.isCarExist(testCar.getLicensePlate()));
	}
	
	@Test
	public void testDeleteById() {
		CarDto testCar = new CarDto("TEST01",
                "Toyota",
                "Camry",
                "Szédán",
                "Benzin",
                4,
                5,
                "Kézi",
                50000,
                10000,
                LocalDate.now().plusYears(1),
                "Raktár",
                "1",
                true);
		CarDao carDaoSave = new CarDao();
		carDaoSave.save(testCar);
		
		CarDao carDaoDelete = new CarDao();
		carDaoDelete.deleteById(testCar.getLicensePlate());
		
		CarDao carDaoFind = new CarDao();
		CarDto deletedCar = carDaoFind.findById(testCar.getLicensePlate());
		
		assertEquals(deletedCar.getUpdatedAt(), LocalDate.now());
		assertFalse(deletedCar.isEnabled());
		
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("carfleet_manager");
		EntityManager entityManager = factory.createEntityManager();
	    entityManager.getTransaction().begin();
	    CarDto carDtoDelete = entityManager.find(CarDto.class, testCar.getLicensePlate());
	    entityManager.remove(carDtoDelete);
        entityManager.getTransaction().commit();
	    entityManager.close();
	    factory.close();

	    CarDao carDaoExist2 = new CarDao();
	    assertFalse(carDaoExist2.isCarExist(testCar.getLicensePlate()));
	}
	
	@Test
    public void testGetAll() {
        CarDao carDaoBefore = new CarDao();
        List<CarDto> carsBefore = carDaoBefore.getAll();
        int countBefore = carsBefore.size();

        CarDto testCar = new CarDto("TEST01",
                "Toyota",
                "Camry",
                "Szédán",
                "Benzin",
                4,
                5,
                "Kézi",
                50000,
                10000,
                LocalDate.now().plusYears(1),
                "Raktár",
                "1",
                true);
        

        CarDao carDaoSave = new CarDao();
        carDaoSave.save(testCar);


        CarDao carDaoGetAll = new CarDao();
        List<CarDto> carsAfter = carDaoGetAll.getAll();
        int countAfter = carsAfter.size();

        assertEquals(countBefore + 1, countAfter);
        
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("carfleet_manager");
		EntityManager entityManager = factory.createEntityManager();
	    entityManager.getTransaction().begin();
	    CarDto carDtoDelete = entityManager.find(CarDto.class, testCar.getLicensePlate());
	    entityManager.remove(carDtoDelete);
        entityManager.getTransaction().commit();
	    entityManager.close();
	    factory.close();

	    CarDao carDaoExist2 = new CarDao();
	    assertFalse(carDaoExist2.isCarExist(testCar.getLicensePlate()));
    }

}
