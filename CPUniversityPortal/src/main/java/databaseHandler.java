import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(
    name = "databaseHandler",
    urlPatterns = {"/test"}
)
public class databaseHandler extends HttpServlet {
	Connection  con;
	
	final String createCourseTable = 
			"CREATE TABLE IF NOT EXISTS courses ("
			+ "course_id VARCHAR(10) NOT NULL, "
			+ "course_title VARCHAR(100) NOT NULL, "
			+ "course_major VARCHAR(100) NOT NULL, "
			+ "start_time TIME, "
			+ "end_time TIME, "
			+ "PRIMARY KEY (course_id)"
			+ ")";

@Override

public void init() throws ServletException {
	
	try {
		Class.forName("com.mysql.cj.jdbc.Driver");
	} catch (ClassNotFoundException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}

	System.out.println("step 1 done");
	
	try {
		String url = "jdbc:mysql://127.0.0.1:3306/cpTest";
		try {
			con = DriverManager.getConnection(url, "root", "ladidadida");
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM courses");
			
			while (rs.next()) {
				String courseID = rs.getString("course_id");
				String courseTitle = rs.getString("course_title");
				String courseField = rs.getString("course_field");
				String courseInstructor = rs.getString("course_instructor");
				Time startTime = rs.getTime("start_time");
				Time endTime = rs.getTime("end_time");
				
				System.out.format("%s, %s, %s, %s, %s, %s\n", courseID, courseTitle, courseField, courseInstructor, startTime, endTime);
				
				
			}
		}
		catch (SQLException e) {
			throw new ServletException ("Unable to connect to SQL server", e);
		}
		finally {
			//nah
		}
	}
	finally {
		//nah
	}
}
}

