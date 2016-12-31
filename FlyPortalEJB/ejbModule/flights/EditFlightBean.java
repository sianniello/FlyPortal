package flights;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import flight.Flight;

/**
 * Session Bean implementation class EditFlightBean
 */
@Stateless
@LocalBean
public class EditFlightBean implements EditFlightBeanLocal {

	/**
	 * Default constructor. 
	 */
	public EditFlightBean() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean editFlight(Flight f, String prec) {
		Connection con = null;
		String url = "jdbc:mysql://localhost:3306/";
		String db = "fly_portal";
		String query = "UPDATE flights SET flight='" + f.getFlight() + "', dep_airport='" + f.getDepAirport() + "', "
				+ "arr_airport='" + f.getArrAirport() + "', dep_time='" + f.getDepTime() + "', " 
				+ "company='" + f.getCompany() + "', state='" + f.getState() + "', free_seats='" + f.getFreeSeats() + "', "
				+ "seat_price='" + f.getPrice() + "' WHERE flight='" + prec + "';";

		try{
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(url+db,"admin","password");
			Statement stmt = con.createStatement();
			if(stmt.executeUpdate(query) == 1)
				return true;
		}
		catch(Exception e){
			System.out.println(e.getMessage());
			return false;
		}
		return false;
	}
}

