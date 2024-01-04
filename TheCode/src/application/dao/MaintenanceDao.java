package application.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import application.dto.MaintenanceDto;

public class MaintenanceDao implements ICrud<MaintenanceDto>{
	
	EntityManagerFactory factory = Persistence.createEntityManagerFactory("carfleet_manager");
	EntityManager entityManager = factory.createEntityManager();

	@Override
	public void save(MaintenanceDto obj) {
		entityManager.getTransaction().begin();
		entityManager.persist(obj);
		entityManager.getTransaction().commit();
		entityManager.close();
		factory.close();
		
	}

	
	public void update(MaintenanceDto obj) {
		entityManager.getTransaction().begin();
		MaintenanceDto maintenanceDto = entityManager.find(MaintenanceDto.class, obj.getIdMaintenance());
		if (maintenanceDto!=null) {
			maintenanceDto.updateMaintenanceDto(obj.getLicensePlate(),
					obj.getMaintenanceType(),
					obj.getServiceCompany(),
					obj.getDate(),
					obj.getMileage(),
					obj.getDescription(),
					obj.getAmount(),
					obj.getCreatedAt(),
					obj.getUpdatedAt()
			);
		}
		entityManager.getTransaction().commit();
		entityManager.close();
		factory.close();
	}

	@Override
	public void deleteById(Object id) {
		entityManager.getTransaction().begin();
		MaintenanceDto maintenanceDto = entityManager.find(MaintenanceDto.class, id);
		if (maintenanceDto!=null) {
			maintenanceDto.deleteMaintenanceDto();
			entityManager.getTransaction().commit();
		}
		entityManager.close();
		factory.close();
	}

	@Override
	public MaintenanceDto findById(Object id) {
		MaintenanceDto maintenanceDto = entityManager.find(MaintenanceDto.class, id);
		entityManager.close();
		factory.close();
		return maintenanceDto;
	}

	@Override
	public List<MaintenanceDto> getAll() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<MaintenanceDto> criteriaQuery = criteriaBuilder.createQuery(MaintenanceDto.class);
		Root<MaintenanceDto> root = criteriaQuery.from(MaintenanceDto.class);
		criteriaQuery.select(root);
		List<MaintenanceDto> maintenance = entityManager.createQuery(criteriaQuery).getResultList();
		return maintenance;
	}
}
