package application.util;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import application.dao.InsuranceDao;
import application.dto.InsuranceDto;

public class InsuranceSorter {
	

	public List<NextEvent> insuranceRenewalDates() {
		List<NextEvent> insuranceRenewals = new ArrayList<>();
		
		InsuranceDao insuranceDao = new InsuranceDao();
		List<InsuranceDto> insurances = insuranceDao.getAll();
		
		List<InsuranceDto> compulsoryMotorInsurances = new ArrayList<>();
		
		for (InsuranceDto insuranceDto : insurances) {
			if (insuranceDto.getInsuranceType().contains("Kötelező")) {
				compulsoryMotorInsurances.add(insuranceDto);
			}
		}
		
		for (InsuranceDto insuranceDto : compulsoryMotorInsurances) {
			
			LocalDate actualDate = insuranceDto.getExpireDate().minusDays(30L);
			
			actualDate = LocalDate.now().withMonth(actualDate.getMonthValue())
		            .withDayOfMonth(actualDate.getDayOfMonth());
			
			if (actualDate.isBefore(LocalDate.now())) {
				actualDate = actualDate.plusYears(1L);
			}
			
			actualDate = actualDate.minusDays(30L);
			
			NextEvent neObj = new NextEvent(actualDate, insuranceDto.getLicensePlate(), 
					"Kötelező felelősségbiztosítás újrakötése (30 nap)", "");
			insuranceRenewals.add(neObj);
			
		}
		return insuranceRenewals;
	}
	

}
