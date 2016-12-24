package registration;

import javax.ejb.Local;

import user.User;

@Local
public interface RegistrationBeanLocal {

	public boolean register(User u);
	
}
