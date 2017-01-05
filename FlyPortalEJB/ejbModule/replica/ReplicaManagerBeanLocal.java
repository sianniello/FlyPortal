package replica;

import java.sql.ResultSet;

import javax.ejb.Local;

import database.Database;
import database.DatabaseException;

@Local
public interface ReplicaManagerBeanLocal {

	void addReplica(Database db) throws DatabaseException;

	void removeReplica(Database db) throws DatabaseException;

	Database getPrimary() throws DatabaseException;

	Database getReplica() throws DatabaseException;

	ResultSet executeQuery(String query) throws DatabaseException;

	boolean executeUpdate(String query) throws DatabaseException;

	void init();

}
