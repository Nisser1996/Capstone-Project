import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(
    name = "StudentPage",
    urlPatterns = {"/student"}
)
public class StudentPage extends HttpServlet {

@Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) 
      throws IOException {

    response.setContentType("text/plain");
    response.setCharacterEncoding("UTF-8");
    
    double gpa = calculateGPA('B', 4);

    response.getWriter().print("This page will eventually allow students to log in. For now, your GPA is " + gpa + ".\r\n");
   
  }

	public double calculateGPA(char grade, int credits) {
		double gradeVal = 0;
	
	
		switch (grade) {
			case 'A':
				gradeVal = 4;
				break;
			case 'B':
				gradeVal = 3;
				break;
			case 'C':
				gradeVal = 2;
				break;
			case 'D':
				gradeVal = 1;
				break;
			case 'F':
				gradeVal = 0;
				break;
		}
		double result = (gradeVal*credits)/credits;

		return result;
	}

}

