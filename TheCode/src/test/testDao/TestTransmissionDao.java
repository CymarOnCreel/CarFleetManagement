package test.testDao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.jupiter.api.Test;

import application.dao.CarDao;
import application.dao.TransmissionDao;
import application.dto.CarDto;
import application.dto.CategoryDto;
import application.dto.MakeDto;
import application.dto.ModelDto;
import application.dto.SiteDto;
import application.dto.TransmissionDto;

public class TestTransmissionDao {
	
	@Test
	public void testSave() {
		TransmissionDto testDto = new TransmissionDto("TEST04");
		TransmissionDao testDaoSave = new TransmissionDao();
		testDaoSave.save(testDto);
		
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("carfleet_manager");
		EntityManager entityManager = factory.createEntityManager();
	    entityManager.getTransaction().begin();
	    TransmissionDto testDtoDelete = entityManager.find(TransmissionDto.class, testDto.getTransmissionType());
	    entityManager.remove(testDtoDelete);
        entityManager.getTransaction().commit();
	    entityManager.close();
	    factory.close();
	}
	
	@Test
	public void testUpdate() {
		TransmissionDto testDtoOld = new TransmissionDto("TEST01");
	    new TransmissionDao().save(testDtoOld);
	    
	    CarDto testCar = new CarDto("TEST01",
                new MakeDto("Toyota", null),
                new ModelDto("Camry", null),
                new CategoryDto("Szédán", null),
                "Benzin",
                4,
                5,
                "TEST01",
                50000,
                10000,
                LocalDate.now().plusYears(1),
                new SiteDto("Raktár", "Budapest", 50, null, null, null, null, LocalDate.now(), null, true),
                "1",
                true);
		CarDao carDaoSave = new CarDao();
		carDaoSave.save(testCar);
	    
		TransmissionDto testDtoNew = new TransmissionDto("TEST02");
			
		TransmissionDao updateDao = new TransmissionDao();
		updateDao.update(testDtoOld, testDtoNew);
		
		
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("carfleet_manager");
		EntityManager entityManager = factory.createEntityManager();
	    entityManager.getTransaction().begin();
	    CarDto carDtoDelete = entityManager.find(CarDto.class, testCar.getLicensePlate());
	    entityManager.remove(carDtoDelete);
        entityManager.getTransaction().commit();
       
        entityManager.getTransaction().begin();
        TransmissionDto testDtoDelete = entityManager.find(TransmissionDto.class, testDtoNew.getTransmissionType());
        entityManager.remove(testDtoDelete);
        entityManager.getTransaction().commit();
        
        entityManager.close();
	    factory.close();
	}
	
	
	@Test
    public void testGetAll() {
        TransmissionDao testDaoBefore = new TransmissionDao();
        List<TransmissionDto> dtosBefore = testDaoBefore.getAll();
        int countBefore = dtosBefore.size();

        TransmissionDto testDto = new TransmissionDto("TEST01");
        

        TransmissionDao testDaoSave = new TransmissionDao();
        testDaoSave.save(testDto);


        TransmissionDao testDaoGetAll = new TransmissionDao();
        List<TransmissionDto> dtosAfter = testDaoGetAll.getAll();
        int countAfter = dtosAfter.size();

        assertEquals(countBefore + 1, countAfter);
        
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("carfleet_manager");
		EntityManager entityManager = factory.createEntityManager();
	    entityManager.getTransaction().begin();
	    TransmissionDto deletedDto = entityManager.find(TransmissionDto.class, testDto.getTransmissionType());
	    entityManager.remove(deletedDto);
        entityManager.getTransaction().commit();
	    entityManager.close();
	    factory.close();

	}

}

