package order;

import java.util.LinkedList;

import javax.ejb.Local;

@Local
public interface OrderBeanLocal {

	LinkedList<Order> getOrder(int id) throws OrderException;

}
