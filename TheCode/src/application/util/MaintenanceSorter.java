package application.util;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import application.dao.CarDao;
import application.dto.CarDto;
import application.dto.MaintenanceDto;

public class MaintenanceSorter {
	
	private List<MaintenanceDto> maintenances = new ArrayList<>();
	private List<NextEvent> nextMaintenance = new ArrayList<>();
	EntityManagerFactory factory = Persistence.createEntityManagerFactory("carfleet_manager");
	EntityManager entityManager = factory.createEntityManager();
	
	public List<NextEvent> nextMaintenanceDates() {
		
		lastMaintenances();
		for (MaintenanceDto maintenanceDto : maintenances) {
			nextMaintenance.add(calculateExpectedDate(maintenanceDto));
		}
		CarDao carDaoObj = new CarDao();
		List<CarDto> cars = carDaoObj.getAll();
		for (CarDto carDto : cars) {
			if (!containsCarInMaintenances(carDto)) {
				nextMaintenance.add(addCarWithoutMaintenance(carDto));
			}
		}
		
		entityManager.close();
		factory.close();

		return nextMaintenance;
		
	}
	
	private NextEvent addCarWithoutMaintenance(CarDto carDto) {
		int interval = carDto.getServiceInterval();
		int actualMileage = carDto.getMileage();
		int distance = actualMileage;
		long elapsedDays = ChronoUnit.DAYS.between(carDto.getCreatedAt(), LocalDate.now());
		long expectedDays = elapsedDays * interval / distance;
		LocalDate nextMaintenanceDate = carDto.getCreatedAt().plusDays(expectedDays);
		if (nextMaintenanceDate.isAfter(carDto.getCreatedAt().plusYears(1L))) {
			nextMaintenanceDate = carDto.getCreatedAt().plusYears(1L);
		}
		String description = "";
		if (actualMileage < interval) {
			description = "Az autó első kötelező szervize";
		}else {
			description = "Elmaradt kötelező szerviz!";
		}
		return new NextEvent(nextMaintenanceDate, carDto.getLicensePlate(), description, (interval-distance)+"");
	}

	private boolean containsCarInMaintenances(CarDto carDto) {
		return maintenances.stream().anyMatch(maintenance -> maintenance.getLicensePlate().equals(carDto.getLicensePlate()));
	}

	private NextEvent calculateExpectedDate(MaintenanceDto maintenanceDto) {
		CarDao carDao = new CarDao();
		CarDto car = carDao.findById(maintenanceDto.getLicensePlate());
		int interval = car.getServiceInterval();
		int actualMileage = car.getMileage();	
		int distance = actualMileage - maintenanceDto.getMileage(); 
		long elapsedDays = ChronoUnit.DAYS.between(maintenanceDto.getDate(), LocalDate.now()); 
		long expectedDays = 0;
		if (distance > 0) {
			expectedDays = elapsedDays * interval / distance;
		}else {
			expectedDays = 370;
		}
		LocalDate nextMaintenanceDate = maintenanceDto.getDate().plusDays(expectedDays);
		if (nextMaintenanceDate.isAfter(maintenanceDto.getDate().plusYears(1L))) {
			nextMaintenanceDate = maintenanceDto.getDate().plusYears(1L);
		}
		
		return new NextEvent(nextMaintenanceDate, maintenanceDto.getLicensePlate(), "Kötelező éves szerviz", (interval-distance)+"");
	}
	
	public void lastMaintenances() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<MaintenanceDto> criteriaQuery = criteriaBuilder.createQuery(MaintenanceDto.class);
		Root<MaintenanceDto> root = criteriaQuery.from(MaintenanceDto.class);

		Subquery<Integer> subquery = criteriaQuery.subquery(Integer.class);
		Root<MaintenanceDto> subqueryRoot = subquery.from(MaintenanceDto.class);
		subquery.select(criteriaBuilder.max(subqueryRoot.get("mileage")))
		        .where(
		            criteriaBuilder.equal(subqueryRoot.get("licensePlate"), root.get("licensePlate")),
		            criteriaBuilder.equal(subqueryRoot.get("maintenanceType"), "Kötelező szerviz")
		        )
		        .groupBy(subqueryRoot.get("licensePlate"));

		criteriaQuery.select(root)
		        .where(
		            criteriaBuilder.and(
		                criteriaBuilder.equal(root.get("mileage"), subquery),
		                criteriaBuilder.equal(root.get("maintenanceType"), "Kötelező szerviz")
		            )
		        );

		maintenances = entityManager.createQuery(criteriaQuery).getResultList();

	}

}
