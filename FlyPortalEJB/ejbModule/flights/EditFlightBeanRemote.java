package flights;

import javax.ejb.Remote;

import flight.Flight;

@Remote
public interface EditFlightBeanRemote {

	boolean editFlight(Flight f, String prec);
	
}
