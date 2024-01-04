package test.testDao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.jupiter.api.Test;

import application.dao.CarDao;
import application.dao.CategoryDao;
import application.dao.MakeDao;
import application.dao.ModelDao;
import application.dto.CarDto;
import application.dto.CategoryDto;
import application.dto.MakeDto;
import application.dto.ModelDto;
import application.dto.SiteDto;

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
		MakeDto testDtoOld = new MakeDto("TEST01", null);
	    new MakeDao().save(testDtoOld);
	    
	    ModelDto testModelDto = new ModelDto("TEST01", "TEST01");
	    new ModelDao().save(testModelDto);
	    
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
	    
		MakeDto testDtoNew = new MakeDto("TEST02", null);
		
		MakeDao updateDao = new MakeDao();
		updateDao.update(testDtoOld, testDtoNew);
	
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("carfleet_manager");
		EntityManager entityManager = factory.createEntityManager();
	    entityManager.getTransaction().begin();
	    CarDto carDtoDelete = entityManager.find(CarDto.class, testCar.getLicensePlate());
	    entityManager.remove(carDtoDelete);
        entityManager.getTransaction().commit();
       
        entityManager.getTransaction().begin();
        ModelDto testModelDtoDelete = entityManager.find(ModelDto.class, testModelDto.getNameModel());
        entityManager.remove(testModelDtoDelete);
        entityManager.getTransaction().commit();
        
        entityManager.getTransaction().begin();
        MakeDto testDtoDelete = entityManager.find(MakeDto.class, testDtoNew.getNameMake());
        entityManager.remove(testDtoDelete);
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
