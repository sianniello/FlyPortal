package flights;

import java.io.IOException;

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
	private DeleteFlightBeanLocal dfb;



	@Override
	public void init() throws ServletException {
		dfb = new DeleteFlightBean();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println(request.getParameter("flight"));
		try {
			if(dfb.deleteFlight(request.getParameter("flight")))
				request.getRequestDispatcher("flights/delete_success.html").forward(request, response);
			else
				request.getRequestDispatcher("flights/delete_fail.html").forward(request, response);
		} catch (FlightException e) {
			e.printStackTrace();
		}
	}

}
