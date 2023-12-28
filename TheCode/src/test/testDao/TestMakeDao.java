package test.testDao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.jupiter.api.Test;

import application.dao.MakeDao;
import application.dto.MakeDto;

public class TestMakeDao {

	@Test
	public void testSave() {
		MakeDto testDto = new MakeDto("TEST04", null);
		MakeDao testDaoSave = new MakeDao();
		testDaoSave.save(testDto);
		
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("carfleet_manager");
		EntityManager entityManager = factory.createEntityManager();
	    entityManager.getTransaction().begin();
	    MakeDto testDtoDelete = entityManager.find(MakeDto.class, testDto.getNameMake());
	    entityManager.remove(testDtoDelete);
        entityManager.getTransaction().commit();
	    entityManager.close();
	    factory.close();
	}
	
	@Test
	public void testUpdate() {
		MakeDto testDto = new MakeDto("TEST01", null);
		MakeDao testDaoSave = new MakeDao();
		testDaoSave.save(testDto);
		
		MakeDto testDto2 = new MakeDto("TEST01", "pic/path");
		MakeDao testDaoUpdate = new MakeDao();
		testDaoUpdate.update(testDto2);
		
		MakeDao testDaoFind = new MakeDao();
		MakeDto updatedDto = testDaoFind.findById(testDto2.getNameMake());
		
		assertEquals(testDto2.getNameMake(), updatedDto.getNameMake());
		assertEquals(testDto2.getPicturePathMake(), updatedDto.getPicturePathMake());
				
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("carfleet_manager");
		EntityManager entityManager = factory.createEntityManager();
	    entityManager.getTransaction().begin();
	    MakeDto deletedDto = entityManager.find(MakeDto.class, testDto2.getNameMake());
	    entityManager.remove(deletedDto);
	    entityManager.getTransaction().commit();
	    entityManager.close();
	    factory.close();
	}
	
//	@Test
//	public void testDeleteById() {
//		MakeDto testMake = new MakeDto("TEST01", null);
//		MakeDao makeDaoSave = new MakeDao();
//		makeDaoSave.save(testMake);
//		
//		MakeDao makeDaoDelete = new MakeDao();
//		makeDaoDelete.deleteById(testMake.getNameMake());
//		
//		MakeDao makeDaoFind = new MakeDao();
//		MakeDto deletedMake = makeDaoFind.findById(testMake.getNameMake());
//		
//		assertEquals(deletedMake.getUpdatedAt(), LocalDate.now());
//		assertFalse(deletedMake.isEnabled());
//		
//		EntityManagerFactory factory = Persistence.createEntityManagerFactory("carfleet_manager");
//		EntityManager entityManager = factory.createEntityManager();
//	    entityManager.getTransaction().begin();
//	    MakeDto makeDtoDelete = entityManager.find(MakeDto.class, testMake.getNameMake());
//	    entityManager.remove(makeDtoDelete);
//        entityManager.getTransaction().commit();
//	    entityManager.close();
//	    factory.close();
//
//	    MakeDao makeDaoExist2 = new MakeDao();
//	    assertFalse(makeDaoExist2.isMakeExist(testMake.getNameMake()));
//	}
	
	@Test
    public void testGetAll() {
        MakeDao testDaoBefore = new MakeDao();
        List<MakeDto> dtosBefore = testDaoBefore.getAll();
        int countBefore = dtosBefore.size();

        MakeDto testDto = new MakeDto("TEST01",null);
        

        MakeDao testDaoSave = new MakeDao();
        testDaoSave.save(testDto);


        MakeDao testDaoGetAll = new MakeDao();
        List<MakeDto> dtosAfter = testDaoGetAll.getAll();
        int countAfter = dtosAfter.size();

        assertEquals(countBefore + 1, countAfter);
        
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("carfleet_manager");
		EntityManager entityManager = factory.createEntityManager();
	    entityManager.getTransaction().begin();
	    MakeDto testDtoDelete = entityManager.find(MakeDto.class, testDto.getNameMake());
	    entityManager.remove(testDtoDelete);
        entityManager.getTransaction().commit();
	    entityManager.close();
	    factory.close();

	}

}
