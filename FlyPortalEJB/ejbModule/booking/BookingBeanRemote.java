package booking;

import javax.ejb.Remote;
import javax.naming.NamingException;
import javax.transaction.SystemException;

import order.Order;
import user.UserException;

@Remote
public interface BookingBeanRemote {

	boolean addBooking(Order order) throws UserException, IllegalStateException, SecurityException, SystemException, NamingException;

	void init();
	
}