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
    name = "OverridePassword",
    urlPatterns = {"/override_password"}
)
public class OverridePassword extends HttpServlet {
	DatabaseController dbc = new DatabaseController(true);
	
@Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) 
      throws IOException, ServletException {
	
		String id = request.getParameter("id");
		String sID = request.getParameter("sID");
		String sPass = request.getParameter("sPassword");
		
		Boolean success = false;
		try {
			success= dbc.changePasswordOverride(id, sID, sPass);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (success) {
			System.out.println("success!");
			response.sendRedirect("faculty/home.jsp?id=" +id);
			
		}
		else{
			System.out.println("failure!");
			response.sendRedirect("faculty/generate_login.jsp?id=" +id);
		}
	}

}

