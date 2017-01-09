package flights;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import flight.Flight;

/**
 * Servlet implementation class AddFlightServlet
 */
@WebServlet("/AddFlightServlet")
public class AddFlightServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@EJB
	private AddFlightBeanRemote afb;

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String flight = request.getParameter("flight");
		String dep_airport = request.getParameter("dep_airport");
		String arr_airport = request.getParameter("arr_airport");

		String dep_time = request.getParameter("dep_time");

		String company = request.getParameter("company");
		String state = request.getParameter("state");
		int free_seats = Integer.parseInt(request.getParameter("free_seats"));
		double price = Integer.parseInt(request.getParameter("price"));

		if(afb.addFlight(new Flight(flight, dep_airport, arr_airport, dep_time, company, state, free_seats, price)))
			request.getRequestDispatcher("flights/addFlightSuccess.html").forward(request, response);

	}

}
