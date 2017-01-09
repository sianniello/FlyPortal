package replica;

import java.sql.ResultSet;

import javax.ejb.Remote;

import database.Database;
import database.DatabaseException;

@Remote
public interface ReplicaManagerBeanRemote {

	void addReplica(Database db) throws DatabaseException;

	void removeReplica(Database db) throws DatabaseException;

	Database getPrimary() throws DatabaseException;

	Database getReplica() throws DatabaseException;

	ResultSet executeQuery(String query) throws DatabaseException;

	boolean executeUpdate(String query) throws DatabaseException;

	void init();

	ResultSet executeQuery(String query, String param) throws DatabaseException;

}
