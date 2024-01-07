package test.testUtil;

import java.util.List;

import org.junit.jupiter.api.Test;

import application.util.MaintenanceSorter;
import application.util.NextEvent;

public class TestMaintenanceSorter {
	
	@Test
	public void testNextMaintenanceDates() {
		MaintenanceSorter mtsObj = new MaintenanceSorter();
		List<NextEvent> testMaintenances = mtsObj.nextMaintenanceDates();
		for (NextEvent nextEvent : testMaintenances) {
			System.out.println(nextEvent);
		}
	}
	
	@Test
	public void testLastMaintenances() {
		MaintenanceSorter mtsObj = new MaintenanceSorter();
		mtsObj.lastMaintenances();
	}

}
