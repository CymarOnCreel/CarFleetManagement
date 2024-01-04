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
import application.dto.CategoryDto;

public class CategoryDao implements ICrud<CategoryDto>{
	
	EntityManagerFactory factory = Persistence.createEntityManagerFactory("carfleet_manager");
	EntityManager entityManager = factory.createEntityManager();

	@Override
	public void save(CategoryDto obj) {
		entityManager.getTransaction().begin();
		entityManager.persist(obj);
		entityManager.getTransaction().commit();
		entityManager.close();
		factory.close();	
	}

	
	public void update(CategoryDto oldObj, CategoryDto newObj) {
		CategoryDto oldDto = entityManager.find(CategoryDto.class, oldObj.getNameCategory());
		if (oldDto != null) {
			entityManager.getTransaction().begin();
	        entityManager.persist(newObj);
	        entityManager.getTransaction().commit();

	        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
	        CriteriaUpdate<CarDto> update = criteriaBuilder.createCriteriaUpdate(CarDto.class);
	        Root<CarDto> root = update.from(CarDto.class);
	        update.set("category", newObj); 
	        update.where(criteriaBuilder.equal(root.get("category"), oldDto));
	        
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
		CategoryDto categoryDto = entityManager.find(CategoryDto.class, id);
		if (categoryDto!=null) {
			categoryDto.deleteCategoryDto();
			entityManager.getTransaction().commit();
		}
		entityManager.close();
		factory.close();	
	}

	@Override
	public CategoryDto findById(Object id) {
		CategoryDto categoryDto = entityManager.find(CategoryDto.class, id);
		entityManager.close();
		factory.close();
		return categoryDto;
	}

	@Override
	public List<CategoryDto> getAll() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<CategoryDto> criteriaQuery = criteriaBuilder.createQuery(CategoryDto.class);
		Root<CategoryDto> root = criteriaQuery.from(CategoryDto.class);
		criteriaQuery.select(root);
		List<CategoryDto> category = entityManager.createQuery(criteriaQuery).getResultList();
		return category;
	}
}
