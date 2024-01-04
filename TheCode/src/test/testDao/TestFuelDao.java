package test.testDao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.jupiter.api.Test;

import application.dao.CarDao;
import application.dao.FuelDao;
import application.dto.CarDto;
import application.dto.CategoryDto;
import application.dto.FuelDto;
import application.dto.MakeDto;
import application.dto.ModelDto;
import application.dto.SiteDto;

public class TestFuelDao {

	@Test
	public void testSave() {
		FuelDto testDto = new FuelDto("TEST04");
		FuelDao testDaoSave = new FuelDao();
		testDaoSave.save(testDto);
		
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("carfleet_manager");
		EntityManager entityManager = factory.createEntityManager();
	    entityManager.getTransaction().begin();
	    FuelDto testDtoDelete = entityManager.find(FuelDto.class, testDto.getFuelType());
	    entityManager.remove(testDtoDelete);
        entityManager.getTransaction().commit();
	    entityManager.close();
	    factory.close();
	}
	
	@Test
	public void testUpdate() {
	    FuelDto testDtoOld = new FuelDto("TEST01");
	    new FuelDao().save(testDtoOld);
	    
	    CarDto testCar = new CarDto("TEST01",
                new MakeDto("Toyota", null),
                new ModelDto("Camry", null),
                new CategoryDto("Szédán", null),
                "TEST01",
                4,
                5,
                "Kézi",
                50000,
                10000,
                LocalDate.now().plusYears(1),
                new SiteDto("Raktár", "Budapest", 50, null, null, null, null, LocalDate.now(), null, true),
                "1",
                true);
		CarDao carDaoSave = new CarDao();
		carDaoSave.save(testCar);
	    
		FuelDto testDtoNew = new FuelDto("TEST02");
			
		FuelDao updateDao = new FuelDao();
		updateDao.update(testDtoOld, testDtoNew);
		
		
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("carfleet_manager");
		EntityManager entityManager = factory.createEntityManager();
	    entityManager.getTransaction().begin();
	    CarDto carDtoDelete = entityManager.find(CarDto.class, testCar.getLicensePlate());
	    entityManager.remove(carDtoDelete);
        entityManager.getTransaction().commit();
       
        entityManager.getTransaction().begin();
        FuelDto testDtoDelete = entityManager.find(FuelDto.class, testDtoNew.getFuelType());
        entityManager.remove(testDtoDelete);
        entityManager.getTransaction().commit();
        
        entityManager.close();
	    factory.close();
		
	}
	
	
	@Test
    public void testGetAll() {
        FuelDao testDaoBefore = new FuelDao();
        List<FuelDto> dtosBefore = testDaoBefore.getAll();
        int countBefore = dtosBefore.size();

        FuelDto testDto = new FuelDto("TEST03");
        

        FuelDao testDaoSave = new FuelDao();
        testDaoSave.save(testDto);


        FuelDao testDaoGetAll = new FuelDao();
        List<FuelDto> dtosAfter = testDaoGetAll.getAll();
        int countAfter = dtosAfter.size();

        assertEquals(countBefore + 1, countAfter);
        
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("carfleet_manager");
		EntityManager entityManager = factory.createEntityManager();
	    entityManager.getTransaction().begin();
	    FuelDto deletedDto = entityManager.find(FuelDto.class, testDto.getFuelType());
	    entityManager.remove(deletedDto);
        entityManager.getTransaction().commit();
	    entityManager.close();
	    factory.close();

	}

}

