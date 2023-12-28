package test.testDao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.jupiter.api.Test;

import application.dao.FuelDao;
import application.dto.FuelDto;

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
		FuelDto testDto = new FuelDto("TEST01");
		FuelDao testDaoSave = new FuelDao();
		testDaoSave.save(testDto);
		
		FuelDto testDto2 = new FuelDto("TEST01");
		FuelDao testDaoUpdate = new FuelDao();
		testDaoUpdate.update(testDto2);
		
		FuelDao testDaoFind = new FuelDao();
		FuelDto updatedDto = testDaoFind.findById(testDto2.getFuelType());
		
		assertEquals(testDto2.getFuelType(), updatedDto.getFuelType());
				
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("carfleet_manager");
		EntityManager entityManager = factory.createEntityManager();
	    entityManager.getTransaction().begin();
	    FuelDto deletedDto = entityManager.find(FuelDto.class, testDto2.getFuelType());
	    entityManager.remove(deletedDto);
	    entityManager.getTransaction().commit();
	    entityManager.close();
	    factory.close();
	}
	
	
	@Test
    public void testGetAll() {
        FuelDao testDaoBefore = new FuelDao();
        List<FuelDto> dtosBefore = testDaoBefore.getAll();
        int countBefore = dtosBefore.size();

        FuelDto testDto = new FuelDto("TEST01");
        

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

