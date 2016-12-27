package booking;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.TreeMap;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;

import flight.Flight;
import flight.FlightException;

/**
 * Session Bean implementation class FlightsBean
 */
@Stateful
@LocalBean
public class BookingBean implements BookingBeanLocal {

	private TreeMap<String, Flight> flights;	//user's booked flights
	private Statement stmt;

	/**
	 * Default constructor. 
	 */
	public BookingBean() {
	}

	@Override
	public void initialize() {
		flights = new TreeMap<String, Flight>();

		Connection con = null;
		String url = "jdbc:mysql://localhost:3306/";
		String db = "FlyPortal";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(url+db,"admin","password");
			stmt = con.createStatement();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void addFlight(Flight f) {
		try {
			if(check(f))
				flights.put(f.getFlight(), f);
		} catch (FlightException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void removeFlight(String flight) throws FlightException {
		if(flights.containsKey(flight))
			flights.remove(flight);
		else throw new FlightException("Flight not present");
	}

	/**
	 * 
	 * This function check if Flight f is present in DB and if it has free seats to book.
	 * 
	 * @param f
	 * @return
	 * @throws FlightException 
	 */
	private boolean check(Flight f) throws FlightException {

		String query = "SELECT * FROM flight WHERE flight ='" + f.getFlight() + 
				" AND free_seats > 0' AND DATE_ADD(dep_time, INTERVAL 6 HOUR) > CURDATE();";

		try{
			ResultSet rs = stmt.executeQuery(query);

			if(rs.next())
				return true;
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		throw new FlightException("Flight not present");
	}

	public void buy() {
		for(String key : flights.keySet()) {
			try {
				if(check(flights.get(key))) {
					String query = "UPDATE flights SET free_seats = free_seats - 1 WHERE flight='" + flights.get(key).getFlight() + "';";
					stmt.executeUpdate(query);
				}
			} catch (FlightException | SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void buy(Flight f, int seats) {
		try {
			if(check(f)) {
				String query = "UPDATE flights SET free_seats = free_seats - " + seats + " WHERE flight='" + f.getFlight() + "';";
				stmt.executeUpdate(query);
			} 
		}catch (SQLException | FlightException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public TreeMap<String, Flight> getBooking() {
		return flights;
	}

}
