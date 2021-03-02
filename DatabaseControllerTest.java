import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DatabaseControllerTest {

	private static final String COUR11 = "COUR11";
	private databaseController dbController;
	private Connection con;
	private Statement statement;
	private String dbUser = "root";
	private String dbPassword = "Admin@123";
	private static final String url = "jdbc:mysql://localhost:3306/film";

	@Before
	public void setup() throws SQLException {
		dbController = new databaseController();
		con = DriverManager.getConnection(url, dbUser, dbPassword);
		statement = con.createStatement();
		cleanup();
		prepareDataInDb();
	}

	private void prepareDataInDb() {
		try {
			String insertIntoCourses = "insert into courses values(\"COUR11\",\"dummy\",\"major\",3333,1/1/2001,2/2/2001,\"2\",2)";
			statement.execute(insertIntoCourses);

			String insertIntoStudents = "insert into students values(454545,\"fname\",\"lname\")";
			statement.execute(insertIntoStudents);
			
			String insertIntoCourses1 = "insert into courses values(\"COUR12\",\"dummy1\",\"major1\",3334,1/1/2001,2/2/2001,\"2\",2)";
			statement.execute(insertIntoCourses1);

			String insertIntoStudents1 = "insert into students values(454546,\"fname3\",\"lname3\")";
			statement.execute(insertIntoStudents1);

			String insertIntoInstructors = "insert into instructors values(3333,\"fname1\",\"lname2\")";
			statement.execute(insertIntoInstructors);

			String insertIntoStudentCourse = "insert into studentsInCourses values(\"COUR11\",454545)";
			statement.execute(insertIntoStudentCourse);

		} catch (Exception e) {
			e.printStackTrace();
			fail("failed to init db");
		}
	}

	@Test
	public void testGetStudentCourses() {
		try {
			Vector<String> studentCourses = dbController.getStudentCourses(454545);
			assertEquals(1, studentCourses.size());
			assertEquals(COUR11, studentCourses.get(0));
		} catch (Exception e) {
			e.printStackTrace();
			fail("testGetStudentCourses failed");
		}
	}

	@Test
	public void testGetStudentSchedule() {
		try {
			Vector<String> courses = new Vector<String>();
			courses.add(COUR11);
			Vector<Course> studentSchedule = dbController.getStudentSchedule(courses);
			assertEquals(1, studentSchedule.size());
			assertEquals(COUR11, studentSchedule.get(0).courseID);
		} catch (Exception e) {
			e.printStackTrace();
			fail("testGetStudentSchedule failed");
		}
	}

	@Test
	public void testGetStudentIDsInClass() {
		try {
			Vector<Integer> studIds = dbController.getStudentIDsInClass(COUR11);
			assertEquals(1, studIds.size());
			assertEquals(Integer.valueOf(454545), studIds.get(0));
		} catch (Exception e) {
			e.printStackTrace();
			fail("testGetStudentIDsInClass failed");
		}
	}
	
	@Test
	public void testAddCourse() {
		try {
			Boolean addCourse = dbController.addCourse(454546, "COUR12");
			assertTrue(addCourse);
		} catch (Exception e) {
			e.printStackTrace();
			fail("testAddCourse failed");
		}
	}
	
	@Test
	public void testRemoveCourse() {
		try {
			Boolean addCourse = dbController.removeCourse(454546, "COUR12");
			assertTrue(addCourse);
		} catch (Exception e) {
			e.printStackTrace();
			fail("testRemoveCourse failed");
		}
	}
	
	@Test
	public void testGetInstructorName() {
		try {
			String inst = dbController.getInstructorName(3333);
			assertEquals("fname1", inst);
		} catch (Exception e) {
			e.printStackTrace();
			fail("getInstructorName failed");
		}
	}
	
	@Test
	public void testGetCoursesByInstructor() {
		try {
			Vector<Course> courses = dbController.getCoursesByInstructor(3333);
			assertEquals(1, courses.size());
			assertEquals(COUR11, courses.get(0).courseID);
		} catch (Exception e) {
			e.printStackTrace();
			fail("testGetCoursesByInstructor failed");
		}
	}

	@After
	public void cleanup() {
		try {
			String deleteFromCourse = "delete from courses where course_id=\"COUR11\"";
			statement.executeUpdate(deleteFromCourse);

			String deleteFromStudents = "delete from students where student_id=454545";
			statement.executeUpdate(deleteFromStudents);

			String deleteFromCourse1 = "delete from courses where course_id=\"COUR12\"";
			statement.executeUpdate(deleteFromCourse1);

			String deleteFromStudents1 = "delete from students where student_id=454546";
			statement.executeUpdate(deleteFromStudents1);
			
			String deleteFromInstructor = "delete from instructors where instructor_id = 3333";
			statement.executeUpdate(deleteFromInstructor);

			String deleteFromStudentCourse = "delete from studentsInCourses where student_id=454545 and course_id=\"COUR11\"";
			statement.executeUpdate(deleteFromStudentCourse);
			
			String deleteFromStudentCourse1 = "delete from studentsInCourses where student_id=454546 and course_id=\"COUR12\"";
			statement.executeUpdate(deleteFromStudentCourse1);

		} catch (Exception e) {
			e.printStackTrace();
			fail("failed to cleanup db");
		}
	}

}
