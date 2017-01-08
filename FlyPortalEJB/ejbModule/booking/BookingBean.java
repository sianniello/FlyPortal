package booking;

import java.net.InetSocketAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

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
import javax.websocket.Session;

import replica.ReplicaManagerBean;
import replica.ReplicaManagerBeanLocal;
import order.*;
import user.*;
import database.Database;
import database.DatabaseException;
import flight.Flight;
import flight.FlightException;

/**
 * Session Bean implementation class FlightsBean
 */
@Stateless
@LocalBean
@TransactionManagement(TransactionManagementType.BEAN)
public class BookingBean implements BookingBeanLocal {

	@EJB
	private ReplicaManagerBeanLocal rm;

	private LinkedList<Database> res;

	@Resource
	private SessionContext sessioncontext;

	/**
	 * Default constructor. 
	 */
	public BookingBean() {
	}

	@Override
	public void init() {
		res = new LinkedList<>();
		res.add(new Database(new InetSocketAddress("127.0.0.1", 3306), "fly_portal"));
		res.add(new Database(new InetSocketAddress("127.0.0.1", 3306), "fly_portal_backup"));
	}

	@Override
	public boolean addBooking(Order order) throws NamingException, IllegalStateException, SecurityException, SystemException  {
		Context context = new InitialContext();
		UserTransaction userTransaction =
				(UserTransaction) context.lookup("java:comp/UserTransaction");
		try {
			userTransaction.begin();
			Double total = 0.0;
			int lid = lastOrdersId();
			if(lid >= 0) {
				lid++;
				for(String flight : order.getCart().keySet()) {
					Flight x = check(flight, order.getCart().get(flight));
					if(x != null) {
						insertOrder(order, lid, x);
						updateSeats(order, x.getFlight());
						total += total + (order.getCart().get(flight) * x.getPrice());
					}
					else {
						userTransaction.rollback();
						throw new FlightException("Flight not avilable");
					}
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

	private void updateAccount(String user, Double total) throws SQLException, UserException {
		for(Database db : res) {
			String url = "jdbc:mysql://" + db.getIsa().getHostString() + ":" + db.getIsa().getPort() + "/";
			String dbName = db.getName();
			String query = "UPDATE users SET account=account-" + total + " WHERE username='"+ user +"';";
			Connection con = DriverManager.getConnection(url+dbName+"?autoReconnect=true&useSSL=false","admin","password");
			Statement stmt = con.createStatement();
			if(stmt.executeUpdate(query) == 0) 
				throw new UserException("User's account is empty");
			con.close();
		}
	}

	private void insertOrder(Order order, int lid, Flight x) throws Exception {
		for(Database db : res) {
			String url = "jdbc:mysql://" + db.getIsa().getHostString() + ":" + db.getIsa().getPort() + "/";
			String dbName = db.getName();
			String query = "INSERT INTO orders (orderID, user, flight, quantity, price) "
					+ "VALUES ('" + (lid) + "', '" + order.getUser() + "', '" + x.getFlight() + "', "
					+ "'" + order.getCart().get(x.getFlight()) + "', '" + x.getPrice() * order.getCart().get(x.getFlight()) + "')";

			Connection con = DriverManager.getConnection(url+dbName+"?autoReconnect=true&useSSL=false","admin","password");
			Statement stmt = con.createStatement();
			if(stmt.executeUpdate(query) == 0) 
				throw new Exception("Generic exception");
			con.close();
		}
	}

	private void updateSeats(Order order, String f) throws SQLException, FlightException {
		for(Database db : res) {
			String url = "jdbc:mysql://" + db.getIsa().getHostString() + ":" + db.getIsa().getPort() + "/";
			String dbName = db.getName();
			String query = "UPDATE flights SET free_seats=free_seats - "+ order.getCart().get(f) + " WHERE flight='" + f + "';";

			Connection con = DriverManager.getConnection(url+dbName+"?autoReconnect=true&useSSL=false","admin","password");
			Statement stmt = con.createStatement();
			if(stmt.executeUpdate(query) == 0) 
				throw new FlightException("No free seats");
			con.close();
		}
	}

	private void insertTransaction(int lid, double total) throws Exception {
		for(Database db : res) {
			String url = "jdbc:mysql://" + db.getIsa().getHostString() + ":" + db.getIsa().getPort() + "/";
			String dbName = db.getName();
			String query = "INSERT INTO transactions (order_id, amount, status) VALUES ('"
					+ lid + "', '" + total + "', 'confirmed');";

			Connection con = DriverManager.getConnection(url+dbName+"?autoReconnect=true&useSSL=false","admin","password");
			Statement stmt = con.createStatement();
			if(stmt.executeUpdate(query) == 0) 
				throw new Exception("Generic exception");
			con.close();
		}
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
