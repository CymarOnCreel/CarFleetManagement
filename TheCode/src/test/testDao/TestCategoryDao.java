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
import application.dto.CarDto;
import application.dto.CategoryDto;
import application.dto.MakeDto;
import application.dto.ModelDto;
import application.dto.SiteDto;

public class TestCategoryDao {

	@Test
	public void testSave() {
		CategoryDto testDto = new CategoryDto("TEST04", null);
		CategoryDao testDaoSave = new CategoryDao();
		testDaoSave.save(testDto);
		
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("carfleet_manager");
		EntityManager entityManager = factory.createEntityManager();
	    entityManager.getTransaction().begin();
	    CategoryDto testDtoDelete = entityManager.find(CategoryDto.class, testDto.getNameCategory());
	    entityManager.remove(testDtoDelete);
        entityManager.getTransaction().commit();
	    entityManager.close();
	    factory.close();
	}
	
	@Test
	public void testUpdate() {
		CategoryDto testDtoOld = new CategoryDto("TEST01", null);
	    new CategoryDao().save(testDtoOld);
	    
	    CarDto testCar = new CarDto("TEST01",
                new MakeDto("Toyota", null),
                new ModelDto("Camry", null),
                new CategoryDto("TEST01", null),
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
	    
		CategoryDto testDtoNew = new CategoryDto("TEST02","pict/path");
		
		CategoryDao updateDao = new CategoryDao();
		updateDao.update(testDtoOld, testDtoNew);
	
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("carfleet_manager");
		EntityManager entityManager = factory.createEntityManager();
	    entityManager.getTransaction().begin();
	    CarDto carDtoDelete = entityManager.find(CarDto.class, testCar.getLicensePlate());
	    entityManager.remove(carDtoDelete);
        entityManager.getTransaction().commit();
       
        entityManager.getTransaction().begin();
        CategoryDto testDtoDelete = entityManager.find(CategoryDto.class, testDtoNew.getNameCategory());
        entityManager.remove(testDtoDelete);
        entityManager.getTransaction().commit();
        
        entityManager.close();
	    factory.close();		
	}
	
	@Test
    public void testGetAll() {
        CategoryDao testDaoBefore = new CategoryDao();
        List<CategoryDto> dtosBefore = testDaoBefore.getAll();
        int countBefore = dtosBefore.size();

        CategoryDto testDto = new CategoryDto("TEST01",null);
        

        CategoryDao testDaoSave = new CategoryDao();
        testDaoSave.save(testDto);


        CategoryDao testDaoGetAll = new CategoryDao();
        List<CategoryDto> dtosAfter = testDaoGetAll.getAll();
        int countAfter = dtosAfter.size();

        assertEquals(countBefore + 1, countAfter);
        
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("carfleet_manager");
		EntityManager entityManager = factory.createEntityManager();
	    entityManager.getTransaction().begin();
	    CategoryDto testDtoDelete = entityManager.find(CategoryDto.class, testDto.getNameCategory());
	    entityManager.remove(testDtoDelete);
        entityManager.getTransaction().commit();
	    entityManager.close();
	    factory.close();

	}

}