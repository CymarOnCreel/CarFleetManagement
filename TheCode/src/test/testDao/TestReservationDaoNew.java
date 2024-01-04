package test.testDao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.junit.jupiter.api.Test;

import application.dao.ReservationDao;
import application.dto.ReservationDto;

public class TestReservationDaoNew {

	@Test
	public void testSave() {
		
		
		ReservationDto testDto = new ReservationDto(1, "ABC123", LocalDateTime.now(), LocalDateTime.now(), 
				"foglalás", LocalDateTime.now(), LocalDateTime.now(), false);
		ReservationDao testDaoSave = new ReservationDao();
		testDaoSave.save(testDto);
		
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("carfleet_manager");
		EntityManager entityManager = factory.createEntityManager();
	    entityManager.getTransaction().begin();
		
	    Query maxIdQuery = entityManager.createQuery("SELECT MAX(r.id) FROM ReservationDto r");
        Integer maxId = (Integer) maxIdQuery.getSingleResult();
        ReservationDto testDtoDelete = entityManager.find(ReservationDto.class, maxId);
	    entityManager.remove(testDtoDelete);
        entityManager.getTransaction().commit();
	    entityManager.close();
	    factory.close();
	}
	
//	@Test
//	public void testUpdate() {
//		ReservationDto testDto = new ReservationDto(1, "ABC123", LocalDateTime.now(), LocalDateTime.now(), "foglalás");
//		ReservationDao testDaoSave = new ReservationDao();
//		testDaoSave.save(testDto);
//		
//		ReservationDto testDto2 = new ReservationDto(1, "XYZ789", LocalDateTime.now(), LocalDateTime.now(), "Módosított");
//		ReservationDao testDaoUpdate = new ReservationDao();
//		testDaoUpdate.update(testDto2);
//		
//		ReservationDao testDaoFind = new ReservationDao();
//		ReservationDto updatedDto = testDaoFind.findById(testDto2.getIdReservation());
//		
//		assertEquals(testDto2.getIdReservation(), updatedDto.getIdReservation());
//		assertEquals(testDto2.getLicensePlate(), updatedDto.getLicensePlate());
//		assertEquals(testDto2.getStartDateTime(), updatedDto.getStartDateTime());
//		assertEquals(testDto2.getEndDateTime(), updatedDto.getEndDateTime());
//		assertEquals(testDto2.getDescription(), updatedDto.getDescription());
//
//				
//		EntityManagerFactory factory = Persistence.createEntityManagerFactory("carfleet_manager");
//		EntityManager entityManager = factory.createEntityManager();
//	    entityManager.getTransaction().begin();
//	    ReservationDto deletedDto = entityManager.find(ReservationDto.class, testDto2.getIdReservation());
//	    entityManager.remove(deletedDto);
//	    entityManager.getTransaction().commit();
//	    entityManager.close();
//	    factory.close();
//	}
	
	
	@Test
    public void testGetAll() {
        ReservationDao testDaoBefore = new ReservationDao();
        List<ReservationDto> dtosBefore = testDaoBefore.getAll();
        int countBefore = dtosBefore.size();

        ReservationDto testDto = new ReservationDto(1, "ABC123", LocalDateTime.now(), LocalDateTime.now(), "foglalás");
        

        ReservationDao testDaoSave = new ReservationDao();
        testDaoSave.save(testDto);


        ReservationDao testDaoGetAll = new ReservationDao();
        List<ReservationDto> dtosAfter = testDaoGetAll.getAll();
        int countAfter = dtosAfter.size();

        assertEquals(countBefore + 1, countAfter);
        
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("carfleet_manager");
		EntityManager entityManager = factory.createEntityManager();
	    entityManager.getTransaction().begin();
        
	    Query maxIdQuery = entityManager.createQuery("SELECT MAX(r.id) FROM ReservationDto r");
        Integer maxId = (Integer) maxIdQuery.getSingleResult();
        ReservationDto testDtoDelete = entityManager.find(ReservationDto.class, maxId);
	    entityManager.remove(testDtoDelete);
        entityManager.getTransaction().commit();
	    entityManager.close();
	    factory.close();

	}

}