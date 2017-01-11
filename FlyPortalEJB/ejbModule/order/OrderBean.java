package order;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import database.DatabaseException;
import replica.ReplicaManagerBean;
import replica.ReplicaManagerBeanRemote;

/**
 * Session Bean implementation class OrderBean
 */
@Stateless
@LocalBean
public class OrderBean implements OrderBeanRemote {

	@EJB
	ReplicaManagerBeanRemote rm;


	@Override
	public LinkedList<Order> getOrder(int id) throws OrderException {
		LinkedList<Order> llo = new LinkedList<>();
		String query = "SELECT * FROM orders WHERE orderID=" + id + ";";
		ResultSet rs;
		try {
			rs = rm.executeQuery(query);
			while(rs.next())
				llo.add(new Order(rs.getString("user"), rs.getString("flight"), rs.getInt("quantity"), 
						rs.getDouble("price"), rs.getString("timestamp")));
		} catch (DatabaseException | SQLException e) {
			e.printStackTrace();
			throw new OrderException("No order found");
		}
		return llo;
	}

}
