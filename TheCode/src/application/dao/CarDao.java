package application.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import application.dto.CarDto;



public class CarDao implements ICrud<CarDto> {
	
	EntityManagerFactory factory = Persistence.createEntityManagerFactory("carfleet_manager");
	EntityManager entityManager = factory.createEntityManager();

	public void save(CarDto obj) {
		entityManager.getTransaction().begin();
		entityManager.persist(obj);
		entityManager.getTransaction().commit();
		entityManager.close();
		factory.close();
	}

	public void update(CarDto obj) {
		entityManager.getTransaction().begin();
		CarDto carDto = entityManager.find(CarDto.class, obj.getLicensePlate());
		if (carDto!=null) {
			carDto.updateCarDto(obj.getMake(),
					obj.getModel(),
					obj.getCategory(),
					obj.getFuel(),
					obj.getDoors(),
					obj.getSeats(), 
					obj.getTransmissionType(), 
					obj.getMileage(), 
					obj.getServiceInterval(), 
					obj.getInspectionExpiryDate(), 
					obj.getSiteName(), 
					obj.getStatus(), 
					obj.isEnabled());
		}
		entityManager.getTransaction().commit();
		entityManager.close();
		factory.close();
	}

	public void deleteById(Object id) {
		entityManager.getTransaction().begin();
		CarDto carDto = entityManager.find(CarDto.class, id);
		if (carDto!=null) {
			carDto.deleteCarDto();
			entityManager.getTransaction().commit();
		}
		entityManager.close();
		factory.close();
	}

	public CarDto findById(Object id) {
		CarDto carDto = entityManager.find(CarDto.class, id);
		entityManager.close();
		factory.close();
		return carDto;
	}

	public List<CarDto> getAll() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<CarDto> criteriaQuery = criteriaBuilder.createQuery(CarDto.class);
		Root<CarDto> root = criteriaQuery.from(CarDto.class);
		criteriaQuery.select(root);
		List<CarDto> cars = entityManager.createQuery(criteriaQuery).getResultList();
		return cars;
	}
	
	public boolean isCarExist(String licensePlate) {
	    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
	    CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
	    Root<CarDto> from = criteriaQuery.from(CarDto.class);

	    criteriaQuery.select(criteriaBuilder.count(from))
	        .where(criteriaBuilder.equal(from.get("licensePlate"), licensePlate.toUpperCase()));

	    TypedQuery<Long> typedQuery = entityManager.createQuery(criteriaQuery);

	    Long count = typedQuery.getSingleResult();

	    return count > 0;
	}

}
