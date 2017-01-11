package replica;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.ejb.Remote;

import com.sun.rowset.CachedRowSetImpl;
import database.Database;
import database.DatabaseException;

@Remote
public interface ReplicaManagerBeanRemote {

	void addReplica(Database db) throws DatabaseException;

	void removeReplica(Database db) throws DatabaseException;

	Database getPrimary() throws DatabaseException;

	Database getReplica() throws DatabaseException;

	CachedRowSetImpl executeQuery(String query) throws DatabaseException, SQLException;

	boolean executeUpdate(String query) throws DatabaseException;

	ResultSet executeQuery(String query, String param) throws DatabaseException, SQLException;

}
