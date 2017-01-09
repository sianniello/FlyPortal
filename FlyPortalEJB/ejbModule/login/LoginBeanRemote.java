package login;

import javax.ejb.Remote;

import user.User;

@Remote
public interface LoginBeanRemote {

	public boolean login(User u, String s);
	
}
