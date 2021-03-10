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
    name = "ChangePassword",
    urlPatterns = {"/student_override"}
)
public class ChangePassword extends HttpServlet {
	DatabaseController dbc = new DatabaseController(true);
	
@Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) 
      throws IOException, ServletException {
	
		String id = request.getParameter("id");
		String old = request.getParameter("old");
		String password = request.getParameter("new");
		
		Boolean success = false;
		try {
			success= dbc.changePassword(id, old, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (success) {
			System.out.println("success!");
			response.sendRedirect("../index.html");
			
		}
		else{
			System.out.println("failure!");
		response.sendRedirect("login/change_password.jsp");
		}
	}

}

