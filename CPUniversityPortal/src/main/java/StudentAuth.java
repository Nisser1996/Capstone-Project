import java.io.IOException;
import java.sql.SQLException;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.classes.*;

/******************************************************************************************
 * 
 * @author Hayley Carter
 *
 */
@WebServlet(
    name = "StudentAuth",
    urlPatterns = {"/sAuth"}
)
public class StudentAuth extends HttpServlet {
	DatabaseController dbc = new DatabaseController(true);
	
@Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) 
      throws IOException, ServletException {
		
		String userID = request.getParameter("sID");
		String password = request.getParameter("sPassword");
	
		AuthenticationManager auth = new AuthenticationManager(dbc);
		Boolean success = auth.login(userID, password);
		
		if (success) {
			System.out.println("Success!");
			response.sendRedirect("student/home.jsp?id=" + userID);
		}
		else {
			System.out.println("Failure!");
			response.sendRedirect("login/student.html");
		}
	}

}

