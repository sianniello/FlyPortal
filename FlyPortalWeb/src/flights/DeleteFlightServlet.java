package flights;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import flight.FlightException;

/**
 * Servlet implementation class DeleteFlightServlet
 */
@WebServlet("/DeleteFlightServlet")
public class DeleteFlightServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@EJB
	private DeleteFlightBeanLocal dfb;

	@Override
	public void init() throws ServletException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println(request.getParameter("flight"));
		response.setContentType("text/plain");
		try {
			if(dfb.deleteFlight(request.getParameter("flight"))) {
				//request.getRequestDispatcher("flights/delete_success.html").forward(request, response);
				response.getWriter().write("Flight " + request.getParameter("flight") + " removed!");
			}
			else {
				//request.getRequestDispatcher("flights/delete_fail.html").forward(request, response);
				response.getWriter().write("fail");
			}
		} catch (FlightException e) {
			e.printStackTrace();
		}
	}

}
