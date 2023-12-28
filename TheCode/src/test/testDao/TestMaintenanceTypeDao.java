package test.testDao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.jupiter.api.Test;

import application.dao.MaintenanceTypeDao;
import application.dto.MaintenanceTypeDto;

public class TestMaintenanceTypeDao {

	@Test
	public void testSave() {
		MaintenanceTypeDto testDto = new MaintenanceTypeDto("TEST04");
		MaintenanceTypeDao testDaoSave = new MaintenanceTypeDao();
		testDaoSave.save(testDto);
		
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("carfleet_manager");
		EntityManager entityManager = factory.createEntityManager();
	    entityManager.getTransaction().begin();
	    MaintenanceTypeDto testDtoDelete = entityManager.find(MaintenanceTypeDto.class, testDto.getNameMaintenanceType());
	    entityManager.remove(testDtoDelete);
        entityManager.getTransaction().commit();
	    entityManager.close();
	    factory.close();
	}
	
	@Test
	public void testUpdate() {
		MaintenanceTypeDto testDto = new MaintenanceTypeDto("TEST01");
		MaintenanceTypeDao testDaoSave = new MaintenanceTypeDao();
		testDaoSave.save(testDto);
		
		MaintenanceTypeDto testDto2 = new MaintenanceTypeDto("TEST01");
		MaintenanceTypeDao testDaoUpdate = new MaintenanceTypeDao();
		testDaoUpdate.update(testDto2);
		
		MaintenanceTypeDao testDaoFind = new MaintenanceTypeDao();
		MaintenanceTypeDto updatedDto = testDaoFind.findById(testDto2.getNameMaintenanceType());
		
		assertEquals(testDto2.getNameMaintenanceType(), updatedDto.getNameMaintenanceType());
				
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("carfleet_manager");
		EntityManager entityManager = factory.createEntityManager();
	    entityManager.getTransaction().begin();
	    MaintenanceTypeDto deletedDto = entityManager.find(MaintenanceTypeDto.class, testDto2.getNameMaintenanceType());
	    entityManager.remove(deletedDto);
	    entityManager.getTransaction().commit();
	    entityManager.close();
	    factory.close();
	}
	
	
	@Test
    public void testGetAll() {
        MaintenanceTypeDao testDaoBefore = new MaintenanceTypeDao();
        List<MaintenanceTypeDto> dtosBefore = testDaoBefore.getAll();
        int countBefore = dtosBefore.size();

        MaintenanceTypeDto testDto = new MaintenanceTypeDto("TEST01");
        

        MaintenanceTypeDao testDaoSave = new MaintenanceTypeDao();
        testDaoSave.save(testDto);


        MaintenanceTypeDao testDaoGetAll = new MaintenanceTypeDao();
        List<MaintenanceTypeDto> dtosAfter = testDaoGetAll.getAll();
        int countAfter = dtosAfter.size();

        assertEquals(countBefore + 1, countAfter);
        
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("carfleet_manager");
		EntityManager entityManager = factory.createEntityManager();
	    entityManager.getTransaction().begin();
	    MaintenanceTypeDto deletedDto = entityManager.find(MaintenanceTypeDto.class, testDto.getNameMaintenanceType());
	    entityManager.remove(deletedDto);
        entityManager.getTransaction().commit();
	    entityManager.close();
	    factory.close();

	}

}
