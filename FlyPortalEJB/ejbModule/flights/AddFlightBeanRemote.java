package flights;

import flight.Flight;
import javax.ejb.Remote;

@Remote
public interface AddFlightBeanRemote {

	boolean addFlight(Flight f);

}
