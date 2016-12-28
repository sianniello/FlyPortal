package flyPortalData;

import java.util.LinkedList;

import org.junit.Test;

public class FlyPortalDataTest {

	@Test
	public void testGetAirports() {
		LinkedList<Airport> list1 = FlyPortalData.getAirports();
		System.out.println(list1.toString());
	}

	@Test
	public void testGetAirlines() {
		LinkedList<String> list2 = FlyPortalData.getAirlines();
		System.out.println(list2.toString());
	}

}
