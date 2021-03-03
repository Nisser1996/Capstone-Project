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

import com.classes.*;

/******************************************************************************************
 * 
 * @author Hayley Carter
 *
 */
@WebServlet(
    name = "DropStudent",
    urlPatterns = {"/drop_student"}
)
public class DropStudent extends HttpServlet {
	DatabaseController dbc = new DatabaseController(true);
	
@Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) 
      throws IOException, ServletException {
		String instructorID = request.getParameter("id");
		String courseID = request.getParameter("course");
		String[] studentIDs = request.getParameterValues("selectedStudents");
		
		for (int i = 0; i < studentIDs.length; i++) {
			System.out.println(studentIDs[i]);
			try {
				Boolean success = dbc.dropCourse(studentIDs[i], courseID);
				if (!success) {
					System.out.println("Failed to drop " + studentIDs[i]);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		response.sendRedirect("faculty/home.jsp?id=" + instructorID);
	}

}

