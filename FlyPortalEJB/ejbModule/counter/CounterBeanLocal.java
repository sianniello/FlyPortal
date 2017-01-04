package counter;

import java.net.InetAddress;

import javax.ejb.Local;

@Local
public interface CounterBeanLocal {

	void setCounter();

	int getCounter();

	void setCounter(InetAddress ia);

}
