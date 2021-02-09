import java.sql.Time;


/*******************************************************************************************
 * 
 * @author Hayley Carter
 *
 * This class functions like a structure. It defines the course object and all the information
 * it should contain.
 */
public class Course {
	String courseID;
	String courseTitle;
	String courseMajor;
	String courseInstructor;
	Time startTime;
	Time endTime;
	String quarterOffered;
	int yearOffered;
	
	// Initialize a course object.
	public Course(String courseID, 
				  String courseTitle, 
				  String courseMajor, 
				  String courseInstructor, 
				  Time startTime, 
				  Time endTime, 
				  String quarterOffered,
				  int yearOffered) {
		
		this.courseID = courseID;
		this.courseTitle = courseTitle;
		this.courseMajor = courseMajor;
		this.courseInstructor = courseInstructor;
		this.startTime = startTime;
		this.endTime = endTime;
		this.quarterOffered = quarterOffered;
		this.yearOffered = yearOffered;
	}
	
	// simple toString method for easier debugging
	public String toString() {
		String result = "";
		
		result += courseID;
		result += ", ";
		result += courseTitle;
		result += ", ";
		result += courseMajor;
		result += ", ";
		result += courseInstructor;
		result += ", ";
		result += startTime.toString();
		result += ", ";
		result += endTime.toString();
		result += ", ";
		result += quarterOffered;
		result += ", ";
		result += yearOffered;
		
		return result;
	}
	
}