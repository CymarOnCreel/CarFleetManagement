package application.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import application.dto.EmployeeDto;
import application.dto.ReservationDto;

public class ReservationDao implements ICrud<ReservationDto> {
	 public void setEntityManager(EntityManager entityManager) {
	        this.entityManager = entityManager;
	    }
	EntityManagerFactory factory = Persistence.createEntityManagerFactory("carfleet_manager");
	EntityManager entityManager = factory.createEntityManager();

	@Override
	public void save(ReservationDto obj) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(ReservationDto obj) {
		entityManager.getTransaction().begin();
		

	}

	@Override
	public void deleteById(Object id) {
		// TODO Auto-generated method stub

	}

	@Override
	public ReservationDto findById(Object id) {
	entityManager.getTransaction().begin();
	ReservationDto reservationById=entityManager.find(ReservationDto.class, id);
	System.out.println(reservationById.toStringWithNames());
	entityManager.getTransaction().commit();
	entityManager.close();
	return reservationById;
	}

	@Override
	public List<ReservationDto> getAll() {
		CriteriaBuilder criteriaBuilder=entityManager.getCriteriaBuilder();
		CriteriaQuery<ReservationDto> criteriaQuery=criteriaBuilder.createQuery(ReservationDto.class);
		Root<ReservationDto> root=criteriaQuery.from(ReservationDto.class);
		criteriaQuery.select(root);
		List<ReservationDto> reservations=entityManager.createQuery(criteriaQuery).getResultList();
		return reservations;
	}
	
	public List<ReservationDto> getReservationsByUserId(Long userId){
		CriteriaBuilder criteriaBuilder=entityManager.getCriteriaBuilder();
		CriteriaQuery<ReservationDto> criteriaQuery=criteriaBuilder.createQuery(ReservationDto.class);
		Root<ReservationDto> root=criteriaQuery.from(ReservationDto.class);
		criteriaQuery.where(criteriaBuilder.equal(root.get("employee"), userId));
		List<ReservationDto> reservations=entityManager.createQuery(criteriaQuery).getResultList();
		return reservations;
	}

	public List <ReservationDto> getReservationsByCarLicencePlate(String licencePlate){
		CriteriaBuilder criteriaBuilder=entityManager.getCriteriaBuilder();
		CriteriaQuery<ReservationDto> criteriaQuery= criteriaBuilder.createQuery(ReservationDto.class);
		Root<ReservationDto> root=criteriaQuery.from(ReservationDto.class);
		criteriaQuery.where(criteriaBuilder.equal(root.get("car"), licencePlate));
		List<ReservationDto>reservations=entityManager.createQuery(criteriaQuery).getResultList();
		return reservations;
	}
}
