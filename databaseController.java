import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;

import java.util.Vector;
/******************************************************************************************
 * 
 * @author Hayley Carter
 * 
 * Database Controller class. The database queries and connections are all handled here.
 * 
 */

public class databaseController {
	Connection  con;
	Statement statement;
	PreparedStatement pStatement;
	ResultSet rs;
	
	// We are currently using a server hosted by Ryan for our database.
	private String dbUser = "root";
	//private String dbUser = "hayley";
	private String dbPassword = "Admin@123";
	//private String dbPassword = "hayley";
	//private String url = "jdbc:mysql://cscapstone.ddns.net:3306/sys";
	private static final String url = "jdbc:mysql://localhost:3306/film";

	
	//Initializer
	public databaseController() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	// Retrieves a list of course IDs to use for acquiring a student's schedule.
	// OPTIMIZATION NOTE: This can be combined with the getStudentSchedule method. Combine queries.
	public Vector<String> getStudentCourses(int studentID) throws SQLException {
		Vector<String> courseList = new Vector<String>();	
		try {
			con = DriverManager.getConnection(url, dbUser, dbPassword);
			statement = con.createStatement();
			rs = statement.executeQuery("SELECT course_id FROM studentsInCourses WHERE student_id = " + studentID);
		
		while (rs.next()) {				
				courseList.add(rs.getString("course_id"));
			}
		
		}
		catch (SQLException e) {
			throw e;
		}
		finally {
			con.close();
			statement.close();
			rs.close();
		}
		return courseList;
			
	}
	
	// Gets a student's schedule using a list of course IDs.
	public Vector<Course> getStudentSchedule(Vector<String> courses) throws SQLException {
		Vector<Course> courseSchedule = new Vector<Course>();
		try {
			con = DriverManager.getConnection(url, dbUser, dbPassword);
			statement = con.createStatement();
		
		
		for (int i = 0; i < courses.size(); i++) {
			String temp = "\"" + courses.elementAt(i) + "\"";
			String query = "SELECT * FROM courses WHERE course_id = " + temp;
			rs = statement.executeQuery(query);
			while (rs.next()) {
				String courseID = rs.getString(1); 
				String courseTitle = rs.getString(2);  
				String courseMajor = rs.getString(3);  
				String courseInstructor = rs.getString(4);  
				Time startTime = rs.getTime(5);
				Time endTime = rs.getTime(6);
				String quarterOffered = rs.getString(7); 
				int yearOffered = rs.getInt(8);
				
				Course course = new Course(courseID, courseTitle, courseMajor, courseInstructor, startTime, endTime, quarterOffered, yearOffered);
				courseSchedule.add(course);
			}
		}
		
		}
		catch (SQLException e) {
			throw e;
		}
		finally {
			
		}
		return courseSchedule;
	}
	
	//Gets a list of student ids associated with a course for acquiring the names of students in a given class.
	//OPTIMIZATION NOTE: This can be combined with getStudentsInClasses method. Combine queries.
	public Vector<Integer> getStudentIDsInClass(String courseID) throws SQLException {
		Vector<Integer> studentIDs = new Vector<Integer>();
		
		try {
			con = DriverManager.getConnection(url, dbUser, dbPassword);
			statement = con.createStatement();
			rs = statement.executeQuery("SELECT student_id FROM studentsInCourses WHERE course_id = \"" + courseID + "\"");
			
			while (rs.next()) {
				studentIDs.add(rs.getInt("student_id"));
			}
		} 
		catch (SQLException e) {
			throw e;
		}
		finally {
			con.close();
			statement.close();
			rs.close();
		}
		
		return studentIDs;
	}
	
	// Gets a list of students names in a given class based on their ids
	public Vector<String> getStudentsInClass(Vector<Integer> studentIDs) throws SQLException{
		Vector<String> studentList = new Vector<String>();
		
		try { 
		con = DriverManager.getConnection(url, dbUser, dbPassword);
		statement = con.createStatement();
		
		for (int i = 0; i < studentIDs.size(); i++) {
			rs = statement.executeQuery("SELECT first_name FROM students WHERE student_id = " + studentIDs.elementAt(i));
			
			while (rs.next()) {
				studentList.add(rs.getString(1));
				}
			}
		}
		catch (SQLException e) {
			throw e;
		}
		finally {
			con.close();
			statement.close();
			rs.close();
		}
		return studentList;
	}

	public Boolean addCourse(int studentID, String courseID) throws SQLException {
		// First, validate the inputs.
		if (!validateStudent(studentID)) {
			System.out.println("invalid student id");
			return false;
		}
		if (!validateCourse(courseID)) {
			System.out.println("invalid course id");
			return false;
		}
		
		//If the inputs are valid, we can perform the add course operation.
		try {
			con = DriverManager.getConnection(url, dbUser, dbPassword);
			pStatement = con.prepareStatement("INSERT INTO studentsInCourses (course_id, student_id) VALUES (?, ?)");
			pStatement.setString(1, courseID);
			pStatement.setInt(2, studentID);
			pStatement.executeUpdate();
		}
		catch (SQLException e) {
			throw e;
		}
		finally {
			con.close();
			pStatement.close();
			rs.close();
		}
		
		return true;
	}
	
	public Boolean removeCourse (int studentID, String courseID) throws SQLException {
		// First, validate the inputs.
		if (!validateStudent(studentID)) {
			System.out.println("invalid student id");
			return false;
		}
		if (!validateCourse(courseID)) {
			System.out.println("invalid course id");
			return false;
		}
		
		//If the inputs are valid, we can perform the add course operation.
		try {
			con = DriverManager.getConnection(url, dbUser, dbPassword);
			pStatement = con.prepareStatement("DELETE FROM studentsInCourses WHERE course_id = ? AND student_id = ?");
			pStatement.setString(1, courseID);
			pStatement.setInt(2, studentID);
			pStatement.executeUpdate();
		}
		catch (SQLException e) {
			throw e;
		}
		finally {
			con.close();
			pStatement.close();
			rs.close();
		}
		
		return true;
	}
	
	public Boolean validateStudent(int studentID) throws SQLException {
		try {
			con = DriverManager.getConnection(url, dbUser, dbPassword);
			statement = con.createStatement();
			rs = statement.executeQuery("SELECT first_name FROM students WHERE student_id = " + studentID);
			
			if (!rs.next()) return false;
		} catch (SQLException e) {
			throw e;
		}
		finally {
			con.close();
			statement.close();
			rs.close();
		}
		
		return true;
		
	}
	
	public Boolean validateCourse(String courseID) throws SQLException {
		try {
			con = DriverManager.getConnection(url, dbUser, dbPassword);
			statement = con.createStatement();
			rs = statement.executeQuery("SELECT title FROM courses WHERE course_id = \"" + courseID + "\"");
			
			if (!rs.next()) return false;
		} catch (SQLException e) {
			throw e;
		}
		finally {
			con.close();
			statement.close();
			rs.close();
		}
		
		return true;
		
	}
	
	public String getInstructorName(int instructorID) throws SQLException {
		String result = "";
		
		try {
			con = DriverManager.getConnection(url, dbUser, dbPassword);
			statement = con.createStatement();
			rs = statement.executeQuery("SELECT first_name FROM instructors WHERE instructor_id = " + instructorID);
			
			while (rs.next()) {
				result = rs.getString(1);
			}
		}
		catch (SQLException e) {
			throw e;
		}
		finally {
			con.close();
			statement.close();
			rs.close();
		}
		
		return result;
	}
	
	public Vector<Course> getCoursesByInstructor(int instructorId) throws SQLException {
		Vector<Course> instructorSchedule = new Vector<Course>();
		
		try {
			con = DriverManager.getConnection(url, dbUser, dbPassword);
			statement = con.createStatement();
			rs = statement.executeQuery("SELECT * FROM courses WHERE instructor_id = \"" + instructorId + "\"");
			
			while (rs.next()) {
				String courseID = rs.getString(1); 
				String courseTitle = rs.getString(2);  
				String courseMajor = rs.getString(3);  
				String courseInstructor = rs.getString(4);  
				Time startTime = rs.getTime(5);
				Time endTime = rs.getTime(6);
				String quarterOffered = rs.getString(7); 
				int yearOffered = rs.getInt(8);
				
				Course course = new Course(courseID, courseTitle, courseMajor, courseInstructor, startTime, endTime, quarterOffered, yearOffered);
				instructorSchedule.add(course);
			}
		}
		catch (SQLException e) {
			throw e;
		}
		finally {
			con.close();
			statement.close();
			rs.close();
		}
		
		return instructorSchedule;
		
	}

}
