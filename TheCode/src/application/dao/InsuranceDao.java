package application.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import application.dto.InsuranceDto;

public class InsuranceDao implements ICrud<InsuranceDto>{
	
	EntityManagerFactory factory = Persistence.createEntityManagerFactory("carfleet_manager");
	EntityManager entityManager = factory.createEntityManager();

	@Override
	public void save(InsuranceDto obj) {
		entityManager.getTransaction().begin();
		entityManager.persist(obj);
		entityManager.getTransaction().commit();
		entityManager.close();
		factory.close();
		
	}

	
	public void update(InsuranceDto obj) {
		entityManager.getTransaction().begin();
		InsuranceDto insuranceDto = entityManager.find(InsuranceDto.class, obj.getInsuranceId());
		if (insuranceDto!=null) {
			insuranceDto.updateInsuranceDto(obj.getLicensePlate(),
					obj.getInsuranceType(),
					obj.getInsurerName(),
					obj.getPrice(),
					obj.getExpireDate(),
					obj.getPayPeriod(),
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
		InsuranceDto insuranceDto = entityManager.find(InsuranceDto.class, id);
		if (insuranceDto!=null) {
			insuranceDto.deleteInsuranceDto();
			entityManager.getTransaction().commit();
		}
		entityManager.close();
		factory.close();
	}

	@Override
	public InsuranceDto findById(Object id) {
		InsuranceDto insuranceDto = entityManager.find(InsuranceDto.class, id);
		entityManager.close();
		factory.close();
		return insuranceDto;
	}

	@Override
	public List<InsuranceDto> getAll() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<InsuranceDto> criteriaQuery = criteriaBuilder.createQuery(InsuranceDto.class);
		Root<InsuranceDto> root = criteriaQuery.from(InsuranceDto.class);
		criteriaQuery.select(root);
		List<InsuranceDto> insurance = entityManager.createQuery(criteriaQuery).getResultList();
		return insurance;
	}
}
