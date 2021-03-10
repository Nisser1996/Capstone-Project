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
 * @author Hayley Carter
 *
 * Database Controller class. The database queries and connections are all handled here.
 *
 */

public class databaseController {
    Connection con;
    Statement statement;
    PreparedStatement pStatement;
    ResultSet rs;

    // We are currently using a server hosted by Ryan for our database.
    private String dbUser = "hayley";
    private String dbPassword = "hayley";
    private String url = "jdbc:mysql://cscapstone.ddns.net:3306/sys";

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

        } catch (SQLException e) {
            throw e;
        } finally {
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

        } catch (SQLException e) {
            throw e;
        } finally {

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
        } catch (SQLException e) {
            throw e;
        } finally {
            con.close();
            statement.close();
            rs.close();
        }

        return studentIDs;
    }

    // Gets a list of students names in a given class based on their ids
    public Vector<String> getStudentsInClass(Vector<Integer> studentIDs) throws SQLException {
        Vector<String> studentList = new Vector<String>();

        try {
            con = DriverManager.getConnection(url, dbUser, dbPassword);
            statement = con.createStatement();

            for (int i = 0; i < studentIDs.size(); i++) {
                rs = statement.executeQuery("SELECT student_name FROM students WHERE student_id = " + studentIDs.elementAt(i));

                while (rs.next()) {
                    studentList.add(rs.getString(1));
                }
            }
        } catch (SQLException e) {
            throw e;
        } finally {
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
        } catch (SQLException e) {
            throw e;
        } finally {
            con.close();
            pStatement.close();
            rs.close();
        }

        return true;
    }

    public Boolean removeCourse(int studentID, String courseID) throws SQLException {
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
        } catch (SQLException e) {
            throw e;
        } finally {
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
            rs = statement.executeQuery("SELECT student_name FROM students WHERE student_id = " + studentID);

            if (!rs.next()) return false;
        } catch (SQLException e) {
            throw e;
        } finally {
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
            rs = statement.executeQuery("SELECT course_title FROM courses WHERE course_id = \"" + courseID + "\"");

            if (!rs.next()) return false;
        } catch (SQLException e) {
            throw e;
        } finally {
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
            rs = statement.executeQuery("SELECT instructor_name FROM instructors WHERE instructor_id = " + instructorID);

            while (rs.next()) {
                result = rs.getString(1);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            con.close();
            statement.close();
            rs.close();
        }

        return result;
    }

    public Vector<Course> getCoursesByInstructor(String instructorName) throws SQLException {
        Vector<Course> instructorSchedule = new Vector<Course>();

        try {
            con = DriverManager.getConnection(url, dbUser, dbPassword);
            statement = con.createStatement();
            rs = statement.executeQuery("SELECT * FROM courses WHERE course_instructor = \"" + instructorName + "\"");

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
        } catch (SQLException e) {
            throw e;
        } finally {
            con.close();
            statement.close();
            rs.close();
        }

        return instructorSchedule;

    }

    public LoginData getLoginData(String UserID) throws SQLException {
        LoginData data = new LoginData();
        try {
            con = DriverManager.getConnection(url, dbUser, dbPassword);
            statement = con.createStatement();
            rs = statement.executeQuery("SELECT * FROM login WHERE userid = \"" + UserID + "\"");

            while (rs.next()) {
                int intID = rs.getInt(1);
                data.userID = String.format("%010d", intID);
                data.hash = rs.getString(2);
                data.salt = rs.getBytes(3);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            con.close();
            statement.close();
            rs.close();
        }
        if (!data.userID.equals(UserID)) {
            // User recieved an incorrect user record, abort and return null
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
            con = DriverManager.getConnection(url, dbUser, dbPassword);
            statement = con.createStatement();
            int ret = statement.executeUpdate("INSERT INTO login (hash, salt) values (\"" + hash + "\", 0x" + data.getSaltAsHex() + ")");
            if (ret != 1)
                return null;
            rs = statement.executeQuery("SELECT userid FROM login WHERE hash=\"" + hash + "\" AND salt = 0x" + data.getSaltAsHex() + "");
            while (rs.next()) {
                data.userID = String.format("%05d", rs.getInt(1));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            con.close();
            statement.close();
            rs.close();
        }
        return data.userID;
    }

    public boolean addNewStudent(String studentID, String firstName, String lastName) throws SQLException {
        try {
            con = DriverManager.getConnection(url, dbUser, dbPassword);
            statement = con.createStatement();
            // check if the user exists in the login field
            rs = statement.executeQuery("SELECT COUNT(1) FROM login WHERE userid=\"" + studentID + "\"");
            boolean userExists = false;
            while (rs.next()) {
                userExists = true;
            }
            if (!userExists)
                return false;
            // user exists, insert into student table with "undeclared" as major
            int ret = statement.executeUpdate("INSERT INTO students (student_id, first_name, last_name, major) " +
                    "values (\"" + studentID + "\", \"" + firstName + "\", \"" + lastName + "\", \"Undeclared\")");
            if (ret != 1)
                return false;

        } catch (SQLException e) {
            throw e;
        } finally {
            con.close();
            statement.close();
            rs.close();
        }
        return true;
    }

    public boolean isValidFaculty(String facultyID) throws SQLException {
        try {
            con = DriverManager.getConnection(url, dbUser, dbPassword);
            statement = con.createStatement();
            // check if the user exists in the login field
            rs = statement.executeQuery("SELECT COUNT(1) FROM faculty WHERE id=\"" + facultyID + "\"");
            boolean userExists = false;
            while (rs.next()) {
                userExists = true;
            }
            if (!userExists)
                return false;

        } catch (SQLException e) {
            throw e;
        } finally {
            con.close();
            statement.close();
            rs.close();
        }
        return true;
    }

    public boolean changePassword(String userID, String oldPassword, String newPassword) throws SQLException {
        int ret = 0;
        AuthenticationManager authman = new AuthenticationManager(this);
        if (!authman.login(userID, oldPassword)) {
            return false;
        }
        try {
            con = DriverManager.getConnection(url, dbUser, dbPassword);
            String updateString =
                    "UPDATE login SET hash=?, salt=? WHERE userid=?";
            PreparedStatement updatePassword = con.prepareStatement(updateString);
            LoginData data = new LoginData();
            data.userID = userID;
            data.salt = AuthenticationManager.getSalt();
            data.hash = AuthenticationManager.getSecurePassword(newPassword, data.salt);
            updatePassword.setString(1, data.hash);
            updatePassword.setBytes(2, data.salt);
            updatePassword.setString(3, data.userID);
            ret = updatePassword.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            con.close();
            statement.close();
            rs.close();
        }
        return (ret > 0);
    }

    public boolean changePasswordOverride(String facultyID, String userID, String newPassword) throws SQLException {
        int ret = 0;
        if (!isValidFaculty(facultyID))
            return false;
        try {
            con = DriverManager.getConnection(url, dbUser, dbPassword);
            String updateString =
                    "UPDATE login SET hash=?, salt=? WHERE userid=?";
            PreparedStatement updatePassword = con.prepareStatement(updateString);
            LoginData data = new LoginData();
            data.userID = userID;
            data.salt = AuthenticationManager.getSalt();
            data.hash = AuthenticationManager.getSecurePassword(newPassword, data.salt);
            updatePassword.setString(1, data.hash);
            updatePassword.setBytes(2, data.salt);
            updatePassword.setString(3, data.userID);
            ret = updatePassword.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            con.close();
            statement.close();
            rs.close();
        }
        return (ret > 0);
    }

}
