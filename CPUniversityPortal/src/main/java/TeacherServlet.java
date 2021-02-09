import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Vector;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(
    name = "TeacherServlet",
    urlPatterns = {"/teacher"}
)
public class TeacherServlet extends HttpServlet {
	databaseController dbController = new databaseController();

@Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) 
      throws IOException {

	String temp = request.getParameter("teacherID");
	int teacherID = Integer.parseInt(temp);
	
	String teacherName = "";
	Vector<Course> instructorSchedule = new Vector<Course>();
	
	try {
		teacherName = dbController.getInstructorName(teacherID);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	try {
		instructorSchedule = dbController.getCoursesByInstructor(teacherName);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	String schedule = "<h2>Your Current Course Schedule</h2>\n";
	
	for (int i = 0; i < instructorSchedule.size(); i++) {
		schedule += "<p>";
		schedule += instructorSchedule.elementAt(i).toString();
		schedule += "</p>";
	}
	String htmlResponse = "<!DOCTYPE html>\n"
			+ "<html lang=\"en\">\n"
			+ "<head>\n"
			+ "    <meta charset=\"UTF-8\">\n"
			+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
			+ "    <title>iAcademic | Faculty Portal</title>\n"
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
			+ "      <a class=\"navbar-brand\" href=\"#\"><img src=\"images/iAcademic.ico\" height=\"35\" width=\"35\"></a>\n"
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
			+ "    <h1>Check Course Information</h1>\n"
			+ "    <form action=\"teacher\">\n"
			+ "        <label for=\"studentID\">Teacher ID:</label><br>\n"
			+ "        <input type=\"number\" id=\"teacherID\" name=\"teacherID\" value=\"\">\n"
			+ "        <p>ex: 1</p>\n"
			+ "        <input type=\"submit\" value=\"Submit\">\n"
			+ "      </form> \n"
			+ schedule
			+ "  </section>\n"
			+ "</body>\n"
			+ "</html>";
	
	PrintWriter writer = response.getWriter();
	writer.println(htmlResponse);
	

  }
}