package routing;

import java.util.Set;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import database.Database;

/**
 * Session Bean implementation class RoutingBean
 */
@Stateless
@LocalBean
public class RoutingBean implements RoutingBeanRemote {

    /**
     * Default constructor. 
     */
    public RoutingBean() {
        // TODO Auto-generated constructor stub
    }
    
    public static Database Geo(Set<Database> dbSet) {
    	Database db = null;
    	return db;
    }
    
    public static Database Parity(Set<Database> dbSet) {
    	Database db = null;
    	return db;
    }

}
