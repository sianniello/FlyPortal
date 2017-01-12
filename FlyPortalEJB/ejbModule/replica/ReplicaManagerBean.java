package replica;

import java.net.InetSocketAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.LocalBean;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import com.sun.rowset.CachedRowSetImpl;

import database.*;
import routing.*;

/**
 * Session Bean implementation class ReplicaManagerBean
 */
@Singleton
@Startup
@LocalBean
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
public class ReplicaManagerBean implements ReplicaManagerBeanRemote {

	Database primary ;
	ArrayList<Database> rl;

	@PostConstruct
	void start() {
		primary = new Database(new InetSocketAddress("127.0.0.1", 3306), "fly_portal", "admin", "password");
		rl = new ArrayList<Database>();
		rl.add(new Database(new InetSocketAddress("127.0.0.1", 3306), "fly_portal_backup", "admin", "password"));
		//rl.add(new Database(new InetSocketAddress("sql7.freemysqlhosting.net", 3306), "sql7152971", " sql7152971", "FNars4R9cQ"));
	};
	
	@Override
	public Database getPrimary() throws DatabaseException {
		String url = "jdbc:mysql://" + primary.getIsa().getHostString() + ":" + primary.getIsa().getPort() + "/";
		String db = primary.getName();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			DriverManager.getConnection(url+db+"?autoReconnect=true&useSSL=false", primary.getUsername(), primary.getPassword());
			return primary;
		} catch (ClassNotFoundException | SQLException e) {
			if(!rl.isEmpty()) {
				primary = rl.get(0);
				removeReplica(rl.get(0));
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
				DriverManager.getConnection(url+db+"?autoReconnect=true&useSSL=false", d.getUsername(), d.getPassword());
				return d;
			} catch (ClassNotFoundException | SQLException e) {
				removeReplica(d);
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
			Connection con = DriverManager.getConnection(url+dbName+"?autoReconnect=true&useSSL=false", primary.getUsername(), primary.getPassword());
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
					Connection con = DriverManager.getConnection(url+dbName+"?autoReconnect=true&useSSL=false", db.getUsername(), db.getPassword());
					Statement stmt = con.createStatement();
					if(stmt.executeUpdate(query) != affectedRows)
						throw new DatabaseException("Inconsistence on replica!");
					con.close();
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
					removeReplica(db);
					if(rl.isEmpty())
						throw new DatabaseException("No more replica.");
				}
			}
		return res;
	}

	@Override
	public CachedRowSetImpl executeQuery(String query) throws DatabaseException, SQLException {

		Database db = primary;

		String url = "jdbc:mysql://" + db.getIsa().getHostString() + ":" + db.getIsa().getPort() + "/";
		String dbName = db.getName();
		CachedRowSetImpl crs = new CachedRowSetImpl();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(url+dbName+"?autoReconnect=true&useSSL=false", db.getUsername(), db.getPassword());
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			crs.populate(rs);
			return crs;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			primary = getPrimary();
			executeQuery(query);
		}
		return null;
	}

	@Override
	public CachedRowSetImpl executeQuery(String query, String param) throws DatabaseException, SQLException {

		Set<Database> dbSet = new HashSet<>();
		dbSet.add(primary);
		dbSet.addAll(rl);
		Database db = Routing.Parity(dbSet, param);

		String url = "jdbc:mysql://" + db.getIsa().getHostString() + ":" + db.getIsa().getPort() + "/";
		String dbName = db.getName();
		CachedRowSetImpl crs = new CachedRowSetImpl();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(url+dbName+"?autoReconnect=true&useSSL=false", db.getUsername(), db.getPassword());
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			crs.populate(rs);
			return crs;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			primary = getPrimary();
			executeQuery(query);
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