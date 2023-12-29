package test.testDao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.jupiter.api.Test;

import application.dao.ModelDao;
import application.dto.ModelDto;

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
		ModelDto testDto = new ModelDto("TEST01", "Honda");
		ModelDao testDaoSave = new ModelDao();
		testDaoSave.save(testDto);
		
		ModelDto testDto2 = new ModelDto("TEST01", "Ford");
		ModelDao testDaoUpdate = new ModelDao();
		testDaoUpdate.update(testDto2);
		
		ModelDao testDaoFind = new ModelDao();
		ModelDto updatedDto = testDaoFind.findById(testDto2.getNameModel());
		
		assertEquals(testDto2.getNameModel(), updatedDto.getNameModel());
		assertEquals(testDto2.getMake(), updatedDto.getMake());
				
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("carfleet_manager");
		EntityManager entityManager = factory.createEntityManager();
	    entityManager.getTransaction().begin();
	    ModelDto deletedDto = entityManager.find(ModelDto.class, testDto2.getNameModel());
	    entityManager.remove(deletedDto);
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
