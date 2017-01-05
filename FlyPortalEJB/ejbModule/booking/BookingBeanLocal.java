package booking;

import javax.naming.NamingException;
import javax.transaction.SystemException;

import order.Order;
import user.UserException;

public interface BookingBeanLocal {

	boolean addBooking(Order order) throws UserException, IllegalStateException, SecurityException, SystemException, NamingException;

	void init();
	
}