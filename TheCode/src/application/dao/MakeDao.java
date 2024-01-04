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

public class MakeDao implements ICrud<MakeDto> {
	
	EntityManagerFactory factory = Persistence.createEntityManagerFactory("carfleet_manager");
	EntityManager entityManager = factory.createEntityManager();

	@Override
	public void save(MakeDto obj) {
		entityManager.getTransaction().begin();
		entityManager.persist(obj);
		entityManager.getTransaction().commit();
		entityManager.close();
		factory.close();
	}
	
	
	
	public void update(MakeDto oldObj, MakeDto newObj) {
		MakeDto oldDto = entityManager.find(MakeDto.class, oldObj.getNameMake());
		if (oldDto != null) {
			entityManager.getTransaction().begin();
	        entityManager.persist(newObj);
	        entityManager.getTransaction().commit();
	        
	        CriteriaBuilder criteriaBuilderModel = entityManager.getCriteriaBuilder();
	        CriteriaUpdate<ModelDto> updateModel = criteriaBuilderModel.createCriteriaUpdate(ModelDto.class);
	        Root<ModelDto> rootModel = updateModel.from(ModelDto.class);
	        updateModel.set("make", newObj.getNameMake());
	        updateModel.where(criteriaBuilderModel.equal(rootModel.get("make"), oldDto.getNameMake()));
	        
	        entityManager.getTransaction().begin();
	        entityManager.createQuery(updateModel).executeUpdate();
	        entityManager.getTransaction().commit();

	        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
	        CriteriaUpdate<CarDto> update = criteriaBuilder.createCriteriaUpdate(CarDto.class);
	        Root<CarDto> root = update.from(CarDto.class);
	        update.set("make", newObj);
	        update.where(criteriaBuilder.equal(root.get("make"), oldDto));
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
		MakeDto makeDto = entityManager.find(MakeDto.class, id);
		if (makeDto!=null) {
			makeDto.deleteMakeDto();
			entityManager.getTransaction().commit();
		}
		entityManager.close();
		factory.close();
	}

	@Override
	public MakeDto findById(Object id) {
		MakeDto makeDto = entityManager.find(MakeDto.class, id);
		entityManager.close();
		factory.close();
		return makeDto;
	}

	@Override
	public List<MakeDto> getAll() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<MakeDto> criteriaQuery = criteriaBuilder.createQuery(MakeDto.class);
		Root<MakeDto> root = criteriaQuery.from(MakeDto.class);
		criteriaQuery.select(root);
		List<MakeDto> make = entityManager.createQuery(criteriaQuery).getResultList();
		return make;
	}
}
