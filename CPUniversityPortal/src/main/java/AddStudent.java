import static java.lang.Integer.parseInt;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.classes.DatabaseController;

/******************************************************************************************
 * 
 * @author Hayley Carter
 *
 */
@WebServlet(
    name = "AddStudent",
    urlPatterns = {"/add_student"}
)
public class AddStudent extends HttpServlet {
	DatabaseController dbc = new DatabaseController(true);
	
@Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) 
      throws IOException, ServletException {
		String userID = request.getParameter("id");
		String studentID = request.getParameter("student");
		String courseID = request.getParameter("courseID");
		
		System.out.println(courseID);
		
        int intID = parseInt(studentID);
        String reformatID = String.format("%010d", intID);
        if (reformatID.equals(studentID)) {
	        try {
				dbc.addCourse(studentID, courseID);
			} catch (SQLException e) {
				e.printStackTrace();
			}
	        response.sendRedirect("faculty/home.jsp?id=" + userID);
        }
        else {
        	System.out.println("failed because studentID was invalid");
        }
	}

}

