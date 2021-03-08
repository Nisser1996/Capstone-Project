package com.classes;

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
    public DatabaseController(Boolean isServer) {
    	try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
    	
    	if (isServer) {
	    	dbUser = "hayley";
	    	dbPassword = "hayley";
	    	url = "jdbc:mysql://cscapstone.ddns.net:3306/sys";
    	}
    	else {
	    	dbUser = "root";
	    	dbPassword = "Ladidadida";
	    	url = "jdbc:mysql://127.0.0.1:3306/cpTest";
    	}
    	
    	
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
        Vector<String> instructorIDs = new Vector<String>();
        
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
                    String instructor = String.format("%010d", rs.getInt(4));
                    Time startTime = rs.getTime(5);
                    Time endTime = rs.getTime(6);
                    String quarterOffered = rs.getString(7);
                    int yearOffered = rs.getInt(8);

                    Course course = new Course(courseID, title, major, instructor, startTime, endTime, quarterOffered, yearOffered);
                    courseSchedule.add(course);
                    instructorIDs.add(instructor);
                }
            }
            for (int i = 0 ; i < instructorIDs.size(); i++) {
            	courseSchedule.elementAt(i).instructor = getInstructor(instructorIDs.elementAt(i)).toString();
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
     * METHOD: getStudentHistory
     * 
     * Given a student ID, this method will return the student's course history.
     * 
     * @param String studentID
     * @return Vector<Course> courseSchedule
     * @throws SQLException
     */
    public Vector<Course> getStudentHistory(String studentID) throws SQLException {
        Vector<Course> courseSchedule = new Vector<Course>();
        
        try {
        	// Set up the connections and execute the first query
            conn = DriverManager.getConnection(url, dbUser, dbPassword);
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM pastCourses WHERE student_id = " + studentID);
            
            // Put the courseIDs into a vector to use for displaying a schedule
            while (rs.next()) {
                    String courseID = rs.getString(1);
                    String grade = rs.getString(3);
                    String quarterOffered = rs.getString(4);
                    int yearOffered = rs.getInt(5);

                    Course course = new Course(courseID, quarterOffered, yearOffered, grade);
                    courseSchedule.add(course);
                
            	}
            
            for (int i = 0 ; i < courseSchedule.size(); i++) {
            	Course course = getCourse(courseSchedule.elementAt(i).courseID);
            	courseSchedule.elementAt(i).title = course.title;
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
     * METHOD: getInstructorSchedule
     * 
     * Given an instructorID, this method returns a list of courses that instructor teaches.
     * 
     * @param String instructorID
     * @return Vector<Course> instructorSchedule
     * @throws SQLException
     */
    public Vector<Course> getInstructorSchedule(String instructorID) throws SQLException {
        Vector<Course> instructorSchedule = new Vector<Course>();
        User user = getInstructor(instructorID);
        String instructor = user.name.toString();
        try {
            conn = DriverManager.getConnection(url, dbUser, dbPassword);
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM courses WHERE instructor_id = " + instructorID);
            
            while (rs.next()) {
                String courseID = rs.getString(1);
                String title = rs.getString(2);
                String major = rs.getString(3);
                Time startTime = rs.getTime(5);
                Time endTime = rs.getTime(6);
                String quarterOffered = rs.getString(7);
                int yearOffered = rs.getInt(8);

                Course course = new Course(courseID, title, major, instructor, startTime, endTime, quarterOffered, yearOffered);
                instructorSchedule.add(course);
            }   
        } 
        catch (SQLException e) {
            throw e;
        } finally {
            conn.close();
            stmt.close();
            rs.close();
        }

        return instructorSchedule;
    }
    
	/***************************************************************************************************************************
	 * METHOD: getStudentsInCourse
	 * 
	 * Given a courseID, this method will return a list of the students taking that course.
	 * 
	 * @param String courseID
	 * @return Vector<String> studentList
	 * @throws SQLException
	 */
    public Vector<User> getStudentsInCourse(String courseID) throws SQLException {
        Vector<String> studentIDs = new Vector<String>();
        Vector<User> studentList = new Vector<User>();

        try {
            conn = DriverManager.getConnection(url, dbUser, dbPassword);
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT student_id FROM studentsInCourses WHERE course_id = \"" + courseID + "\"");

            while (rs.next()) {
                studentIDs.add(String.format("%010d", rs.getInt("student_id")));
            }
            
            for (int i = 0; i < studentIDs.size(); i++) {
                rs = stmt.executeQuery("SELECT first_name, last_name FROM students WHERE student_id = " + studentIDs.elementAt(i));

                while (rs.next()) {
                	
                    studentList.add(new User(studentIDs.elementAt(i), rs.getString(1), rs.getString(2)));
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
     * METHOD: getStudent
     * 
     * Given a studentID, this method will return the student as a User object
     * 
     * @param String studentID
     * @return User user
     * @throws SQLException
     */
    public User getStudent(String studentID) throws SQLException {
        String firstName = "";
        String lastName = "";

        try {
            conn = DriverManager.getConnection(url, dbUser, dbPassword);
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT first_name, last_name FROM students WHERE student_id = " + studentID);

            while (rs.next()) {
                firstName = rs.getString(1);
                lastName += rs.getString(2);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            conn.close();
            stmt.close();
            rs.close();
        }
        
        User user = new User(studentID, firstName, lastName);

        return user;
    }
    
    /***************************************************************************************************************************
     * METHOD: getInstructor
     * 
     * Given an instructorID, this method will return the instructor as a User object.
     * 
     * @param String instructorID
     * @return User user
     * @throws SQLException
     */
    public User getInstructor(String instructorID) throws SQLException {
        String firstName = "";
        String lastName = "";

        try {
            conn = DriverManager.getConnection(url, dbUser, dbPassword);
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT first_name, last_name FROM instructors WHERE instructor_id = " + instructorID);

            while (rs.next()) {
                firstName = rs.getString(1);
                lastName += rs.getString(2);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            conn.close();
            stmt.close();
            rs.close();
        }
        
        
        User user = new User(instructorID, firstName, lastName);
        return user;
    }
    
    /***************************************************************************************************************************
     * METHOD: getCourse
     * 
     * Given an instructorID, this method will return the instructor as a User object.
     * 
     * @param String instructorID
     * @return User user
     * @throws SQLException
     */
    public Course getCourse(String courseID) throws SQLException {
        Course course = null;

        try {
            conn = DriverManager.getConnection(url, dbUser, dbPassword);
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM courses WHERE course_id = \"" + courseID + "\"");

            while (rs.next()) {
                String id = rs.getString(1);
                String title = rs.getString(2);
                String major = rs.getString(3);
                String instructor = String.format("%010d", rs.getInt(4));
                Time startTime = rs.getTime(5);
                Time endTime = rs.getTime(6);
                String quarterOffered = rs.getString(7);
                int yearOffered = rs.getInt(8);

                course = new Course(id, title, major, instructor, startTime, endTime, quarterOffered, yearOffered);
            }
            
        } catch (SQLException e) {
            throw e;
        } finally {
            conn.close();
            stmt.close();
            rs.close();
        }
        

        return course;
    }
    
    /***************************************************************************************************************************
     * METHOD: getSearchResults
     * 
     * Given an instructorID, this method will return the instructor as a User object.
     * 
     * @param String instructorID
     * @return User user
     * @throws SQLException
     */
    public Vector<Course> getSearchResults(String courseID, String courseName, String term, String year, String subject) throws SQLException {
        Vector<Course> results = new Vector<Course>();
        Vector<String> instructorIDs = new Vector<String>();
        
        String query = "SELECT * FROM courses WHERE";
        
        Boolean hasID = true;
        Boolean hasName = true;
        Boolean hasTerm = true;
        Boolean hasYear = true;
        Boolean hasSubject = true;
        
        Boolean isFirst = true;
        
        if (courseID.equals("")) hasID = false;
        if (courseName.equals("")) hasName = false;
        if (term.equals("")) hasTerm = false;
        if (year.equals("")) hasYear = false;
        if (subject.equals("")) hasSubject = false;
        
        System.out.println("CourseID: " + courseID);
        System.out.println("CourseName: " + courseName);
        System.out.println("Term: " + term);
        System.out.println("Year: " + year);
        System.out.println("Subject: " + subject);
        
        if (!hasID && !hasName && !hasTerm && !hasYear && !hasSubject) {
        	System.out.println("Cannot run an empty search");
        	return null;
        }
        
        if (hasID) {
        	System.out.println("detected an ID!");
        	if (isFirst) {
        		isFirst = false;
        		query = query + " course_id = \"" + courseID + "\"";
        	}
        	else query = query + " AND course_id = \"" + courseID + "\"";
        }
        if (hasName) {
        	System.out.println("detected a name!");
        	if (isFirst) {
        		isFirst = false;
        		query = query +  " title = \"" + courseName + "\"";
        	}
        	else query = query + " AND title = \"" + courseName + "\"";
        }
        if (hasTerm) {
        	System.out.println("detected a term!");
        	if(isFirst) {
        		isFirst = false;
        		query = query + " quarter_offered = \"" + term + "\"";
        	}
        	else query = query + " AND quarter_offered = \"" + term + "\"";
        }
        if (hasYear) {
        	System.out.println("detected a year!");
        	if(isFirst) {
        		isFirst = false;
        		query = query + " year_offered = \"" + year + "\"";
        	}
        	else query = query + " AND year_offered = \"" + year + "\"";
        }
        if (hasSubject) {
        	System.out.println("detected a subject!");
        	if(isFirst) {
        		isFirst = false;
        		query = query + " major = \"" + subject + "\"";
        	}
        	else query = query + " AND major = \"" + subject + "\"";	
        }
        
        System.out.println(query);
        
        try {
            conn = DriverManager.getConnection(url, dbUser, dbPassword);
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                String ID = rs.getString(1);
                String title = rs.getString(2);
                String major = rs.getString(3);
                String instructor = String.format("%010d", rs.getInt(4));
                Time startTime = rs.getTime(5);
                Time endTime = rs.getTime(6);
                String quarterOffered = rs.getString(7);
                int yearOffered = rs.getInt(8);

                Course course = new Course(ID, title, major, instructor, startTime, endTime, quarterOffered, yearOffered);
                results.add(course);
                instructorIDs.add(instructor);
            }
        
        for (int i = 0 ; i < instructorIDs.size(); i++) {
        	results.elementAt(i).instructor = getInstructor(instructorIDs.elementAt(i)).toString();
        }
            
        } catch (SQLException e) {
            throw e;
        } finally {
            conn.close();
            stmt.close();
            rs.close();
        }
        

        return results;
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
     * METHOD: addPastCourse
     * 
     * Given a studentID and a courseID, adds that course to that student's schedule.
     * 
     * @param String studentID
     * @param String courseID
     * @return true if the course was added, false if not
     * @throws SQLException
     */
    public Boolean addPastCourse(String studentID, Course course, String grade) throws SQLException {
        // First, validate the inputs.
        if (!validateStudent(studentID)) {
            System.out.println("invalid student id");
            return false;
        }
        if (!validateCourse(course.courseID)) {
            System.out.println("invalid course id");
            return false;
        }

        //If the inputs are valid, we can perform the add course operation.
        try {
            conn = DriverManager.getConnection(url, dbUser, dbPassword);
            pStmt = conn.prepareStatement("INSERT INTO pastCourses (course_id, student_id, grade, quarter_offered, year_offered) VALUES (?, ?, ?, ?, ?)");
            pStmt.setString(1, course.courseID);
            pStmt.setInt(2, Integer.parseInt(studentID));
            pStmt.setString(3, grade);
            pStmt.setString(4, course.quarterOffered);
            pStmt.setInt(5, course.yearOffered);
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
            rs = stmt.executeQuery("SELECT * FROM login WHERE userid = " + UserID);
            
            while (rs.next()) {
                data.userID =String.format("%010d", Integer.parseInt(rs.getString(1)));
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
