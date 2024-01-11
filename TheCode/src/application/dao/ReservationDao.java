package application.dao;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import application.dto.CarDto;
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
		entityManager.getTransaction().begin();
		entityManager.persist(obj);
		entityManager.getTransaction().commit();
		entityManager.close();
		factory.close();

	}

	
	public void update(ReservationDto obj) {
		entityManager.getTransaction().begin();
		ReservationDto reservationDto = entityManager.find(ReservationDto.class, obj.getIdReservation());
		if (reservationDto != null) {
			reservationDto.updateReservationDto(obj.getEmployee(), obj.getCar(), obj.getStartDateTime(),
					obj.getEndDateTime(), obj.getDescription());
		}
		entityManager.getTransaction().commit();
		entityManager.close();
		factory.close();
	}

	@Override
	public void deleteById(Object id) {
		entityManager.getTransaction().begin();
		ReservationDto reservationById = entityManager.find(ReservationDto.class, id);
		if (reservationById != null) {
			reservationById.deleteReservation();
			entityManager.merge(reservationById);
			entityManager.getTransaction().commit();
		}
		;
		entityManager.close();
		factory.close();
	}

	@Override
	public ReservationDto findById(Object id) {

		ReservationDto reservationDto = entityManager.find(ReservationDto.class, id);
		entityManager.close();
		factory.close();
		return reservationDto;
	}

	@Override
	public List<ReservationDto> getAll() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<ReservationDto> criteriaQuery = criteriaBuilder.createQuery(ReservationDto.class);
		Root<ReservationDto> root = criteriaQuery.from(ReservationDto.class);
		criteriaQuery.select(root);
		List<ReservationDto> reservations = entityManager.createQuery(criteriaQuery).getResultList();
		return reservations;

	}

	public List<ReservationDto> getReservationsByUserId(Long userId) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<ReservationDto> criteriaQuery = criteriaBuilder.createQuery(ReservationDto.class);
		Root<ReservationDto> root = criteriaQuery.from(ReservationDto.class);
		criteriaQuery.where(criteriaBuilder.equal(root.get("employee"), userId));
		List<ReservationDto> reservations = entityManager.createQuery(criteriaQuery).getResultList();
		return reservations;
	}

	public List<ReservationDto> getReservationsByCarLicencePlate(String licencePlate) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<ReservationDto> criteriaQuery = criteriaBuilder.createQuery(ReservationDto.class);
		Root<ReservationDto> root = criteriaQuery.from(ReservationDto.class);
		 Join<ReservationDto, CarDto> carJoin = root.join("car");
		criteriaQuery.where(criteriaBuilder.equal(carJoin.get("licensePlate"), licencePlate));
		List<ReservationDto> reservations = entityManager.createQuery(criteriaQuery).getResultList();
		return reservations;
	}
	
	public boolean isReservedByCarLicensePlate(String licensePlate) {
	    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
	    CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
	    Root<ReservationDto> root = criteriaQuery.from(ReservationDto.class);

	    criteriaQuery.select(criteriaBuilder.count(root));
	    
	    criteriaQuery.where(
		        criteriaBuilder.equal(root.get("car").get("licensePlate"), licensePlate),
		        criteriaBuilder.greaterThanOrEqualTo(root.get("endDateTime"), LocalDate.now().atStartOfDay()),
		        criteriaBuilder.lessThanOrEqualTo(root.get("startDateTime"), LocalDate.now().atStartOfDay())
		);

	    Long reservationCount = entityManager.createQuery(criteriaQuery).getSingleResult();
	    
	    return reservationCount > 0;
	}
	
	public EmployeeDto getEmployeeForNowReservedCar(String licensePlate) {
	    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
	    CriteriaQuery<EmployeeDto> criteriaQuery = criteriaBuilder.createQuery(EmployeeDto.class);
	    Root<ReservationDto> root = criteriaQuery.from(ReservationDto.class);

	    criteriaQuery.select(root.get("employee"));
	    
	    criteriaQuery.where(
		        criteriaBuilder.equal(root.get("car").get("licensePlate"), licensePlate),
		        criteriaBuilder.greaterThanOrEqualTo(root.get("endDateTime"), LocalDate.now().atStartOfDay()), 
		        criteriaBuilder.lessThanOrEqualTo(root.get("startDateTime"), LocalDate.now().atStartOfDay())
		);

	    List<EmployeeDto> employees = entityManager.createQuery(criteriaQuery).getResultList();
	    
	    if (!employees.isEmpty()) {
	        return employees.get(0); 
	    } else {
	        return null;
	    }
	}


}
