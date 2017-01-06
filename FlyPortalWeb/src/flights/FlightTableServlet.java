package flights;

import java.io.IOException;
import java.net.InetAddress;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import flight.Flight;
import flight.FlightException;
import flights.FlightsBean;

/**
 * Servlet implementation class FlightTable
 */
@WebServlet("/FlightTable")
public class FlightTableServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@EJB
	private FlightsBean fb;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FlightTableServlet() {
    }

	@Override
	public void init() throws ServletException {
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		InetAddress clientIP = InetAddress.getByName(request.getRemoteAddr());
		request.setAttribute("flights", fb.getFlights());
		request.getRequestDispatcher("flights/flights.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String flight = request.getParameter("flight");
		try {
			Flight f = fb.getFlight(flight);
			response.getWriter().println(f.getDepAirport() + "#" + f.getArrAirport() + "#" + f.getDepTime() + "#" +
			f.getCompany() + "#" + f.getState() + "#" + f.getFreeSeats());
		} catch (FlightException e) {
			e.printStackTrace();
		}
	}

}
