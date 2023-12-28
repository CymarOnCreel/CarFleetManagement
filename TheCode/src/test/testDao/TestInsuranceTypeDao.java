package test.testDao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.jupiter.api.Test;

import application.dao.InsuranceTypeDao;
import application.dto.InsuranceTypeDto;

public class TestInsuranceTypeDao {

	@Test
	public void testSave() {
		InsuranceTypeDto testDto = new InsuranceTypeDto("TEST04");
		InsuranceTypeDao testDaoSave = new InsuranceTypeDao();
		testDaoSave.save(testDto);
		
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("carfleet_manager");
		EntityManager entityManager = factory.createEntityManager();
	    entityManager.getTransaction().begin();
	    InsuranceTypeDto testDtoDelete = entityManager.find(InsuranceTypeDto.class, testDto.getNameInsuranceType());
	    entityManager.remove(testDtoDelete);
        entityManager.getTransaction().commit();
	    entityManager.close();
	    factory.close();
	}
	
	@Test
	public void testUpdate() {
		InsuranceTypeDto testDto = new InsuranceTypeDto("TEST01");
		InsuranceTypeDao testDaoSave = new InsuranceTypeDao();
		testDaoSave.save(testDto);
		
		InsuranceTypeDto testDto2 = new InsuranceTypeDto("TEST01");
		InsuranceTypeDao testDaoUpdate = new InsuranceTypeDao();
		testDaoUpdate.update(testDto2);
		
		InsuranceTypeDao testDaoFind = new InsuranceTypeDao();
		InsuranceTypeDto updatedDto = testDaoFind.findById(testDto2.getNameInsuranceType());
		
		assertEquals(testDto2.getNameInsuranceType(), updatedDto.getNameInsuranceType());
				
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("carfleet_manager");
		EntityManager entityManager = factory.createEntityManager();
	    entityManager.getTransaction().begin();
	    InsuranceTypeDto deletedDto = entityManager.find(InsuranceTypeDto.class, testDto2.getNameInsuranceType());
	    entityManager.remove(deletedDto);
	    entityManager.getTransaction().commit();
	    entityManager.close();
	    factory.close();
	}
	
	
	@Test
    public void testGetAll() {
        InsuranceTypeDao testDaoBefore = new InsuranceTypeDao();
        List<InsuranceTypeDto> dtosBefore = testDaoBefore.getAll();
        int countBefore = dtosBefore.size();

        InsuranceTypeDto testDto = new InsuranceTypeDto("TEST01");
        

        InsuranceTypeDao testDaoSave = new InsuranceTypeDao();
        testDaoSave.save(testDto);


        InsuranceTypeDao testDaoGetAll = new InsuranceTypeDao();
        List<InsuranceTypeDto> dtosAfter = testDaoGetAll.getAll();
        int countAfter = dtosAfter.size();

        assertEquals(countBefore + 1, countAfter);
        
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("carfleet_manager");
		EntityManager entityManager = factory.createEntityManager();
	    entityManager.getTransaction().begin();
	    InsuranceTypeDto deletedDto = entityManager.find(InsuranceTypeDto.class, testDto.getNameInsuranceType());
	    entityManager.remove(deletedDto);
        entityManager.getTransaction().commit();
	    entityManager.close();
	    factory.close();

	}

}
