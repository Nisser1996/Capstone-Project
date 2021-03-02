import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Vector;

/******************************************************************************************
 * 
 * @author Hayley Carter
 *
 */
@WebServlet(
    name = "LogoutServlet",
    urlPatterns = {"/logout"}
)
public class LogoutServlet extends HttpServlet {
	// DatabaseController dbc = new DatabaseController();
	
@Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) 
      throws IOException, ServletException {
	
		request.getSession().removeAttribute("User");
		RequestDispatcher view = request.getRequestDispatcher("/index.html");
		
		view.forward(request, response);
	}

}

