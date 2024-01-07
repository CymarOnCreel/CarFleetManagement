package test.testUtil;

import org.junit.jupiter.api.Test;

import application.util.InsuranceSorter;

public class TestInsuranceSorter {
	
	@Test
	public void TestInsuranceRenewalDatesDto() {
		InsuranceSorter insSorter = new InsuranceSorter();
		insSorter.insuranceRenewalDates();
	}

}
