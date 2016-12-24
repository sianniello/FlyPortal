package flights;

import java.util.LinkedList;

import javax.ejb.Local;

import flight.Flight;

@Local
public interface FlightsEJBLocal {

	 public LinkedList<Flight> getFlights();
	
}
