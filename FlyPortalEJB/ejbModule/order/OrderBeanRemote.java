package order;

import java.util.LinkedList;

import javax.ejb.Remote;

@Remote
public interface OrderBeanRemote {

	LinkedList<Order> getOrder(int id) throws OrderException;

}
