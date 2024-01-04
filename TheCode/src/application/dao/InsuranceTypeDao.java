package application.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import application.dto.InsuranceTypeDto;

public class InsuranceTypeDao implements ICrud<InsuranceTypeDto>{
	
	EntityManagerFactory factory = Persistence.createEntityManagerFactory("carfleet_manager");
	EntityManager entityManager = factory.createEntityManager();

	@Override
	public void save(InsuranceTypeDto obj) {
		entityManager.getTransaction().begin();
		entityManager.persist(obj);
		entityManager.getTransaction().commit();
		entityManager.close();
		factory.close();
		
	}

	
	public void update(InsuranceTypeDto obj) {
		entityManager.getTransaction().begin();
		InsuranceTypeDto insuranceTypeDto = entityManager.find(InsuranceTypeDto.class,obj.getNameInsuranceType());
		if (insuranceTypeDto!=null) {
			insuranceTypeDto.updateInsuranceTypeDto(obj.getNameInsuranceType()
			);
		}
		entityManager.getTransaction().commit();
		entityManager.close();
		factory.close();
	}

	@Override
	public void deleteById(Object id) {
		entityManager.getTransaction().begin();
		InsuranceTypeDto insuranceTypeDto = entityManager.find(InsuranceTypeDto.class, id);
		if (insuranceTypeDto!=null) {
			insuranceTypeDto.deleteInsuranceTypeDto();
			entityManager.getTransaction().commit();
		}
		entityManager.close();
		factory.close();	
	}

	@Override
	public InsuranceTypeDto findById(Object id) {
		InsuranceTypeDto insuranceTypeDto = entityManager.find(InsuranceTypeDto.class, id);
		entityManager.close();
		factory.close();
		return insuranceTypeDto;
	}

	@Override
	public List<InsuranceTypeDto> getAll() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<InsuranceTypeDto> criteriaQuery = criteriaBuilder.createQuery(InsuranceTypeDto.class);
		Root<InsuranceTypeDto> root = criteriaQuery.from(InsuranceTypeDto.class);
		criteriaQuery.select(root);
		List<InsuranceTypeDto> insuranceType = entityManager.createQuery(criteriaQuery).getResultList();
		return insuranceType;
	}
}
