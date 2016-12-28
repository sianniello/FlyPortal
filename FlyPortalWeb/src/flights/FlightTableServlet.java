package flights;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import flights.FlightsBean;

/**
 * Servlet implementation class FlightTable
 */
@WebServlet("/FlightTable")
public class FlightTableServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private FlightsBean fb;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FlightTableServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	@Override
	public void init() throws ServletException {
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("flights", fb.getFlights());
		request.getRequestDispatcher("flights/flights.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
