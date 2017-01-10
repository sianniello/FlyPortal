package login;

import java.sql.ResultSet;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import replica.ReplicaManagerBean;
import replica.ReplicaManagerBeanRemote;
import user.User;

//import user.User;


/**
 * Session Bean implementation class LoginEJB
 */
@Stateless
@LocalBean
public class LoginBean implements LoginBeanRemote {

	@EJB
	ReplicaManagerBeanRemote rm;

	/**
	 * Default constructor. 
	 */
	public LoginBean() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean login(User u, String s) {
		rm = new ReplicaManagerBean();
		String query = "SELECT * FROM " + (s.equals("user")? "users" : "db_managers") + " WHERE username ='" + u.getUsername() + "' AND password='" + u.getPassword() + "';";
		try{
			ResultSet rs = rm.executeQuery(query);
			if(rs.next())
				return true;
		}
		catch(Exception e){
			return false;
		}
		return false;
	}

}
