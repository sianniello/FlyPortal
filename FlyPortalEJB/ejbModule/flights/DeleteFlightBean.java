package flights;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import replica.ReplicaManagerBean;
import replica.ReplicaManagerBeanLocal;
import flight.FlightException;

/**
 * Session Bean implementation class DeleteFlightBean
 */
@Stateless
@Remote
public class DeleteFlightBean implements DeleteFlightBeanLocal {

	@EJB
	private ReplicaManagerBeanLocal rm;
	/**
	 * Default constructor. 
	 */
	public DeleteFlightBean() {
	}

	@Override
	public boolean deleteFlight(String f) throws FlightException {
		rm = new ReplicaManagerBean();
		rm.init();
		String query = "DELETE FROM flights WHERE flight='" + f + "';";

		try{
			if(rm.executeUpdate(query))
				return true;
		}
		catch(Exception e){
			e.printStackTrace();
			throw new FlightException("Flight not present");
		}
		throw new FlightException("Flight not present");
	}

}
