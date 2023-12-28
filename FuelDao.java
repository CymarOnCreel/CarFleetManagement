package application.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
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
	public void update(FuelDto obj) {
		entityManager.getTransaction().begin();
		FuelDto fuelDto = entityManager.find(FuelDto.class, obj.getFuelType());
		if (fuelDto!=null) {
			fuelDto.updateFuelDto(obj.getFuelType()
			);
		}
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
}
