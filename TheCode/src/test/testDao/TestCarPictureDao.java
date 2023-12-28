package test.testDao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.jupiter.api.Test;

import application.dao.PictureDao;
import application.dto.PictureDto;

public class TestCarPictureDao {

	@Test
	public void testSave() {
		PictureDto testDto = new PictureDto("TEST04", "ABC123");
		PictureDao testDaoSave = new PictureDao();
		testDaoSave.save(testDto);
		
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("carfleet_manager");
		EntityManager entityManager = factory.createEntityManager();
	    entityManager.getTransaction().begin();
	    PictureDto testDtoDelete = entityManager.find(PictureDto.class, testDto.getPicturePath());
	    entityManager.remove(testDtoDelete);
        entityManager.getTransaction().commit();
	    entityManager.close();
	    factory.close();
	}
	
	@Test
	public void testUpdate() {
		PictureDto testDto = new PictureDto("TEST01", "ABC123");
		PictureDao testDaoSave = new PictureDao();
		testDaoSave.save(testDto);
		
		PictureDto testDto2 = new PictureDto("TEST01", "XYZ789");
		PictureDao testDaoUpdate = new PictureDao();
		testDaoUpdate.update(testDto2);
		
		PictureDao testDaoFind = new PictureDao();
		PictureDto updatedDto = testDaoFind.findById(testDto2.getPicturePath());
		
		assertEquals(testDto2.getPicturePath(), updatedDto.getPicturePath());
		assertEquals(testDto2.getLicensePlate(), updatedDto.getLicensePlate());
				
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("carfleet_manager");
		EntityManager entityManager = factory.createEntityManager();
	    entityManager.getTransaction().begin();
	    PictureDto deletedDto = entityManager.find(PictureDto.class, testDto2.getPicturePath());
	    entityManager.remove(deletedDto);
	    entityManager.getTransaction().commit();
	    entityManager.close();
	    factory.close();
	}
	
	@Test
    public void testGetAll() {
        PictureDao testDaoBefore = new PictureDao();
        List<PictureDto> dtosBefore = testDaoBefore.getAll();
        int countBefore = dtosBefore.size();

        PictureDto testDto = new PictureDto("TEST01","ABC123");
        

        PictureDao testDaoSave = new PictureDao();
        testDaoSave.save(testDto);


        PictureDao testDaoGetAll = new PictureDao();
        List<PictureDto> dtosAfter = testDaoGetAll.getAll();
        int countAfter = dtosAfter.size();

        assertEquals(countBefore + 1, countAfter);
        
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("carfleet_manager");
		EntityManager entityManager = factory.createEntityManager();
	    entityManager.getTransaction().begin();
	    PictureDto testDtoDelete = entityManager.find(PictureDto.class, testDto.getPicturePath());
	    entityManager.remove(testDtoDelete);
        entityManager.getTransaction().commit();
	    entityManager.close();
	    factory.close();

	}

}
