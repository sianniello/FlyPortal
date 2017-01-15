package flights;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet implementation class FlightDataServlet
 */
@WebServlet("/FlightDataServlet")
public class FlightDataServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//System.out.println(service.getAirports());
		request.getRequestDispatcher("flights/add_flight.jsp").forward(request, response);
	}

}
