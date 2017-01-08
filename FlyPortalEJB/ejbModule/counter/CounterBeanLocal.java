package counter;

import java.net.InetAddress;
import java.util.TreeMap;

import javax.ejb.Local;

@Local
public interface CounterBeanLocal {

	void setCounter();

	int getCounter();

	void setCounter(InetAddress ia);

	TreeMap<String, String> getVisitorsSet();

}
