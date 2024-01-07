package test.testUtil;

import java.util.List;

import org.junit.jupiter.api.Test;

import application.util.InspectionExpirySorter;
import application.util.NextEvent;

public class TestInspectionExpirySorter {

	@Test
	public void testInspectionExpiriesDates() {
		InspectionExpirySorter InsObj = new InspectionExpirySorter();
		List<NextEvent>	events = InsObj.inspectionExpiriesDates();
		for (NextEvent nextEvent : events) {
			System.out.println(nextEvent);
		}
	}
}
