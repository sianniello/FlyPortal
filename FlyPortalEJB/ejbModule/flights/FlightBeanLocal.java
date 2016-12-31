package flights;

import java.util.LinkedList;
import java.util.Set;

import flight.Flight;
import flight.FlightException;

public interface FlightBeanLocal {

	Flight getFlight(String f) throws FlightException;

	LinkedList<Flight> getFlights();

	LinkedList<Flight> getFlights(Set<String> list) throws FlightException;

}
