package login;

import java.io.IOException;
import java.net.InetAddress;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import counter.CounterBeanRemote;
import user.User;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	private LoginBeanRemote lb;

	@EJB
	private CounterBeanRemote cb;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String ipAddr= request.getLocalAddr();
		String locale = request.getLocale().toLanguageTag();
		
		if(lb.login(new User(request.getParameter("username"), request.getParameter("password")), request.getParameter("optradio"))) {
			HttpSession session = request.getSession();
			session.setAttribute("auth", (request.getParameter("optradio").equals("admin")? "admin" : "user"));
			session.setAttribute("username", request.getParameter("username"));
			session.setAttribute("cart", null);
			cb.setCounter(InetAddress.getByName(request.getRemoteAddr()));
			request.getRequestDispatcher("index.jsp").forward(request, response);
		}
		else
			request.getRequestDispatcher("login_fail.html").forward(request, response);
	}

}
