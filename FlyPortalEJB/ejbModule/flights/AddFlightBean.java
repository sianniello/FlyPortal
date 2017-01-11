package flights;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import database.DatabaseException;
import replica.ReplicaManagerBeanRemote;
import flight.Flight;

/**
 * Session Bean implementation class AddFlightBean
 */
@Stateless
@Remote
public class AddFlightBean implements AddFlightBeanRemote{

	@EJB
	private ReplicaManagerBeanRemote rm;

	@Override
	public boolean addFlight(Flight f) {
		String query = "INSERT INTO flights (flight, dep_airport, arr_airport, dep_time, company, state, free_seats, seat_price) "
				+ "VALUES ('" + f.getFlight() + "', '" + f.getDepAirport() + "', '" + f.getArrAirport() + "', '" + 
				f.getDepTime() + "', '" + f.getCompany() + "', '" + f.getState() + "', '" + f.getFreeSeats() + "', '" +
				f.getPrice() + "');";
		try {
			if(rm.executeUpdate(query))
				return true;
		} catch (DatabaseException e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}

}
