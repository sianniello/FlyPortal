package flights;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.WebServiceRef;

import flyportal.FlyPortalWebServices;

/**
 * Servlet implementation class FlightDataServlet
 */
@WebServlet("/FlightDataServlet")
public class FlightDataServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@WebServiceRef(wsdlLocation = 
		      "META-INF/wsdl/localhost_8080/FlightsWebService.wsdl")
		    private static FlyPortalWebServices service;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("airportsList", service.getAirports());
		request.setAttribute("airlinesList", service.getAirlines());
		
		request.getRequestDispatcher("add_flight.jsp").forward(request, response);
	}

}
