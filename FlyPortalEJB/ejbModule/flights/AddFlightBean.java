package flights;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import database.DatabaseException;
import replica.ReplicaManagerBean;
import replica.ReplicaManagerBeanLocal;
import flight.Flight;

/**
 * Session Bean implementation class AddFlightBean
 */
@Stateless
@LocalBean
public class AddFlightBean {

	private ReplicaManagerBeanLocal rm;
	/**
	 * Default constructor. 
	 */
	public AddFlightBean() {
		// TODO Auto-generated constructor stub
	}

	public boolean addFlight(Flight f) {
		rm = new ReplicaManagerBean();
		rm.init();
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
