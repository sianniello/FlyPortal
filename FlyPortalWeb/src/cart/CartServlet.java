package cart;

import java.io.IOException;
import java.util.TreeMap;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CartServlet
 */
@WebServlet("/CartServlet")
public class CartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		TreeMap<String, Integer> cart = (TreeMap<String, Integer>) request.getSession().getAttribute("cart");
		String op = request.getParameter("operation");
		String flight;
		
		switch(op) {
		case "add": 
			flight = request.getParameter("flight");
			if(cart == null) {
				cart = new TreeMap<>();
			}

			if(cart.containsKey(flight))
				cart.replace(flight, cart.get(flight) + 1);
			else
				cart.put(flight, 1);
			request.getSession().setAttribute("cart", cart);

			for(String f : cart.keySet())
				System.out.println(f + " " + cart.get(f));

			response.getWriter().println(cart);
			break;

		case "remove":
			flight = request.getParameter("flight");
			if(cart.containsKey(flight)) {
				if(cart.get(flight) > 1) 
					cart.replace(flight, cart.get(flight) - 1);
				else cart.remove(flight);
				response.getWriter().println("correctly removed");
			}
			else
				response.getWriter().println("not found in cart");

			if (cart.size() == 0)
				request.getSession().setAttribute("cart", null);
			break;

		case "buy":

			
			
			break;
		}
	}

}
