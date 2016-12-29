package booking;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import flight.Flight;



/**
 * Servlet implementation class FlightServlet
 */
@WebServlet("/FlightServlet")
public class BookingFlightServlet extends HttpServlet {
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
		
		response.getWriter().write("You have booked a seat on flight " + request.getParameter("flight") + "!");
	}

}
