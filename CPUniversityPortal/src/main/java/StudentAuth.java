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
    name = "StudentAuth",
    urlPatterns = {"/sAuth"}
)
public class StudentAuth extends HttpServlet {
	DatabaseController dbc = new DatabaseController(true);
	
@Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) 
      throws IOException, ServletException {
		
		String userID = request.getParameter("sID");
		if (userID == null) System.out.println("UserID is null");
			
		String password = request.getParameter("sPassword");
		if (password == null) System.out.println("Password is null");
		
		System.out.println("Got user and password, time to authenticate!");
	
		AuthenticationManager auth = new AuthenticationManager(dbc);
		Boolean success = auth.login(userID, password);
		
		if (success) {
			System.out.println("Success!");
			User student;
				try {
					student = dbc.getStudent(userID);
					request.getSession().setAttribute("User", student);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			
			RequestDispatcher view = request.getRequestDispatcher("../student.html");
			view.forward(request, response);
		}
		else {
			System.out.println("Failure!");
			RequestDispatcher view = request.getRequestDispatcher("login/student.html");
			view.forward(request, response);
		}
	}

}

