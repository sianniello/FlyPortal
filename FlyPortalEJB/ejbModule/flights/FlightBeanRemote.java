package flights;

import java.util.LinkedList;
import java.util.Set;

import javax.ejb.Remote;

import flight.Flight;
import flight.FlightException;

@Remote
public interface FlightBeanRemote {

	Flight getFlight(String f) throws FlightException;

	LinkedList<Flight> getFlights();

	LinkedList<Flight> getFlights(Set<String> list) throws FlightException;

}
