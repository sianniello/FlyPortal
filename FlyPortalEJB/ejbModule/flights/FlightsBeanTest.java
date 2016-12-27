package flights;

import org.junit.Test;

public class FlightsBeanTest {

	private FlightsBean fb = new FlightsBean();
	
	@Test
	public void testGetFlights() {
		System.out.println(fb.getFlights().toString());
	}

}
