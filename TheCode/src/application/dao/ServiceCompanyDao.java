package application.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import application.dto.ServiceCompanyDto;

public class ServiceCompanyDao implements ICrud<ServiceCompanyDto>{
	
	EntityManagerFactory factory = Persistence.createEntityManagerFactory("carfleet_manager");
	EntityManager entityManager = factory.createEntityManager();

	@Override
	public void save(ServiceCompanyDto obj) {
		entityManager.getTransaction().begin();
		entityManager.persist(obj);
		entityManager.getTransaction().commit();
		entityManager.close();
		factory.close();
		
	}

	@Override
	public void update(ServiceCompanyDto obj) {
		entityManager.getTransaction().begin();
		ServiceCompanyDto serviceCompanyDto = entityManager.find(ServiceCompanyDto.class,obj.getNameServiceCompany());
		if (serviceCompanyDto!=null) {
			serviceCompanyDto.updateServiceCompanyDto(obj.getLocation(),
					obj.getContactPerson(),
					obj.getContactEmail(),
					obj.getContactPhone(),
					obj.getCreatedAt(),
					obj.getUpdatedAt(),
					obj.isEnabled()
			);
		}
		entityManager.getTransaction().commit();
		entityManager.close();
		factory.close();
	}

	@Override
	public void deleteById(Object id) {
		entityManager.getTransaction().begin();
		ServiceCompanyDto serviceCompanyDto = entityManager.find(ServiceCompanyDto.class, id);
		if (serviceCompanyDto!=null) {
			serviceCompanyDto.deleteServiceCompanyDto();
			entityManager.getTransaction().commit();
		}
		entityManager.close();
		factory.close();
	}

	@Override
	public ServiceCompanyDto findById(Object id) {
		ServiceCompanyDto serviceCompanyDto = entityManager.find(ServiceCompanyDto.class, id);
		entityManager.close();
		factory.close();
		return serviceCompanyDto;
	}

	@Override
	public List<ServiceCompanyDto> getAll() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<ServiceCompanyDto> criteriaQuery = criteriaBuilder.createQuery(ServiceCompanyDto.class);
		Root<ServiceCompanyDto> root = criteriaQuery.from(ServiceCompanyDto.class);
		criteriaQuery.select(root);
		List<ServiceCompanyDto> service = entityManager.createQuery(criteriaQuery).getResultList();
		return service;
	}
}
