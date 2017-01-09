package visitors;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import counter.CounterBeanRemote;

/**
 * Servlet implementation class VisitorServlet
 */
@WebServlet("/VisitorServlet")
public class VisitorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@EJB
	private CounterBeanRemote cb;

	@Override
	public void init() throws ServletException {
	}



	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println(cb.getVisitorsSet());
		response.getWriter().write(cb.getVisitorsSet().toString());
	}

}
