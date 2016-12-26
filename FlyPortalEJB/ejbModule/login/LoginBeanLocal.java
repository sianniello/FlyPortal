package login;

import javax.ejb.Local;

import user.User;

@Local
public interface LoginBeanLocal {

	public boolean login(User u);
	
}
