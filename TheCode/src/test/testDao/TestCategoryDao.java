package test.testDao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.jupiter.api.Test;

import application.dao.CategoryDao;
import application.dto.CategoryDto;

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
		CategoryDto testDto = new CategoryDto("TEST01", null);
		CategoryDao testDaoSave = new CategoryDao();
		testDaoSave.save(testDto);
		
		CategoryDto testDto2 = new CategoryDto("TEST01", "pic/path");
		CategoryDao testDaoUpdate = new CategoryDao();
		testDaoUpdate.update(testDto2);
		
		CategoryDao testDaoFind = new CategoryDao();
		CategoryDto updatedDto = testDaoFind.findById(testDto2.getNameCategory());
		
		assertEquals(testDto2.getNameCategory(), updatedDto.getNameCategory());
		assertEquals(testDto2.getPicturePathCategory(), updatedDto.getPicturePathCategory());
				
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("carfleet_manager");
		EntityManager entityManager = factory.createEntityManager();
	    entityManager.getTransaction().begin();
	    CategoryDto deletedDto = entityManager.find(CategoryDto.class, testDto2.getNameCategory());
	    entityManager.remove(deletedDto);
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