package replica;

import java.net.InetSocketAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;

import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.LocalBean;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;

import routing.RoutingBeanRemote;
import database.*;

/**
 * Session Bean implementation class ReplicaManagerBean
 */
@Singleton
@LocalBean
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
public class ReplicaManagerBean implements ReplicaManagerBeanRemote {

	RoutingBeanRemote rb;

	Database primary = new Database(new InetSocketAddress("127.0.0.1", 3306), "fly_portal");

	ArrayList<Database> rl = new ArrayList<Database>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			add(new Database(new InetSocketAddress("127.0.0.1", 3306), "fly_portal_backup"));
		}
	};
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
		String url = "jdbc:mysql://" + primary.getIsa().getHostString() + ":" + primary.getIsa().getPort() + "/";
		String db = primary.getName();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			DriverManager.getConnection(url+db+"?autoReconnect=true&useSSL=false","admin","password");
			return primary;
		} catch (ClassNotFoundException | SQLException e) {
			if(!rl.isEmpty()) {
				primary = rl.get(0);
				rl.remove(0);
				return getPrimary();
			}
			else throw new DatabaseException("Replica fail, system crashed");	//No other replica in list
		}
	}

	@Override
	@Lock(LockType.READ)
	public Database getReplica() throws DatabaseException {
		if(!rl.isEmpty()) {
			Database d = rl.get(0);
			String url = "jdbc:mysql://" + d.getIsa().getHostString() + ":" + d.getIsa().getPort() + "/";
			String db = d.getName();
			try {
				Class.forName("com.mysql.jdbc.Driver");
				DriverManager.getConnection(url+db+"?autoReconnect=true&useSSL=false","admin","password");
				return d;
			} catch (ClassNotFoundException | SQLException e) {
				rl.remove(d);
				return getReplica();
			}
		}
		else throw new DatabaseException("Replica fail, system crashed");
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
			else throw new DatabaseException("No rows updates");
			con.close();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			if(!rl.isEmpty())
				primary = getReplica();
			throw new DatabaseException();
		}
		if(res = true) 
			for(Database db : rl) {
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
					rl.remove(db);
					if(rl.isEmpty())
						throw new DatabaseException("No more replica.");
				}
			}
		return res;
	}

	@Override
	public ResultSet executeQuery(String query) throws DatabaseException {

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
			primary = getPrimary();
			executeQuery(query);
		}
		return null;
	}

	@Override
	public ResultSet executeQuery(String query, String param) throws DatabaseException {

		HashSet<Database> dbSet = new HashSet<>();
		dbSet.add(primary);
		dbSet.addAll(rl);
		Database db = rb.Geo(dbSet);

		String url = "jdbc:mysql://" + db.getIsa().getHostString() + ":" + db.getIsa().getPort() + "/";
		String dbName = db.getName();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(url+dbName+"?autoReconnect=true&useSSL=false","admin","password");
			Statement stmt = con.createStatement();
			return stmt.executeQuery(query);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			primary = getPrimary();
			executeQuery(query, param);
		}
		return null;
	}

	@Override
	@Lock(LockType.WRITE)
	public void addReplica(Database db) throws DatabaseException {
		rl.add(db);
	}

	@Override
	@Lock(LockType.WRITE)
	public void removeReplica(Database db) throws DatabaseException {
		rl.remove(db);
	}
}