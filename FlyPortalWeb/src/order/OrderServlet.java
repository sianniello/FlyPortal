package order;

import java.io.IOException;
import java.util.LinkedList;

import javax.ejb.EJB;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class OrderServlet
 */
@WebServlet("/OrderServlet")
public class OrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	private OrderBeanRemote ob;

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int orderId = Integer.parseInt(request.getParameter("order"));
		try {
			LinkedList<Order> orderList = ob.getOrder(orderId);
			String orders = "";
			for(Order o : orderList)
				orders += o.getUser()+"#"+o.getFlight()+"#"+o.getQuantity()+"#"+o.getPrice()+"#"+o.getTimestamp()+"!"; 
			response.getWriter().write(orders);
		} catch (OrderException e) {
			e.printStackTrace();
			response.getWriter().write("Order error!");
		}
	}

}
