package booking;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;

import database.DatabaseException;
import replica.ReplicaManagerBean;
import replica.ReplicaManagerBeanLocal;
import flight.Flight;
import flight.FlightException;
import order.*;

/**
 * Session Bean implementation class FlightsBean
 */
@Stateful
@LocalBean
public class BookingBean implements BookingBeanLocal {

	private ReplicaManagerBeanLocal rm;

	/**
	 * Default constructor. 
	 */
	public BookingBean() {
	}

	@Override
	public boolean addBooking(Order order) {
		String query;
		Double total = 0.0;
		int lid = lastOrdersId();
		if(lid >= 0) {
			lid++;
			rm = new ReplicaManagerBean();
			rm.init();
			for(String flight : order.getCart().keySet())
				try {
					Flight x = check(flight, order.getCart().get(flight));
					if(x != null) {
						query = "INSERT INTO orders (orderID, user, flight, quantity, price) "
								+ "VALUES ('" + (lid) + "', '" + order.getUser() + "', '" + x.getFlight() + "', "
								+ "'" + order.getCart().get(flight) + "', '" + x.getPrice() * order.getCart().get(flight) + "')";
						rm.executeUpdate(query);

						query = "UPDATE flights SET free_seats=free_seats - "+ order.getCart().get(flight) + " WHERE flight='" + flight + "';";
						rm.executeUpdate(query);
						total += total + (order.getCart().get(flight) * x.getPrice());
					}
					else throw new OrderException("Order not processable");
				} catch (FlightException | DatabaseException | OrderException e) {
					e.printStackTrace();
					return false;
				}
			query = "INSERT INTO transactions (order_id, amount, status) VALUES ('"
					+ lid + "', '" + total + "', 'confirmed');";
			try {
				rm.executeUpdate(query);
			} catch (DatabaseException e) {
				e.printStackTrace();
				return false;
			}
			return true;
		}
		return false;
	}

	/**
	 * 
	 * This function check if Flight f is present in DB and if it has free seats to book.
	 * 
	 * @param flight
	 * @return
	 * @throws FlightException 
	 */
	private Flight check(String flight, int qty) throws FlightException {

		String query = "SELECT * FROM flights WHERE flight ='" + flight + 
				"' AND free_seats >= '" + qty + "' AND DATE_ADD(dep_time, INTERVAL 6 HOUR) > CURDATE();";
		rm = new ReplicaManagerBean();
		rm.init();
		try{
			ResultSet rs = rm.executeQuery(query);

			if(rs.next())
				return new Flight(rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getInt(8), rs.getDouble(9));
			else return null;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		throw new FlightException("Flight not present");
	}

	private int lastOrdersId() {
		String query = "SELECT MAX(orderID) FROM orders";
		rm = new ReplicaManagerBean();
		rm.init();
		try {
			ResultSet rs = rm.executeQuery(query);
			if(rs.next())
				return rs.getInt(1);
		} catch (DatabaseException | SQLException e) {
			e.printStackTrace();
			return -1;
		}
		return -1;
	}

}
