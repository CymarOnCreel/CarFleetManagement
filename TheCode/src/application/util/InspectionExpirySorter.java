package application.util;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import application.dao.CarDao;
import application.dto.CarDto;

public class InspectionExpirySorter {
	
	private List<CarDto> cars = new ArrayList<>();
	private List<NextEvent> inspectionExpiries = new ArrayList<>();
	EntityManagerFactory factory = Persistence.createEntityManagerFactory("carfleet_manager");
	EntityManager entityManager = factory.createEntityManager();

	public List<NextEvent> inspectionExpiriesDates() {
		CarDao carDao = new CarDao();
		cars = carDao.getAll();
		for (CarDto carDto : cars) {
			NextEvent neObj = new NextEvent(carDto.getInspectionExpiryDate(), 
					carDto.getLicensePlate(), 
					"Műszaki vizsga", 0);
			inspectionExpiries.add(neObj);
		}
		return inspectionExpiries;
	}
}
