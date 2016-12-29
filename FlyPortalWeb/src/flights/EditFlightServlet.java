package flights;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import flight.Flight;

/**
 * Servlet implementation class EditFlightServlet
 */
@WebServlet("/EditFlightServlet")
public class EditFlightServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private EditFlightBeanLocal efb;

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		efb = new EditFlightBean();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String flight = request.getParameter("flight");
		String aux[] = flight.split("#");
		request.setAttribute("flight", aux[0]);
		if(efb.editFlight(new Flight(aux[0],aux[1],aux[2],aux[3],aux[4],aux[5],Integer.parseInt(aux[6]),Double.parseDouble(aux[7]))))
			response.getWriter().write("Flight " + request.getAttribute("flight") + " modified!");
		else
			response.getWriter().write("Flight " + request.getAttribute("flight") + " not modified!");
	}

}
