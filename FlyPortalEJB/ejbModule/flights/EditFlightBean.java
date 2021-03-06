package flights;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import replica.ReplicaManagerBean;
import replica.ReplicaManagerBeanRemote;
import flight.Flight;

/**
 * Session Bean implementation class EditFlightBean
 */
@Stateless
@LocalBean
public class EditFlightBean implements EditFlightBeanRemote {

	@EJB
	ReplicaManagerBeanRemote rm;

	/**
	 * Default constructor. 
	 */
	public EditFlightBean() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean editFlight(Flight f, String prec) {
		rm = new ReplicaManagerBean();
		rm.init();
		String query = "UPDATE flights SET flight='" + f.getFlight() + "', dep_airport='" + f.getDepAirport() + "', "
				+ "arr_airport='" + f.getArrAirport() + "', dep_time='" + f.getDepTime() + "', " 
				+ "company='" + f.getCompany() + "', state='" + f.getState() + "', free_seats='" + f.getFreeSeats() + "', "
				+ "seat_price='" + f.getPrice() + "' WHERE flight='" + prec + "';";

		try{
			if(rm.executeUpdate(query))
				return true;
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return false;
	}
}

