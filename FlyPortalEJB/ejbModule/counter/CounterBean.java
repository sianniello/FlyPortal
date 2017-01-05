package counter;

import java.net.InetAddress;
import java.util.HashSet;

import javax.ejb.LocalBean;
import javax.ejb.Singleton;

/**
 * Session Bean implementation class CounterBean
 */
@Singleton
@LocalBean
public class CounterBean implements CounterBeanLocal {

	private int counter = 1;
	private HashSet<InetAddress> visitorsSet = new HashSet<>();

	@Override
	public HashSet<InetAddress> getVisitorsSet() {
		return visitorsSet;
	}

	@Override
	public void setCounter(InetAddress ia) {
		if(!visitorsSet.contains(ia)) {
			visitorsSet.add(ia);
			counter++;
		}
	}

	@Override
	public int getCounter() {
		return counter;
	}

	@Override
	public void setCounter() {
		counter++;
	}
}
