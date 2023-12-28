package test.testDao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.jupiter.api.Test;

import application.dao.TransmissionDao;
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
		TransmissionDto testDto = new TransmissionDto("TEST01");
		TransmissionDao testDaoSave = new TransmissionDao();
		testDaoSave.save(testDto);
		
		TransmissionDto testDto2 = new TransmissionDto("TEST01");
		TransmissionDao testDaoUpdate = new TransmissionDao();
		testDaoUpdate.update(testDto2);
		
		TransmissionDao testDaoFind = new TransmissionDao();
		TransmissionDto updatedDto = testDaoFind.findById(testDto2.getTransmissionType());
		
		assertEquals(testDto2.getTransmissionType(), updatedDto.getTransmissionType());
				
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("carfleet_manager");
		EntityManager entityManager = factory.createEntityManager();
	    entityManager.getTransaction().begin();
	    TransmissionDto deletedDto = entityManager.find(TransmissionDto.class, testDto2.getTransmissionType());
	    entityManager.remove(deletedDto);
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

