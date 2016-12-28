package flights;

import javax.ejb.Local;

import flight.FlightException;

@Local
public interface DeleteFlightBeanLocal {

	boolean deleteFlight(String f) throws FlightException;

}
