import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Vector;

@WebServlet(
    name = "EnrollServlet",
    urlPatterns = {"/enroll"}
)
public class EnrollServlet extends HttpServlet {
	databaseController dbController = new databaseController();
	
@Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) 
      throws IOException {
	String temp = request.getParameter("studentID");
	int studentID = Integer.parseInt(temp);
	String courseID = request.getParameter("courseID");
	
	Boolean success = true;
	Vector<Course> courseSchedule = new Vector<Course>();
	
	System.out.println("The student id is " + studentID + ", and the course id is " + courseID + ".");
	try {
		success = dbController.addCourse(studentID, courseID);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	if (success) {
		try {
			courseSchedule = dbController.getStudentSchedule(dbController.getStudentCourses(studentID));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String schedule = "<h2>Your Current Course Schedule</h2>\n";
		
		for (int i = 0; i < courseSchedule.size(); i++) {
			schedule += "<p>";
			schedule += courseSchedule.elementAt(i).toString();
			schedule += "</p>";
		}
		String htmlResponse = "<!DOCTYPE html>\n"
				+ "<html lang=\"en\">\n"
				+ "<head>\n"
				+ "    <meta charset=\"UTF-8\">\n"
				+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
				+ "    <title>iAcademic | Student Portal</title>\n"
				+ "    <link rel=\"icon\" href=\"images/iAcademic2.ico\" type=\"image/icon type\">\n"
				+ "    <link rel=\"stylesheet\" href=\"CSS/styles.css\">\n"
				+ "    <link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css\" integrity=\"sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2\" crossorigin=\"anonymous\">\n"
				+ "    <script src=\"https://code.jquery.com/jquery-3.5.1.slim.min.js\" integrity=\"sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj\" crossorigin=\"anonymous\"></script>\n"
				+ "    <script src=\"https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js\" integrity=\"sha384-ho+j7jyWK8fNQe+A12Hb8AhRq26LrZ/JpcUGGOn+Y7RsweNrtN/tE3MoK7ZeZDyx\" crossorigin=\"anonymous\"></script>\n"
				+ "    \n"
				+ "</head>\n"
				+ "<body>\n"
				+ "  <!--Navigation Bar-->\n"
				+ "  <section id=\"title\">\n"
				+ "    <nav class=\"navbar navbar-expand-lg navbar-light bg-danger\">\n"
				+ "      <a class=\"navbar-brand\" href=\"index.html\"><img src=\"../images/iAcademic.ico\" height=\"35\" width=\"35\"></a>\n"
				+ "      <button class=\"navbar-toggler\" type=\"button\" data-toggle=\"collapse\" data-target=\"#navbarNavDropdown\" aria-controls=\"navbarNavDropdown\" aria-expanded=\"false\" aria-label=\"Toggle navigation\">\n"
				+ "      <span class=\"navbar-toggler-icon\"></span>\n"
				+ "      </button>\n"
				+ "      <div class=\"collapse navbar-collapse\" id=\"navbarNavDropdown\">\n"
				+ "        <ul class=\"navbar-nav\">\n"
				+ "          <li class=\"nav-item dropdown\"><a class=\"nav-link\" href=\"index.html\">Home <span class=\"sr-only\">(current)</span></a></li>\n"
				+ "          <li class=\"nav-item active\"><a class=\"nav-link dropdown-toggle\" href=\"#\" id=\"navbarDropdownMenuLink\" role=\"button\" data-toggle=\"dropdown\" aria-haspopup=\"true\" aria-expanded=\"false\">Choose platform</a>\n"
				+ "            <div class=\"dropdown-menu\" aria-labelledby=\"navbarDropdownMenuLink\">\n"
				+ "              <a class=\"dropdown-item\" href=\"student.html\">Student</a>\n"
				+ "              <a class=\"dropdown-item\" href=\"faculty.html\">Faculty</a>\n"
				+ "              <a class=\"dropdown-item\" href=\"staff.html\">Staff</a>\n"
				+ "            </div>\n"
				+ "          </li>\n"
				+ "        </ul>\n"
				+ "      </div>\n"
				+ "    </nav>\n"
				+ "  </section>\n"
				+ "  <section id=\"features\">\n"
				+ "    <div style=\"display:inline-block\">\n"
				+ "    <h1>Add Course</h1>\n"
				+ "    <form action=\"enroll\">\n"
				+ "        <label for=\"studentID\">Student ID:</label><br>\n"
				+ "        <input type=\"number\" id=\"studentID\" name=\"studentID\" value=\"\">\n"
				+ "        <p>ex: 1</p>\n"
				+ "        <label for=\"courseID\">Course ID:</label><br>\n"
				+ "        <input type=\"text\" id=\"courseID\" name=\"courseID\" value=\"\">\n"
				+ "        <p>ex: CS 362</p>\n"
				+ "        <input type=\"submit\" value=\"Submit\">\n"
				+ "     </form> \n"
				+ "     </div>\n"
				
				+ "      <p> Course Added!</p>\n"
				
				+ "    <div style=\"display:inline-block\">\n"
				+ "    <h1>Drop Course</h1>\n"
				+ "    <form action=\"drop\">\n"
				+ "        <label for=\"studentID\">Student ID:</label><br>\n"
				+ "        <input type=\"number\" id=\"studentID\" name=\"studentID\" value=\"\">\n"
				+ "        <p>ex: 1</p>\n"
				+ "        <label for=\"courseID\">Course ID:</label><br>\n"
				+ "        <input type=\"text\" id=\"courseID\" name=\"courseID\" value=\"\">\n"
				+ "        <p>ex: CS 362</p>\n"
				+ "        <input type=\"submit\" value=\"Submit\">\n"
				+ "      </form>\n"
				+ "      </div>\n"
				
				+ schedule
				
				+ "  </section>\n"
				+ "</body>\n"
				+ "</html>";
		
		PrintWriter writer = response.getWriter();
		writer.println(htmlResponse);
	}
	else {
		String htmlResponse = "<!DOCTYPE html>\n"
				+ "<html lang=\"en\">\n"
				+ "<head>\n"
				+ "    <meta charset=\"UTF-8\">\n"
				+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
				+ "    <title>iAcademic | Student Portal</title>\n"
				+ "    <link rel=\"icon\" href=\"images/iAcademic2.ico\" type=\"image/icon type\">\n"
				+ "    <link rel=\"stylesheet\" href=\"CSS/styles.css\">\n"
				+ "    <link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css\" integrity=\"sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2\" crossorigin=\"anonymous\">\n"
				+ "    <script src=\"https://code.jquery.com/jquery-3.5.1.slim.min.js\" integrity=\"sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj\" crossorigin=\"anonymous\"></script>\n"
				+ "    <script src=\"https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js\" integrity=\"sha384-ho+j7jyWK8fNQe+A12Hb8AhRq26LrZ/JpcUGGOn+Y7RsweNrtN/tE3MoK7ZeZDyx\" crossorigin=\"anonymous\"></script>\n"
				+ "    \n"
				+ "</head>\n"
				+ "<body>\n"
				+ "  <!--Navigation Bar-->\n"
				+ "  <section id=\"title\">\n"
				+ "    <nav class=\"navbar navbar-expand-lg navbar-light bg-danger\">\n"
				+ "      <a class=\"navbar-brand\" href=\"index.html\"><img src=\"../images/iAcademic.ico\" height=\"35\" width=\"35\"></a>\n"
				+ "      <button class=\"navbar-toggler\" type=\"button\" data-toggle=\"collapse\" data-target=\"#navbarNavDropdown\" aria-controls=\"navbarNavDropdown\" aria-expanded=\"false\" aria-label=\"Toggle navigation\">\n"
				+ "      <span class=\"navbar-toggler-icon\"></span>\n"
				+ "      </button>\n"
				+ "      <div class=\"collapse navbar-collapse\" id=\"navbarNavDropdown\">\n"
				+ "        <ul class=\"navbar-nav\">\n"
				+ "          <li class=\"nav-item dropdown\"><a class=\"nav-link\" href=\"index.html\">Home <span class=\"sr-only\">(current)</span></a></li>\n"
				+ "          <li class=\"nav-item active\"><a class=\"nav-link dropdown-toggle\" href=\"#\" id=\"navbarDropdownMenuLink\" role=\"button\" data-toggle=\"dropdown\" aria-haspopup=\"true\" aria-expanded=\"false\">Choose platform</a>\n"
				+ "            <div class=\"dropdown-menu\" aria-labelledby=\"navbarDropdownMenuLink\">\n"
				+ "              <a class=\"dropdown-item\" href=\"student.html\">Student</a>\n"
				+ "              <a class=\"dropdown-item\" href=\"faculty.html\">Faculty</a>\n"
				+ "              <a class=\"dropdown-item\" href=\"staff.html\">Staff</a>\n"
				+ "            </div>\n"
				+ "          </li>\n"
				+ "        </ul>\n"
				+ "      </div>\n"
				+ "    </nav>\n"
				+ "  </section>\n"
				+ "  <section id=\"features\">\n"
				+ "  	<div style=\"display:inline-block\">\n"
				+ "    <h1>Add Course</h1>\n"
				+ "    <form action=\"enroll\">\n"
				+ "        <label for=\"studentID\">Student ID:</label><br>\n"
				+ "        <input type=\"number\" id=\"studentID\" name=\"studentID\" value=\"\">\n"
				+ "        <p>ex: 1</p>\n"
				+ "        <label for=\"courseID\">Course ID:</label><br>\n"
				+ "        <input type=\"text\" id=\"courseID\" name=\"courseID\" value=\"\">\n"
				+ "        <p>ex: CS 362</p>\n"
				+ "        <input type=\"submit\" value=\"Submit\">\n"
				+ "      </form> \n"
				+ "    </div>\n"
				
				+"     <p> Failed to add course. Please check your inputs and try again!</p>\n"
				
				+ "    <div style=\"display:inline-block\">\n"
				+ "    <h1>Drop Course</h1>\n"
				+ "    <form action=\"drop\">\n"
				+ "        <label for=\"studentID\">Student ID:</label><br>\n"
				+ "        <input type=\"number\" id=\"studentID\" name=\"studentID\" value=\"\">\n"
				+ "        <p>ex: 1</p>\n"
				+ "        <label for=\"courseID\">Course ID:</label><br>\n"
				+ "        <input type=\"text\" id=\"courseID\" name=\"courseID\" value=\"\">\n"
				+ "        <p>ex: CS 362</p>\n"
				+ "        <input type=\"submit\" value=\"Submit\">\n"
				+ "      </form>\n"
				+ "      </div>\n"
				+ "  </section>\n"
				+ "</body>\n"
				+ "</html>";
		
		PrintWriter writer = response.getWriter();
		writer.println(htmlResponse);	
	}
}
}

