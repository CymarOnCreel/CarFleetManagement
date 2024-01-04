package application.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import application.dto.RoleDto;

public class RoleDao implements ICrud<RoleDto>{
	
	EntityManagerFactory factory = Persistence.createEntityManagerFactory("carfleet_manager");
	EntityManager entityManager = factory.createEntityManager();

	@Override
	public void save(RoleDto obj) {
		entityManager.getTransaction().begin();
		entityManager.persist(obj);
		entityManager.getTransaction().commit();
		entityManager.close();
		factory.close();
		
	}

	
	public void update(RoleDto obj) {
		entityManager.getTransaction().begin();
		RoleDto roleDto = entityManager.find(RoleDto.class, obj.getNameRole());
		if (roleDto!=null) {
			roleDto.updateRoleDto(obj.getDescription()
		   );
		}
		entityManager.getTransaction().commit();
		entityManager.close();
		factory.close();
	}

	@Override
	public void deleteById(Object id) {
		entityManager.getTransaction().begin();
		RoleDto roleDto = entityManager.find(RoleDto.class, id);
		if (roleDto!=null) {
			roleDto.deleteRoleDto();
			entityManager.getTransaction().commit();
		}
		entityManager.close();
		factory.close();	
	}

	@Override
	public RoleDto findById(Object id) {
		RoleDto roleDto = entityManager.find(RoleDto.class, id);
		entityManager.close();
		factory.close();
		return roleDto;
	}

	@Override
	public List<RoleDto> getAll() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<RoleDto> criteriaQuery = criteriaBuilder.createQuery(RoleDto.class);
		Root<RoleDto> root = criteriaQuery.from(RoleDto.class);
		criteriaQuery.select(root);
		List<RoleDto> role = entityManager.createQuery(criteriaQuery).getResultList();
		return role;
	}
}
