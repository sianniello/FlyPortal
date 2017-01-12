package booking;

import java.io.IOException;
import java.util.TreeMap;

import javax.ejb.EJB;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.SystemException;

import order.*;
import user.UserException;

/**
 * Servlet implementation class FlightServlet
 */
@WebServlet("/BookingServlet")
public class BookingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@EJB
	private BookingBeanRemote bookingBean;

	/**
	 * @throws IOException 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String username = (String) request.getSession().getAttribute("username");
		@SuppressWarnings("unchecked")
		TreeMap<String, Integer> cart = (TreeMap<String, Integer>) request.getSession().getAttribute("cart");
		Order order = new Order(username, cart);
		try {
			if(bookingBean.addBooking(order)) {
				request.getSession().setAttribute("cart", null);
				response.getWriter().write("Congratulations your order has been confirmed!");
			}
			else response.getWriter().write("Your order has not been confirmed.");
		} catch (IllegalStateException | SecurityException | UserException
				| SystemException | NamingException | IOException e) {
			response.getWriter().write("Your order has not been confirmed.");
			e.printStackTrace();
		}
	}

}
