package flights;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import flight.FlightException;

/**
 * Session Bean implementation class DeleteFlightBean
 */
@Stateless
@LocalBean
public class DeleteFlightBean implements DeleteFlightBeanLocal {

	/**
	 * Default constructor. 
	 */
	public DeleteFlightBean() {
	}

	@Override
	public boolean deleteFlight(String f) throws FlightException {
		Connection con = null;
		String url = "jdbc:mysql://localhost:3306/";;
		String db = "fly_portal";
		String query = "DELETE FROM flights WHERE flight='" + f + "';";

		try{
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(url+db,"admin","password");
			Statement stmt = con.createStatement();
			if(stmt.executeUpdate(query) == 1)
				return true;
		}
		catch(Exception e){
			System.out.println(e.getMessage());
			throw new FlightException("Flight not present");
		}
		throw new FlightException("Flight not present");
	}

}
