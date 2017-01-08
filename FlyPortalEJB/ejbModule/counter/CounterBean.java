package counter;

import java.net.InetAddress;
import java.sql.Timestamp;
import java.util.TreeMap;

import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;

/**
 * Session Bean implementation class CounterBean
 */
@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
public class CounterBean implements CounterBeanLocal {

	private int counter = 1;
	private TreeMap<String, String> visitorsSet = new TreeMap<>();

	@Override
	public TreeMap<String, String> getVisitorsSet() {
		return visitorsSet;
	}

	@Lock(LockType.WRITE)
	@Override
	public void setCounter(InetAddress ia) {
		if(!visitorsSet.containsKey(ia.toString())) {
			visitorsSet.put(ia.toString(), new Timestamp(System.currentTimeMillis()).toString());
			counter++;
		}
		else
			visitorsSet.replace(ia.toString(), new Timestamp(System.currentTimeMillis()).toString());
	}

	@Lock(LockType.READ)
	@Override
	public int getCounter() {
		return counter;
	}

	@Lock(LockType.WRITE)
	@Override
	public void setCounter() {
		counter++;
	}
}
