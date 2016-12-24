package login;

import javax.ejb.Local;

import user.User;

@Local
public interface LoginEJBLocal {

	public boolean login(User u);
	
}
