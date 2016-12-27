package booking;

import java.util.TreeMap;

import flight.Flight;
import flight.FlightException;

public interface BookingBeanLocal {

	void initialize();

	void addFlight(Flight f);

	void removeFlight(String flight) throws FlightException;
	
	TreeMap<String, Flight> getBooking();
	
}