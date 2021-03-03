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
    name = "Select",
    urlPatterns = {"/select"}
)
public class Select extends HttpServlet {
	// DatabaseController dbc = new DatabaseController(true);
	
@Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) 
      throws IOException, ServletException {
		String instructorID = request.getParameter("id");
		String courseID = request.getParameter("select");
		String action = request.getParameter("do");
		
		String redirect = "#";
		courseID = courseID.replace(' ', '+');
		
		switch (action) {
		case "a":
			redirect = "faculty/add_student.jsp?id=" + instructorID + "&course=" + courseID;
			break;
		case "d":
			redirect = "faculty/drop_student.jsp?id=" + instructorID + "&course=" + courseID;
			break;
		case "g":
			redirect = "faculty/submit_grades.jsp?id=" + instructorID + "&course=" + courseID;
			break;
		default:
			System.out.println("default case");
			break;
		};
		
		
		response.sendRedirect(redirect);
	}

}

