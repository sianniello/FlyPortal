package flights;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.Set;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import flight.Flight;
import flight.FlightException;

/**
 * Session Bean implementation class FlightsBean
 */
@Stateless
@LocalBean
public class FlightsBean implements FlightBeanLocal{

	public LinkedList<Flight> flights;
	private Statement stmt;

	/**
	 * Default constructor. 
	 */
	public FlightsBean() {

	}

	@Override
	public LinkedList<Flight> getFlights() {

		flights = new LinkedList<>();
		Connection con = null;
		String url = "jdbc:mysql://localhost:3306/";
		String db = "fly_portal";

		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(url+db+"?autoReconnect=true&useSSL=false","admin","password");
			stmt = con.createStatement();
			ResultSet rs;
			String query = "SELECT * FROM flights WHERE dep_time >= CURDATE()";

			rs = stmt.executeQuery(query);
			while(rs.next()) {
				System.out.println("hello " + rs.toString());
				flights.add(new Flight(rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), Integer.parseInt(rs.getString(8)), Double.parseDouble(rs.getString(9))));
				System.out.println(flights);
			}
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return flights;
	}

	@Override
	public Flight getFlight(String f) throws FlightException {

		Connection con = null;
		String url = "jdbc:mysql://localhost:3306/";
		String db = "fly_portal";

		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(url+db+"?autoReconnect=true&useSSL=false","admin","password");
			stmt = con.createStatement();
			ResultSet rs;
			String query = "SELECT * FROM flights WHERE dep_time >= CURDATE() AND flight='"+ f +"'";

			rs = stmt.executeQuery(query);

			if(rs.next())
				return new Flight(rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), Integer.parseInt(rs.getString(8)), Double.parseDouble(rs.getString(9)));
			else throw new FlightException("Flight not found");

		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		throw new FlightException("Flight not found");
	}

	@Override
	public LinkedList<Flight> getFlights(Set<String> list) throws FlightException {
		LinkedList<String> aux;
		Connection con = null;
		String url = "jdbc:mysql://localhost:3306/";
		String db = "fly_portal";
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
				Class.forName("com.mysql.jdbc.Driver");
				con = DriverManager.getConnection(url+db+"?autoReconnect=true&useSSL=false","admin","password");
				stmt = con.createStatement();
				ResultSet rs;
				String query = "SELECT * FROM flights WHERE dep_time >= CURDATE() AND flight IN (" + set + ");";
				System.out.println(query);
				rs = stmt.executeQuery(query);

				while(rs.next())
					flights.add(new Flight(rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), Integer.parseInt(rs.getString(8)), Double.parseDouble(rs.getString(9))));

				return flights;

			} catch (SQLException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		throw new FlightException("Flight not found");
	}

}
