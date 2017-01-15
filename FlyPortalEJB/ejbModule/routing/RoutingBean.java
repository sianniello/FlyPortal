package routing;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import database.Database;
import database.DatabaseException;

/**
 * Session Bean implementation class RoutingBean
 */
@Stateless
@LocalBean
public class RoutingBean {

	private static final int PARITY = 0;
	private static final int GEO = 1;

	private static final String ITALY = "it";

	public static Database getDatabase(Set<Database> dbSet, String param, int algorithm) throws DatabaseException {
		List<Database> list = new ArrayList<>(dbSet);
		if(!list.isEmpty()) {
			int size = list.size();
			switch (algorithm) {
			case PARITY:
				String n[] = param.split(".");
				if(Integer.parseInt(n[3]) >= 0 && Integer.parseInt(n[3])%2 == 0)
					return list.get(Integer.parseInt(n[3])%size);
				else return list.get(0);

			case GEO:
				switch(param) {
				case ITALY : return list.get(0);
				default : return list.get(1);
				}

			default:
				return list.get(0);
			}
		}
		else throw new DatabaseException("No active database");
	}

}
