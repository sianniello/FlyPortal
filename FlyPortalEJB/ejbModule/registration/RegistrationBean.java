package registration;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import replica.ReplicaManagerBean;
import replica.ReplicaManagerBeanLocal;
import user.User;

/**
 * Session Bean implementation class RegistrationBean
 */
@Stateless
@LocalBean
public class RegistrationBean implements RegistrationBeanLocal {

	ReplicaManagerBeanLocal rm;

	/**
	 * Default constructor. 
	 */
	public RegistrationBean() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean register(User u) {
		rm = new ReplicaManagerBean();
		rm.init();
		String query = "INSERT INTO users (username, password) VALUES ('" + u.getUsername() + "', '" + u.getPassword() + "');";

		try{
			if(rm.executeUpdate(query))
				return true;
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return false;
	}

}
