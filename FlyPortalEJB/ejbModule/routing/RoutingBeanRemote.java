package routing;

import java.util.Set;

import javax.ejb.Remote;

import database.Database;

@Remote
public interface RoutingBeanRemote {

	Database Parity(Set<Database> dbSet);

	Database Geo(Set<Database> dbSet);

}
