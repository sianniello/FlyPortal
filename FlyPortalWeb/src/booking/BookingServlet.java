package booking;

import java.io.IOException;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import order.*;
import user.User;

/**
 * Servlet implementation class FlightServlet
 */
@WebServlet("/BookingServlet")
public class BookingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private BookingBeanLocal bookingBean;

	@Override
	public void init() throws ServletException {
		bookingBean = new BookingBean();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = (String) request.getSession().getAttribute("username");
		TreeMap<String, Integer> cart = (TreeMap<String, Integer>) request.getSession().getAttribute("cart");

		Order order = new Order(username, cart);

		if(bookingBean.addBooking(order)) {
			request.getSession().setAttribute("cart", null);
				response.getWriter().write("Congratulations your order " + order.toString() + " has been confirmed!");
				request.getRequestDispatcher("flights/flights.jsp").forward(request, response);
		}
		else
			response.getWriter().write("Your order has not been confirmed.");
	}

}
