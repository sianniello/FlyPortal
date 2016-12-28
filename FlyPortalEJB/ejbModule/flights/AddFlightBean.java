package flights;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import flight.Flight;

/**
 * Session Bean implementation class AddFlightBean
 */
@Stateless
@LocalBean
public class AddFlightBean {

    /**
     * Default constructor. 
     */
    public AddFlightBean() {
        // TODO Auto-generated constructor stub
    }
    
    public boolean addFlight(Flight f) {
		Connection con = null;
		String url = "jdbc:mysql://localhost:3306/";;
		String db = "fly_portal";
		String query = "INSERT INTO flights (flight, dep_airport, arr_airport, dep_time, company, state, free_seats) "
				+ "VALUES ('" + f.getFlight() + "', '" + f.getDepAirport() + "', '" + f.getArrAirport() + "', '" + 
				f.getDepTime() + "', '" + f.getCompany() + "', '" + f.getState() + "', '" + f.getFreeSeats() + "');";

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
