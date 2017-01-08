package login;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.ejb.Remote;
import javax.ejb.Stateless;

import user.User;

//import user.User;


/**
 * Session Bean implementation class LoginEJB
 */
@Stateless
@Remote
public class LoginBean implements LoginBeanLocal {


	/**
	 * Default constructor. 
	 */
	public LoginBean() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean login(User u, String s) {

		Connection con = null;
		ResultSet rs = null;
		String url = "jdbc:mysql://localhost:3306/";;
		String db = "fly_portal";
		String query = "SELECT * FROM " + (s.equals("user")? "users" : "db_managers") + " WHERE username ='" + u.getUsername() + "' AND password='" + u.getPassword() + "';";
		try{
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(url+db,"admin","password");
			Statement stmt = con.createStatement();
			rs = stmt.executeQuery(query);

			if(rs.next())
				return true;
		}
		catch(Exception e){
			return false;
		}
		return false;
	}

}
