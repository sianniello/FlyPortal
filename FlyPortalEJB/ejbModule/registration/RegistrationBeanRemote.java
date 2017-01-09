package registration;

import javax.ejb.Remote;

import user.User;

@Remote
public interface RegistrationBeanRemote {

	public boolean register(User u);
	
}
