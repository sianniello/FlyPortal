package flights;

import javax.ejb.Remote;

import flight.FlightException;

@Remote
public interface DeleteFlightBeanRemote {

	boolean deleteFlight(String f) throws FlightException;

}
