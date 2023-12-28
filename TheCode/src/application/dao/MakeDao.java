package application.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import application.dto.MakeDto;

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
	
	
	@Override
	public void update(MakeDto obj) {
		entityManager.getTransaction().begin();
		MakeDto makeDto = entityManager.find(MakeDto.class, obj.getNameMake());
		if (makeDto!=null) {
			makeDto.updateMakeDto(obj.getPicturePathMake());
		}
		entityManager.getTransaction().commit();
		entityManager.close();
		factory.close();
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
