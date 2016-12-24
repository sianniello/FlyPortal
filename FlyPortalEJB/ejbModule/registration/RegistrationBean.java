package registration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import user.User;

/**
 * Session Bean implementation class RegistrationBean
 */
@Stateless
@LocalBean
public class RegistrationBean implements RegistrationBeanLocal {

    /**
     * Default constructor. 
     */
    public RegistrationBean() {
        // TODO Auto-generated constructor stub
    }

	@Override
	public boolean register(User u) {
		Connection con = null;
		String url = "jdbc:mysql://localhost:3306/";;
		String db = "fly_portal";
		String query = "INSERT INTO users (username, password) VALUES ('" + u.getUsername() + "', '" + u.getPassword() + "');";

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
