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
import application.dto.MakeDto;
import application.dto.ModelDto;

public class ModelDao implements ICrud<ModelDto>{
	
	EntityManagerFactory factory = Persistence.createEntityManagerFactory("carfleet_manager");
	EntityManager entityManager = factory.createEntityManager();

	@Override
	public void save(ModelDto obj) {
		entityManager.getTransaction().begin();
		entityManager.persist(obj);
		entityManager.getTransaction().commit();
		entityManager.close();
		factory.close();
		
	}

	
	public void update(ModelDto oldObj, ModelDto newObj) {
		ModelDto oldDto = entityManager.find(ModelDto.class, oldObj.getNameModel());
		if (oldDto != null) {
			entityManager.getTransaction().begin();
	        entityManager.persist(newObj);
	        entityManager.getTransaction().commit();

	        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
	        CriteriaUpdate<CarDto> update = criteriaBuilder.createCriteriaUpdate(CarDto.class);
	        Root<CarDto> root = update.from(CarDto.class);
	        update.set("model", newObj); 
	        update.where(criteriaBuilder.equal(root.get("model"), oldDto));
	        
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
		ModelDto modelDto = entityManager.find(ModelDto.class, id);
		if (modelDto!=null) {
			modelDto.deleteModelDto();
			entityManager.getTransaction().commit();
		}
		entityManager.close();
		factory.close();	
	}

	@Override
	public ModelDto findById(Object id) {
		ModelDto modelDto = entityManager.find(ModelDto.class, id);
		entityManager.close();
		factory.close();
		return modelDto;
	}

	@Override
	public List<ModelDto> getAll() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<ModelDto> criteriaQuery = criteriaBuilder.createQuery(ModelDto.class);
		Root<ModelDto> root = criteriaQuery.from(ModelDto.class);
		criteriaQuery.select(root);
		List<ModelDto> model = entityManager.createQuery(criteriaQuery).getResultList();
		return model;
	}
}
