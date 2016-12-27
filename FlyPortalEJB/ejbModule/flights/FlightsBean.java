package flights;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import flight.Flight;

/**
 * Session Bean implementation class FlightsBean
 */
@Stateless
@LocalBean
public class FlightsBean {

	public LinkedList<Flight> flights;
	private Statement stmt;

	/**
	 * Default constructor. 
	 */
	public FlightsBean() {
		
	}

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
				flights.add(new Flight(rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), Integer.parseInt(rs.getString(8))));
				System.out.println(flights);
			}
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return flights;
	}

}
