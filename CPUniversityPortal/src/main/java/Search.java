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
    name = "Search",
    urlPatterns = {"/search"}
)
public class Search extends HttpServlet {
	DatabaseController dbc = new DatabaseController(true);
	
@Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) 
      throws IOException {
		String id = request.getParameter("id");
	
		String term = request.getParameter("term");
		String year = request.getParameter("year");
		String subject = request.getParameter("subj");
		String cName = request.getParameter("cName");
		String cID = request.getParameter("cID");
		
		subject.replace(" ", "+");
		cName.replace(" ", "+");
		cID.replace(" ", "+");
	
	
		response.sendRedirect("student/search_results.jsp?id=" + id + "&term=" + term +"&year=" + year + "&subj=" + subject + "&cName=" + cName +"&cID=" + cID);
	}
}

