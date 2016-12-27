package flight;

import org.junit.Test;

public class FlightTest {

	@Test
	public void testFlight() {
		Flight f = new Flight("", "", "", "", "", "", 0);
		System.out.println(f.toString());
	}

}
