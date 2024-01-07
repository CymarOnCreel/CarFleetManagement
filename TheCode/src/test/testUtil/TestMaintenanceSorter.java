package test.testUtil;

import java.util.List;

import org.junit.jupiter.api.Test;

import application.util.NextEvent;
import application.util.MaintenanceSorter;

public class TestMaintenanceSorter {
	
	@Test
	public void TestNextMaintenanceDates() {
		MaintenanceSorter mtsObj = new MaintenanceSorter();
		List<NextEvent> testMaintenances = mtsObj.nextMaintenanceDates();
		
	}

}
