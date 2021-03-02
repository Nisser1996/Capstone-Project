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
    name = "TeacherAuth",
    urlPatterns = {"/tAuth"}
)
public class TeacherAuth extends HttpServlet {
	DatabaseController dbc = new DatabaseController(true);
	
@Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) 
      throws IOException, ServletException {
		String userID = request.getParameter("tID");
		
		String password = request.getParameter("tPassword");
		
		
	
		AuthenticationManager auth = new AuthenticationManager(dbc);
		Boolean success = auth.login(userID, password);
		
		if (success) {
			System.out.println("Success! " + userID);
			response.sendRedirect("faculty/home.jsp?id=" + userID);
		}
		else {
			System.out.println("Failure!");
			response.sendRedirect("login/faculty.html");
		}
	}

}

