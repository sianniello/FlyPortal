package login;

import java.io.IOException;
import java.net.InetAddress;

import javax.ejb.EJB;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import counter.CounterBeanLocal;
import user.User;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	private LoginBean lb;

	@EJB
	private CounterBeanLocal cb;
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
	}

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
