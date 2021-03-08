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
    name = "SubmitGrades",
    urlPatterns = {"/submit_grades"}
)
public class SubmitGrades extends HttpServlet {
	DatabaseController dbc = new DatabaseController(true);
	
@Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) 
      throws IOException, ServletException {
		String userID = request.getParameter("id");
		String courseID = request.getParameter("course");
		Course course;
		Vector<User> students;
		try {
			course = dbc.getCourse(request.getParameter("course"));
			students = dbc.getStudentsInCourse(course.courseID);
			
			String[] grades = request.getParameterValues("selectedGrade");
			
			if (students.size() == grades.length) {
				Boolean successPast = false;
				Boolean successDrop = false;
				for (int i = 0; i < grades.length; i++) {
					successPast = dbc.addPastCourse(students.elementAt(i).userID, course, grades[i]);
					if (successPast) successDrop = dbc.dropCourse(students.elementAt(i).userID, courseID);
					if (successDrop) System.out.println("Successfully performed both operations for " + students.elementAt(i).userID + " with class " + courseID);
				}
	
				response.sendRedirect("faculty/home.jsp?id=" + userID);
			}
			else {
				System.out.println("Somehow there weren't enough grades for students in that class");
				courseID = courseID.replace(' ', '+');
				response.sendRedirect("faculty/submit_grades.jsp?id=" + userID + "&course=" + courseID);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}

