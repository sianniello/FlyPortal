package registration;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import replica.ReplicaManagerBeanRemote;
import user.User;

/**
 * Session Bean implementation class RegistrationBean
 */
@Stateless
@LocalBean
public class RegistrationBean implements RegistrationBeanRemote {

	@EJB
	ReplicaManagerBeanRemote rm;

	@Override
	public boolean register(User u) {
		String query = "INSERT INTO users (username, password, account) VALUES "
				+ "('" + u.getUsername() + "', '" + u.getPassword() + "', 10000);";
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
