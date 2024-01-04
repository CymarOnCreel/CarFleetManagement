package test.testDao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.jupiter.api.Test;

import application.dao.CarDao;
import application.dao.MakeDao;
import application.dao.ModelDao;
import application.dto.CarDto;
import application.dto.CategoryDto;
import application.dto.MakeDto;
import application.dto.ModelDto;
import application.dto.SiteDto;

public class TestModelDao {
	
	@Test
	public void testSave() {
		ModelDto testDto = new ModelDto("TEST04", "Honda");
		ModelDao testDaoSave = new ModelDao();
		testDaoSave.save(testDto);
		
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("carfleet_manager");
		EntityManager entityManager = factory.createEntityManager();
	    entityManager.getTransaction().begin();
	    ModelDto testDtoDelete = entityManager.find(ModelDto.class, testDto.getNameModel());
	    entityManager.remove(testDtoDelete);
        entityManager.getTransaction().commit();
	    entityManager.close();
	    factory.close();
	}
	
	@Test
	public void testUpdate() {
	    MakeDto testMakeDto = new MakeDto("TEST01", null);
	    new MakeDao().save(testMakeDto);
	    
	    ModelDto testDtoOld = new ModelDto("TEST01", "TEST01");
	    new ModelDao().save(testDtoOld);
	    
	    CarDto testCar = new CarDto("TEST01",
                new MakeDto("TEST01", null),
                new ModelDto("TEST01", null),
                new CategoryDto("Szédán", null),
                "Benzin",
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
	    
		ModelDto testDtoNew = new ModelDto("TEST02", "TEST01");
		
		ModelDao updateDao = new ModelDao();
		updateDao.update(testDtoOld, testDtoNew);
	
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("carfleet_manager");
		EntityManager entityManager = factory.createEntityManager();
	    entityManager.getTransaction().begin();
	    CarDto carDtoDelete = entityManager.find(CarDto.class, testCar.getLicensePlate());
	    entityManager.remove(carDtoDelete);
        entityManager.getTransaction().commit();
       
        entityManager.getTransaction().begin();
        ModelDto testDtoDelete = entityManager.find(ModelDto.class, testDtoNew.getNameModel());
        entityManager.remove(testDtoDelete);
        entityManager.getTransaction().commit();
        
        entityManager.getTransaction().begin();
        MakeDto testMakeDtoDelete = entityManager.find(MakeDto.class, testMakeDto.getNameMake());
        entityManager.remove(testMakeDtoDelete);
        entityManager.getTransaction().commit();
        
        entityManager.close();
	    factory.close();
	}
	
	
	@Test
    public void testGetAll() {
        ModelDao testDaoBefore = new ModelDao();
        List<ModelDto> dtosBefore = testDaoBefore.getAll();
        int countBefore = dtosBefore.size();

        ModelDto testDto = new ModelDto("TEST01","Honda");
        

        ModelDao testDaoSave = new ModelDao();
        testDaoSave.save(testDto);


        ModelDao testDaoGetAll = new ModelDao();
        List<ModelDto> dtosAfter = testDaoGetAll.getAll();
        int countAfter = dtosAfter.size();

        assertEquals(countBefore + 1, countAfter);
        
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("carfleet_manager");
		EntityManager entityManager = factory.createEntityManager();
	    entityManager.getTransaction().begin();
	    ModelDto deletedDto = entityManager.find(ModelDto.class, testDto.getNameModel());
	    entityManager.remove(deletedDto);
        entityManager.getTransaction().commit();
	    entityManager.close();
	    factory.close();

	}

}
