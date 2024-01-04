package application.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;

import application.dto.CarDto;
import application.dto.TransmissionDto;

public class TransmissionDao implements ICrud<TransmissionDto>{
	
	EntityManagerFactory factory = Persistence.createEntityManagerFactory("carfleet_manager");
	EntityManager entityManager = factory.createEntityManager();

	@Override
	public void save(TransmissionDto obj) {
		entityManager.getTransaction().begin();
		entityManager.persist(obj);
		entityManager.getTransaction().commit();
		entityManager.close();
		factory.close();
		
	}

	
	public void update(TransmissionDto oldObj, TransmissionDto newObj) {
		TransmissionDto oldDto = entityManager.find(TransmissionDto.class, oldObj.getTransmissionType());
	    if (oldDto != null) {
	        entityManager.getTransaction().begin();
	        entityManager.persist(newObj);
	        entityManager.getTransaction().commit();

	        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
	        CriteriaUpdate<CarDto> update = criteriaBuilder.createCriteriaUpdate(CarDto.class);
	        Root<CarDto> root = update.from(CarDto.class);
	        update.set("transmissionType", newObj.getTransmissionType()); 
	        update.where(criteriaBuilder.equal(root.get("transmissionType"), oldDto.getTransmissionType()));
	        
	        entityManager.getTransaction().begin();
	        entityManager.createQuery(update).executeUpdate();
	        entityManager.remove(oldDto);
	        entityManager.getTransaction().commit();
	        entityManager.close();
		    factory.close();
	    }
	}

	@Override
	public void deleteById(Object id) {
		entityManager.getTransaction().begin();
		TransmissionDto transmissionDto = entityManager.find(TransmissionDto.class, id);
		if (transmissionDto!=null) {
			transmissionDto.deleteTransmissionDto();
			entityManager.getTransaction().commit();
		}
		entityManager.close();
		factory.close();
	}

	@Override
	public TransmissionDto findById(Object id) {
		TransmissionDto transmissionDto = entityManager.find(TransmissionDto.class, id);
		entityManager.close();
		factory.close();
		return transmissionDto;
	}

	@Override
	public List<TransmissionDto> getAll() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<TransmissionDto> criteriaQuery = criteriaBuilder.createQuery(TransmissionDto.class);
		Root<TransmissionDto> root = criteriaQuery.from(TransmissionDto.class);
		criteriaQuery.select(root);
		List<TransmissionDto> transmission = entityManager.createQuery(criteriaQuery).getResultList();
		return transmission;
	}
}
