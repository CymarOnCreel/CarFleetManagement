package application.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import application.dto.MaintenanceTypeDto;

public class MaintenanceTypeDao implements ICrud<MaintenanceTypeDto>{
	
	EntityManagerFactory factory = Persistence.createEntityManagerFactory("carfleet_manager");
	EntityManager entityManager = factory.createEntityManager();

	@Override
	public void save(MaintenanceTypeDto obj) {
		entityManager.getTransaction().begin();
		entityManager.persist(obj);
		entityManager.getTransaction().commit();
		entityManager.close();
		factory.close();
		
	}

	
	public void update(MaintenanceTypeDto obj) {
		entityManager.getTransaction().begin();
		MaintenanceTypeDto maintenanceTypeDto = entityManager.find(MaintenanceTypeDto.class, obj.getNameMaintenanceType());
		if (maintenanceTypeDto!=null) {
			maintenanceTypeDto.updateMaintenanceTypeDto(obj.getNameMaintenanceType()
		   );
	   }
		entityManager.getTransaction().commit();
		entityManager.close();
		factory.close();
	}

	@Override
	public void deleteById(Object id) {
		entityManager.getTransaction().begin();
		MaintenanceTypeDto maintenanceTypeDto = entityManager.find(MaintenanceTypeDto.class, id);
		if (maintenanceTypeDto!=null) {
			maintenanceTypeDto.deleteMaintenanceTypeDto();
			entityManager.getTransaction().commit();
		}
		entityManager.close();
		factory.close();
	}

	@Override
	public MaintenanceTypeDto findById(Object id) {
		MaintenanceTypeDto maintenanceTypeDto = entityManager.find(MaintenanceTypeDto.class, id);
		entityManager.close();
		factory.close();
		return maintenanceTypeDto;
	}

	@Override
	public List<MaintenanceTypeDto> getAll() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<MaintenanceTypeDto> criteriaQuery = criteriaBuilder.createQuery(MaintenanceTypeDto.class);
		Root<MaintenanceTypeDto> root = criteriaQuery.from(MaintenanceTypeDto.class);
		criteriaQuery.select(root);
		List<MaintenanceTypeDto> maintenanceType = entityManager.createQuery(criteriaQuery).getResultList();
		return maintenanceType;
	}
}
