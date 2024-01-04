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
import application.dto.FuelDto;

public class FuelDao implements ICrud<FuelDto>{
	
	EntityManagerFactory factory = Persistence.createEntityManagerFactory("carfleet_manager");
	EntityManager entityManager = factory.createEntityManager();

	@Override
	public void save(FuelDto obj) {
		entityManager.getTransaction().begin();
		entityManager.persist(obj);
		entityManager.getTransaction().commit();
		entityManager.close();
		factory.close();
		
	}


	@Override
	public void deleteById(Object id) {
		entityManager.getTransaction().begin();
		FuelDto fuelDto = entityManager.find(FuelDto.class, id);
		if (fuelDto!=null) {
			fuelDto.deleteFuelDto();
			entityManager.getTransaction().commit();
		}
		entityManager.close();
		factory.close();
	}

	@Override
	public FuelDto findById(Object id) {
		FuelDto fuelDto = entityManager.find(FuelDto.class, id);
		entityManager.close();
		factory.close();
		return fuelDto;
	}

	@Override
	public List<FuelDto> getAll() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<FuelDto> criteriaQuery = criteriaBuilder.createQuery(FuelDto.class);
		Root<FuelDto> root = criteriaQuery.from(FuelDto.class);
		criteriaQuery.select(root);
		List<FuelDto> fuel = entityManager.createQuery(criteriaQuery).getResultList();
		return fuel;
	}
	
	
	public void update(FuelDto oldObj, FuelDto newObj) {
	    FuelDto oldDto = entityManager.find(FuelDto.class, oldObj.getFuelType());
	    if (oldDto != null) {
	        entityManager.getTransaction().begin();
	        entityManager.persist(newObj);
	        entityManager.getTransaction().commit();

	        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
	        CriteriaUpdate<CarDto> update = criteriaBuilder.createCriteriaUpdate(CarDto.class);
	        Root<CarDto> root = update.from(CarDto.class);
	        update.set("fuel", newObj.getFuelType()); 
	        update.where(criteriaBuilder.equal(root.get("fuel"), oldDto.getFuelType()));
	        
	        entityManager.getTransaction().begin();
	        entityManager.createQuery(update).executeUpdate();
	        entityManager.remove(oldDto);
	        entityManager.getTransaction().commit();
	        entityManager.close();
		    factory.close();
	    }

	
	}
	
}
