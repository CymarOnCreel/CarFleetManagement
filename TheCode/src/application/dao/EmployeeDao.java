package application.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import application.dto.EmployeeDto;

public class EmployeeDao implements ICrud<EmployeeDto>{
	
	EntityManagerFactory factory = Persistence.createEntityManagerFactory("carfleet_manager");
	EntityManager entityManager = factory.createEntityManager();

	@Override
	public void save(EmployeeDto obj) {
		entityManager.getTransaction().begin();
		entityManager.persist(obj);
		entityManager.getTransaction().commit();
		entityManager.close();
		factory.close();	
	}
	

	
	public void update(EmployeeDto obj) {
		entityManager.getTransaction().begin();
		EmployeeDto employeeDto = entityManager.find(EmployeeDto.class, obj.getIdEmployee());
		if (employeeDto!=null) {
			employeeDto.updateEmployeeDto(obj.getLastName(),
					    obj.getFirstName(),
					    obj.getEmail(),
					    obj.getPassword(),
					    obj.getDriverLicense(),
					    obj.getRoleName(),
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
		EmployeeDto employeeDto = entityManager.find(EmployeeDto.class, id);
		if (employeeDto!=null) {
			employeeDto.deleteEmployeeDto();
			entityManager.getTransaction().commit();
		}
		entityManager.close();
		factory.close();	
	}

	@Override
	public EmployeeDto findById(Object id) {
		EmployeeDto employeeDto = entityManager.find(EmployeeDto.class, id);
		entityManager.close();
		factory.close();
		return employeeDto;
	}

	@Override
	public List<EmployeeDto> getAll() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<EmployeeDto> criteriaQuery = criteriaBuilder.createQuery(EmployeeDto.class);
		Root<EmployeeDto> root = criteriaQuery.from(EmployeeDto.class);
		criteriaQuery.select(root);
		List<EmployeeDto> employee = entityManager.createQuery(criteriaQuery).getResultList();
		return employee;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Long validateEmployeeByEmailAndPasswordLong(String email, String password) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(Long.class);
		Root<EmployeeDto> from = criteriaQuery.from(EmployeeDto.class);
		CriteriaQuery<Long> canLogin = criteriaQuery.select(criteriaBuilder.count(from)).where(criteriaBuilder.and(criteriaBuilder.equal(from.get("email"), email),
			    criteriaBuilder.equal(from.get("password"), password)));
			  
			  TypedQuery<Long> typedQuery = entityManager.createQuery(canLogin);
			  Long result = typedQuery.getSingleResult();
			  return result;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public EmployeeDto getEmployeeByEmail(String email) {
		CriteriaBuilder builder= entityManager.getCriteriaBuilder();
		CriteriaQuery query=builder.createQuery(EmployeeDto.class);
		Root<EmployeeDto> from=query.from(EmployeeDto.class);
		query.where(builder.equal(from.get("email"), email));
	
		EmployeeDto employee=(EmployeeDto) entityManager.createQuery(query).getSingleResult();
		return employee;
	}
	
}
