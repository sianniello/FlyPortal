package flights;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.swing.RepaintManager;

import database.DatabaseException;
import replica.ReplicaManagerBean;
import replica.ReplicaManagerBeanLocal;
import flight.Flight;
import flight.FlightException;

/**
 * Session Bean implementation class FlightsBean
 */
@Stateless
@LocalBean
public class FlightsBean implements FlightBeanLocal{

	public LinkedList<Flight> flights;
	
	@EJB
	private ReplicaManagerBeanLocal rm;

	/**
	 * Default constructor. 
	 */
	public FlightsBean() {

	}

	@Override
	public LinkedList<Flight> getFlights() {

		flights = new LinkedList<>();
		rm = new ReplicaManagerBean();
		rm.init();

		String query = "SELECT * FROM flights WHERE dep_time >= CURDATE()";

		try {
			ResultSet rs = rm.executeQuery(query);

			while(rs.next()) 
				flights.add(new Flight(rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), Integer.parseInt(rs.getString(8)), Double.parseDouble(rs.getString(9))));
		} catch (NumberFormatException | SQLException | DatabaseException e) {
			e.printStackTrace();
		}
		return flights;
	}

	@Override
	public Flight getFlight(String f) throws FlightException {
		rm = new ReplicaManagerBean();
		rm.init();

		String query = "SELECT * FROM flights WHERE dep_time >= CURDATE() AND flight='"+ f +"'";
		ResultSet rs;
		
		try {
			rs = rm.executeQuery(query);
			if(rs.next())
				return new Flight(rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), Integer.parseInt(rs.getString(8)), Double.parseDouble(rs.getString(9)));
			else throw new FlightException("Flight not found");
		} catch (DatabaseException | NumberFormatException | SQLException e) {
			e.printStackTrace();
		}
		throw new FlightException("Flight not found");
	}

	@Override
	public LinkedList<Flight> getFlights(Set<String> list) throws FlightException {
		LinkedList<String> aux;
		rm = new ReplicaManagerBean();
		rm.init();
		if(list.size() > 0)
			try {
				aux = new LinkedList<>();
				flights = new LinkedList<>();
				for(String s : list)
					if(!s.contains("'")) 
						aux.add("'" + s + "'");
					else
						aux.add(s);

				String set = aux.toString();
				set = set.substring(1, set.length() - 1);
				String query = "SELECT * FROM flights WHERE dep_time >= CURDATE() AND flight IN (" + set + ");";
				ResultSet rs = rm.executeQuery(query);

				while(rs.next())
					flights.add(new Flight(rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), Integer.parseInt(rs.getString(8)), Double.parseDouble(rs.getString(9))));

				return flights;

			} catch (SQLException | DatabaseException e) {
				e.printStackTrace();
			}
		throw new FlightException("Flight not found");
	}

}
