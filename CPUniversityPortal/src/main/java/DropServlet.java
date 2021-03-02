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
    name = "DropServlet",
    urlPatterns = {"/drop"}
)
public class DropServlet extends HttpServlet {
	DatabaseController dbc = new DatabaseController(true);
	
@Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) 
      throws IOException {
		String userID = request.getParameter("id");
		System.out.println(userID);
		String[] courseIDs = request.getParameterValues("selectedCourses");
		
		for (int i = 0; i < courseIDs.length; i++) {
			System.out.println(courseIDs[i]);
			try {
				Boolean success = dbc.dropCourse(userID, courseIDs[i]);
				if (!success) {
					System.out.println("Failed to drop " + courseIDs[i]);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		response.sendRedirect("student/home.jsp?id=" + userID);
	}
}

