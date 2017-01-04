package replica;

import java.net.InetSocketAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import database.*;

/**
 * Session Bean implementation class ReplicaManagerBean
 */
@Stateless
@LocalBean
public class ReplicaManagerBean implements ReplicaManagerBeanLocal {

	Database primary;
	LinkedList<Database> replicaList;

	/**
	 * Default constructor. 
	 */
	public ReplicaManagerBean() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init() {
		replicaList = new LinkedList<>();
		primary = new Database(new InetSocketAddress("127.0.0.1", 3306), "fly_portal", true);
		replicaList.add(new Database(new InetSocketAddress("127.0.0.1", 3306), "fly_portal_backup", false));
	}

	@Override
	public void addReplica(Database db) throws DatabaseException {
		db.setPrimary(false);
		if(!replicaList.contains(db)) {
			synchronized (this) {
				replicaList.add(db);
			}
		} else throw new DatabaseException("Database already running");
	}

	@Override
	public void removeReplica(Database db) throws DatabaseException {
		if(replicaList.contains(db)) {
			if(replicaList.get(replicaList.indexOf(db)).isPrimary()) {
				synchronized (this) {
					replicaList.remove(db);
				}
				if(!replicaList.isEmpty())
					replicaList.getFirst().setPrimary(true);
				else throw new DatabaseException("Replica fail, system crashed!");
			} else {
				synchronized (this) {
					replicaList.remove(db);
				}
			}
			if(replicaList.isEmpty()) throw new DatabaseException("Replica fail, system crashed!");
		}
		else throw new DatabaseException("No such database");
	}

	@Override
	public Database getPrimary() throws DatabaseException {
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
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			//primary = getPrimary();
			//executeUpdate(query);
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
}