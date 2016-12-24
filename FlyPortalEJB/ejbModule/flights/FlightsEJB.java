package flights;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import flight.Flight;

/**
 * Session Bean implementation class FlightsEJB
 */
@Stateless
@LocalBean
public class FlightsEJB implements FlightsEJBLocal {

	/**
	 * Default constructor. 
	 */
	public FlightsEJB() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public LinkedList<Flight> getFlights() {
		LinkedList<Flight> flights = new LinkedList<>();
		Connection con = null;
		ResultSet rs = null;
		String url = "jdbc:mysql://localhost:3306/";;
		String db = "FlyPortal";
		String query = "SELECT * FROM flights;";

		try{
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(url+db,"admin","password");
			Statement stmt = con.createStatement();
			rs = stmt.executeQuery(query);

			while(rs.next()) {
				flights.add(new Flight(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6)));
			}
			return flights;
		}
		catch(Exception e){
			System.out.println(e.getMessage());
			return null;
		}
	}

}
