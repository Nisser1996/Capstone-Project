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
			User instructor;
			try {
				instructor = dbc.getInstructor(userID);
				request.getSession().setAttribute("User", instructor);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			response.sendRedirect("/faculty.html");
		}
		else {
			System.out.println("Failure!");
			response.sendRedirect("login/faculty.html");
		}
	}

}

