import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Vector;

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
    name = "AddClass",
    urlPatterns = {"/enroll"}
)
public class AddClass extends HttpServlet {
	DatabaseController dbc = new DatabaseController(true);
	
@Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) 
      throws IOException {
		String studentID= request.getParameter("id");
		String[] courses = request.getParameterValues("selectedCourses");
		
		for (int i = 0; i < courses.length; i++) {
			try {
				Boolean success = dbc.addCourse(studentID, courses[i]);
				if (!success) System.out.println("Failed to add " + courses[i] + " for student " + studentID);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		response.sendRedirect("student/home.jsp?id=" + studentID);

	}
}

