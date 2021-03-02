package com.classes;
import java.io.Serializable;
import java.sql.Time;


/*******************************************************************************************
 * 
 * @author Hayley Carter
 *
 * This class functions like a structure. It defines the course object and all the information
 * it should contain.
 */
public class Course implements Serializable {
	public String courseID;
	public String title;
	public String major;
	public String instructor;
	public Time startTime;
	public Time endTime;
	public String quarterOffered;
	public int yearOffered;
	
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
		this.title = courseTitle;
		this.major = courseMajor;
		this.instructor = courseInstructor;
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
		result += title;
		result += ", ";
		result += major;
		result += ", ";
		result += instructor;
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