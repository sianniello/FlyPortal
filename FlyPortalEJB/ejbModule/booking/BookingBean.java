package booking;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import replica.ReplicaManagerBeanRemote;
import order.*;
import user.*;
import database.DatabaseException;
import flight.Flight;
import flight.FlightException;

/**
 * Session Bean implementation class FlightsBean
 */
@Stateless
@LocalBean
@TransactionManagement(TransactionManagementType.BEAN)
public class BookingBean implements BookingBeanRemote {

	@EJB
	private ReplicaManagerBeanRemote rm;

	@Resource
	private UserTransaction userTransaction;

	@Override
	public boolean addBooking(Order order) throws NamingException, IllegalStateException, SecurityException, SystemException  {
		try {
			userTransaction.begin();
			Double total = 0.0;
			int lid = lastOrdersId();
			if(lid >= 0) {
				lid++;
				for(String flight : order.getCart().keySet()) {
					Flight x = check(flight, order.getCart().get(flight));
					insertOrder(order, lid, x);
					updateSeats(order, x.getFlight());
					total += total + (order.getCart().get(flight) * x.getPrice());
				}
				insertTransaction(lid, total);
				updateAccount(order.getUser(), total);
				userTransaction.commit();
				return true;
			}
			userTransaction.rollback();
			return false;
		} catch(Exception ecc)
		{
			userTransaction.rollback();
		}
		userTransaction.rollback();
		return false;
	}

	private void updateAccount(String user, Double total) throws SQLException, UserException, DatabaseException {
		String query = "UPDATE users SET account=account-" + total + " WHERE username='"+ user +"';";
		if(!rm.executeUpdate(query)) 
			throw new UserException("User's account is empty");
	}


	private void insertOrder(Order order, int lid, Flight x) throws Exception {
		String query = "INSERT INTO orders (orderID, user, flight, quantity, price) "
				+ "VALUES ('" + (lid) + "', '" + order.getUser() + "', '" + x.getFlight() + "', "
				+ "'" + order.getCart().get(x.getFlight()) + "', '" + x.getPrice() * order.getCart().get(x.getFlight()) + "')";
		if(!rm.executeUpdate(query)) 
			throw new Exception("Generic exception");
	}

	private void updateSeats(Order order, String f) throws SQLException, FlightException, DatabaseException {
		String query = "UPDATE flights SET free_seats=free_seats - "+ order.getCart().get(f) + " WHERE flight='" + f + "';";
		if(!rm.executeUpdate(query)) 
			throw new FlightException("No free seats");
	}

	private void insertTransaction(int lid, double total) throws Exception {
		String query = "INSERT INTO transactions (order_id, amount, status) VALUES ('"
				+ lid + "', '" + total + "', 'confirmed');";
		if(!rm.executeUpdate(query)) 
			throw new Exception("Generic exception");
	}

	/**
	 * 
	 * This function check if Flight f is present in DB and if it has free seats to book.
	 * 
	 * @param flight
	 * @return
	 * @throws FlightException 
	 * @throws SQLException 
	 * @throws DatabaseException 
	 */
	private Flight check(String flight, int qty) throws FlightException, DatabaseException, SQLException {
		String query = "SELECT * FROM flights WHERE flight ='" + flight + 
				"' AND free_seats >= '" + qty + "' AND DATE_ADD(dep_time, INTERVAL 6 HOUR) > CURDATE();";
		ResultSet rs = rm.executeQuery(query);
		if(rs.next())
			return new Flight(rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getInt(8), rs.getDouble(9));
		else throw new FlightException("Flight not present");
	}

	private int lastOrdersId() throws DatabaseException, SQLException {
		String query = "SELECT MAX(orderID) FROM orders";
		ResultSet rs = rm.executeQuery(query);
		if(rs.next())
			return rs.getInt(1);
		else throw new DatabaseException();
	}

}
