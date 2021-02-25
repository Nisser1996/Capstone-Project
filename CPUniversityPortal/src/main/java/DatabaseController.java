import java.nio.charset.StandardCharsets;
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
 * @author Hayley Carter, Ryan Perkins
 *
 * Database Controller class. The database queries and connections are all handled here, 
 * including authentication.
 *
 */

public class DatabaseController {
    // All connection objects for database interactions
	Connection conn;
    Statement stmt;
    PreparedStatement pStmt;
    ResultSet rs;
    
    // Database connection details. Set based on whether this is the dev environment or not
    private String dbUser;
    private String dbPassword;
    private String url;

    // Initializer. Defaults to using the Google SQL database.
    public DatabaseController(){
        try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
            
        dbUser = "root";
        dbPassword = "Ladidadida";
        url = "jdbc:mysql://34.106.119.236:3306/cpDatabase";
            
    }
    
    // if we give the database controller an integer, it will establish the connection variables for the dev server.
    public DatabaseController(int x) {
    	try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
    	
    	dbUser = "hayley";
    	dbPassword = "hayley";
    	url = "jdbc:mysql://cscapstone.ddns.net:3306/sys";
    	
    	
    }

    /***************************************************************************************************************************
     * METHOD: getStudentSchedule
     * 
     * Given a student ID, this method will return the student's course schedule.
     * 
     * @param String studentID
     * @return Vector<Course> courseSchedule
     * @throws SQLException
     */
    public Vector<Course> getStudentSchedule(String studentID) throws SQLException {
        Vector<String> courseList = new Vector<String>();
        Vector<Course> courseSchedule = new Vector<Course>();
        
        try {
        	// Set up the connections and execute the first query
            conn = DriverManager.getConnection(url, dbUser, dbPassword);
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT course_id FROM studentsInCourses WHERE student_id = " + studentID);
            
            // Put the courseIDs into a vector to use for displaying a schedule
            while (rs.next()) {
                courseList.add(rs.getString("course_id"));
            }
            
            // Get the information for each course in the vector from above and return a vectors of Courses
            for (int i = 0; i < courseList.size(); i++) {
                String temp = "\"" + courseList.elementAt(i) + "\"";
                String query = "SELECT * FROM courses WHERE course_id = " + temp;
                rs = stmt.executeQuery(query);
                while (rs.next()) {
                    String courseID = rs.getString(1);
                    String title = rs.getString(2);
                    String major = rs.getString(3);
                    String instructorID = String.format("%010d", rs.getInt(4));
                    String instructor = getInstructorName(instructorID);
                    Time startTime = rs.getTime(5);
                    Time endTime = rs.getTime(6);
                    String quarterOffered = rs.getString(7);
                    int yearOffered = rs.getInt(8);

                    Course course = new Course(courseID, title, major, instructor, startTime, endTime, quarterOffered, yearOffered);
                    courseSchedule.add(course);
                }
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            conn.close();
            stmt.close();
            rs.close();
        }
        
        return courseSchedule;
    }


	/***************************************************************************************************************************
	 * METHOD: getStudentsInCourses
	 * 
	 * Given a courseID, this method will return a list of the students taking that course.
	 * 
	 * @param String courseID
	 * @return Vector<String> studentList
	 * @throws SQLException
	 */
    public Vector<String> getStudentsInCourses(String courseID) throws SQLException {
        Vector<String> studentIDs = new Vector<String>();
        Vector<String> studentList = new Vector<String>();

        try {
            conn = DriverManager.getConnection(url, dbUser, dbPassword);
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT student_id FROM studentsInCourses WHERE course_id = \"" + courseID + "\"");

            while (rs.next()) {
                studentIDs.add(String.format("%05d", rs.getInt("student_id")));
            }
            
            for (int i = 0; i < studentIDs.size(); i++) {
                rs = stmt.executeQuery("SELECT first_name, last_name FROM students WHERE student_id = " + studentIDs.elementAt(i));

                while (rs.next()) {
                	// converted to full name for display purposes. May change to first and last so that sorting by first or last name can be done.
                	String fullName = rs.getString(1);
                	fullName += " ";
                	fullName+= rs.getString(2);
                	
                    studentList.add(fullName);
                }
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            conn.close();
            stmt.close();
            rs.close();
        }

        return studentList;
    }
    
    /***************************************************************************************************************************
     * METHOD: getCoursesByInstructor
     * 
     * Given an instructorID, this method returns a list of courses that instructor teaches.
     * 
     * @param String instructorID
     * @return Vector<Course> instructorSchedule
     * @throws SQLException
     */
    public Vector<Course> getCoursesByInstructor(String instructorID) throws SQLException {
        Vector<Course> instructorSchedule = new Vector<Course>();

        try {
            conn = DriverManager.getConnection(url, dbUser, dbPassword);
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM courses WHERE instructor_id = " + instructorID);

            while (rs.next()) {
                String courseID = rs.getString(1);
                String title = rs.getString(2);
                String major = rs.getString(3);
                String instructor = getInstructorName(instructorID);
                Time startTime = rs.getTime(5);
                Time endTime = rs.getTime(6);
                String quarterOffered = rs.getString(7);
                int yearOffered = rs.getInt(8);

                Course course = new Course(courseID, title, major, instructor, startTime, endTime, quarterOffered, yearOffered);
                instructorSchedule.add(course);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            conn.close();
            stmt.close();
            rs.close();
        }

        return instructorSchedule;
    }
    
    /***************************************************************************************************************************
     * METHOD: getInstructorName
     * 
     * Given an instructorID, this method will return that instructor's full name.
     * 
     * @param String instructorID
     * @return String instructorName
     * @throws SQLException
     */
    public String getInstructorName(String instructorID) throws SQLException {
        String instructorName = "";

        try {
            conn = DriverManager.getConnection(url, dbUser, dbPassword);
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT first_name, last_name FROM instructors WHERE instructor_id = " + instructorID);

            while (rs.next()) {
                instructorName = rs.getString(1);
                instructorName += " ";
                instructorName += rs.getString(2);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            conn.close();
            stmt.close();
            rs.close();
        }

        return instructorName;
    }
    /***************************************************************************************************************************
     * METHOD: addCourse
     * 
     * Given a studentID and a courseID, adds that course to that student's schedule.
     * 
     * @param String studentID
     * @param String courseID
     * @return true if the course was added, false if not
     * @throws SQLException
     */
    public Boolean addCourse(String studentID, String courseID) throws SQLException {
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
            conn = DriverManager.getConnection(url, dbUser, dbPassword);
            pStmt = conn.prepareStatement("INSERT INTO studentsInCourses (course_id, student_id) VALUES (?, ?)");
            pStmt.setString(1, courseID);
            pStmt.setInt(2, Integer.parseInt(studentID));
            pStmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            conn.close();
            pStmt.close();
            rs.close();
        }

        return true;
    }
    
    /***************************************************************************************************************************
     * METHOD: dropCourse
     * 
     * Given a studentID and a courseID, drops that course from that student's schedule.
     * 
     * @param String studentID
     * @param String courseID
     * @return true if the course was dropped, false if not
     * @throws SQLException
     */
    public Boolean dropCourse(String studentID, String courseID) throws SQLException {
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
            conn = DriverManager.getConnection(url, dbUser, dbPassword);
            pStmt = conn.prepareStatement("DELETE FROM studentsInCourses WHERE course_id = ? AND student_id = ?");
            pStmt.setString(1, courseID);
            pStmt.setInt(2, Integer.parseInt(studentID));
            pStmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            conn.close();
            pStmt.close();
            rs.close();
        }

        return true;
    }
    
    /***************************************************************************************************************************
     * METHOD: validateCourse
     * 
     * Given a courseID, checks if that course is in the system.
     * 
     * @param String courseID
     * @return true if the course exists, false if not
     * @throws SQLException
     */
    public Boolean validateCourse(String courseID) throws SQLException {
        try {
            conn = DriverManager.getConnection(url, dbUser, dbPassword);
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT title FROM courses WHERE course_id = \"" + courseID + "\"");

            if (!rs.next()) return false;
        } catch (SQLException e) {
            throw e;
        } finally {
            conn.close();
            stmt.close();
            rs.close();
        }

        return true;
    }
    
    // Checks if a student is in the system. May be unnecessary, will have to double check authentication functions
    public Boolean validateStudent(String studentID) throws SQLException {
        try {
            conn = DriverManager.getConnection(url, dbUser, dbPassword);
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM students WHERE student_id = " + studentID);

            if (!rs.next()) return false;
        } catch (SQLException e) {
            throw e;
        } finally {
            conn.close();
            stmt.close();
            rs.close();
        }

        return true;

    }

    public LoginData getLoginData(String UserID) throws SQLException {
        LoginData data = new LoginData();
        try {
            conn = DriverManager.getConnection(url, dbUser, dbPassword);
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM login WHERE userid = \"" + UserID + "\"");

            while (rs.next()) {
                data.userID = rs.getString(1);
                data.hash = rs.getString(2);
                data.salt = rs.getBytes(3);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            conn.close();
            stmt.close();
            rs.close();
        }
        if (!data.userID.equals(UserID)) {
            // User received an incorrect user record, abort and return null
            // this *should* never happen
            return null;
        }

        return data;
    }

    public String addLoginData(String hash, byte[] salt) throws SQLException {
        LoginData data = new LoginData();
        data.hash = hash;
        data.salt = salt;
        try {
            conn = DriverManager.getConnection(url, dbUser, dbPassword);
            stmt = conn.createStatement();
            int ret = stmt.executeUpdate("INSERT INTO login (hash, salt) values (\"" + hash + "\", 0x" + data.getSaltAsHex() + ")");
            if (ret != 1)
                return null;
            rs = stmt.executeQuery("SELECT userid FROM login WHERE hash=\"" + hash + "\" AND salt = 0x" + data.getSaltAsHex() + "");
            while (rs.next()) {
                data.userID = String.format("%05d", rs.getInt(1));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            conn.close();
            stmt.close();
            rs.close();
        }
        return data.userID;
    }

    public boolean addNewStudent(String studentID, String firstName, String lastName) throws SQLException{
        try {
            conn = DriverManager.getConnection(url, dbUser, dbPassword);
            stmt = conn.createStatement();
            // check if the user exists in the login field
            rs = stmt.executeQuery("SELECT COUNT(1) FROM login WHERE userid=\"" + studentID + "\"");
            boolean userExists = false;
            while (rs.next()) {
                userExists = true;
            }
            if(!userExists)
                return false;
            // user exists, insert into student table with "undeclared" as major
            int ret = stmt.executeUpdate("INSERT INTO students (student_id, first_name, last_name, major) " +
                    "values (\"" + studentID + "\", \"" + firstName + "\", \"" + lastName +"\", \"Undeclared\")");
            if (ret != 1)
                return false;

        } catch (SQLException e) {
            throw e;
        } finally {
            conn.close();
            stmt.close();
            rs.close();
        }
        return true;
    }

}
