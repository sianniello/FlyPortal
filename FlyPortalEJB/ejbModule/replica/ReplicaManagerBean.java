package replica;

import java.net.InetSocketAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import javax.ejb.LocalBean;
import javax.ejb.Singleton;

import database.*;

/**
 * Session Bean implementation class ReplicaManagerBean
 */
@Singleton
@LocalBean
public class ReplicaManagerBean implements ReplicaManagerBeanRemote {

	LinkedList<Database> replicaList;
	Database primary = new Database(new InetSocketAddress("127.0.0.1", 3306), "fly_portal");
	Database backup1 = new Database(new InetSocketAddress("127.0.0.1", 3306), "fly_portal_backup");
	/**
	 * Default constructor. 
	 */
	public ReplicaManagerBean() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init() {
	}

	@Override
	public Database getPrimary() throws DatabaseException {
		replicaList = new LinkedList<Database>();
		replicaList.add(backup1);
		String url = "jdbc:mysql://" + primary.getIsa().getHostString() + ":" + primary.getIsa().getPort() + "/";
		String db = primary.getName();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			DriverManager.getConnection(url+db+"?autoReconnect=true&useSSL=false","admin","password");
			return primary;
		} catch (ClassNotFoundException | SQLException e) {
			if(!replicaList.isEmpty()) {
				primary = replicaList.getFirst();
				return getPrimary();
			}
			else throw new DatabaseException("Replica fail, system crashed");	//No other replica in list
		}
	}

	@Override
	public Database getReplica() throws DatabaseException {
		replicaList = new LinkedList<Database>();
		replicaList.add(backup1);
		if(!replicaList.isEmpty()) 
			for(Database d : replicaList) {
				String url = "jdbc:mysql://" + d.getIsa().getHostString() + ":" + d.getIsa().getPort() + "/";
				String db = d.getName();
				try {
					Class.forName("com.mysql.jdbc.Driver");
					DriverManager.getConnection(url+db+"?autoReconnect=true&useSSL=false","admin","password");
					return d;
				} catch (ClassNotFoundException | SQLException e) {
					replicaList.remove(d);
					return getReplica();
				}
			}
		else throw new DatabaseException("Replica fail, system crashed");
		return null;
	}

	@Override
	public boolean executeUpdate(String query) throws DatabaseException {
		boolean res = false;
		int affectedRows = 0;
		String url = "jdbc:mysql://" + primary.getIsa().getHostString() + ":" + primary.getIsa().getPort() + "/";
		String dbName = primary.getName();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(url+dbName+"?autoReconnect=true&useSSL=false","admin","password");
			Statement stmt = con.createStatement();
			affectedRows = stmt.executeUpdate(query);
			if(affectedRows > 0) {
				res = true;
			}
			con.close();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			primary = getPrimary();
			executeUpdate(query);
		}

		for(Database db : replicaList) {
			url = "jdbc:mysql://" + db.getIsa().getHostString() + ":" + db.getIsa().getPort() + "/";
			dbName = db.getName();
			try {
				Class.forName("com.mysql.jdbc.Driver");
				Connection con = DriverManager.getConnection(url+dbName+"?autoReconnect=true&useSSL=false","admin","password");
				Statement stmt = con.createStatement();
				if(stmt.executeUpdate(query) != affectedRows)
					throw new DatabaseException("Inconsistence on replica!");
				con.close();
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
				replicaList.remove(db);
				if(replicaList.isEmpty())
					throw new DatabaseException("No more replica.");
			}
		}
		return res;
	}

	@Override
	public ResultSet executeQuery(String query) {

		//here is possible implement a traffic balancer
		Database db = primary;

		String url = "jdbc:mysql://" + db.getIsa().getHostString() + ":" + db.getIsa().getPort() + "/";
		String dbName = db.getName();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(url+dbName+"?autoReconnect=true&useSSL=false","admin","password");
			Statement stmt = con.createStatement();
			return stmt.executeQuery(query);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			try {
				getPrimary();
			} catch (DatabaseException e1) {
				e1.printStackTrace();
			}
			executeQuery(query);
		}
		return null;
	}

	@Override
	public void addReplica(Database db) throws DatabaseException {
		replicaList.add(db);
	}

	@Override
	public void removeReplica(Database db) throws DatabaseException {
		replicaList.remove(db);
	}
}