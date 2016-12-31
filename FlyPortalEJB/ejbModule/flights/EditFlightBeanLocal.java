package flights;

import javax.ejb.Local;

import flight.Flight;

@Local
public interface EditFlightBeanLocal {

	boolean editFlight(Flight f, String prec);
	
}
