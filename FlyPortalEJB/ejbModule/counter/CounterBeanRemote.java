package counter;

import java.net.InetAddress;
import java.util.TreeMap;

import javax.ejb.Remote;

@Remote
public interface CounterBeanRemote {

	void setCounter();

	int getCounter();

	void setCounter(InetAddress ia);

	TreeMap<String, String> getVisitorsSet();

}
