package cart;

import java.io.IOException;
import java.util.LinkedList;
import java.util.TreeMap;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import flight.Flight;
import flight.FlightException;
import flights.FlightBeanRemote;

/**
 * Servlet implementation class CartInfoServlet
 */
@WebServlet("/CartInfo")
public class CartInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	FlightBeanRemote fb;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		TreeMap<String, Integer> cart = (TreeMap<String, Integer>) request.getSession().getAttribute("cart");
		if(cart != null) {
			LinkedList<Flight> cartList = null;
			try {
				cartList = fb.getFlights(cart.keySet());
			} catch (FlightException e) {
				e.printStackTrace();
			}
			request.setAttribute("cartList", cartList);
		}
		request.getRequestDispatcher("cart/cart.jsp").forward(request, response);
	}

}
