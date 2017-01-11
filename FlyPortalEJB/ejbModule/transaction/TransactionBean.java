package transaction;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import database.DatabaseException;
import replica.ReplicaManagerBeanRemote;

/**
 * Session Bean implementation class TransactionBean
 */
@Stateless
@LocalBean
public class TransactionBean implements TransactionBeanRemote {

	@EJB
	private ReplicaManagerBeanRemote rm;

	@Override
	public LinkedList<Transaction> getTransactions() {
		LinkedList<Transaction> llt = new LinkedList<Transaction>();
		String query = "SELECT transactions.*, orders.user, orders.timestamp "
				+ "FROM transactions join orders on transactions.order_id = orders.orderID GROUP BY orders.orderID;";
		try {
			ResultSet rs = rm.executeQuery(query);
			while(rs.next())
				llt.add(new Transaction(rs.getInt("order_id"), rs.getString("user"), 
						rs.getString("status"), rs.getDouble("amount"), rs.getString("timestamp")));
		} catch (DatabaseException | SQLException e) {
			e.printStackTrace();
			return null;
		}
		return llt;
	}

}
