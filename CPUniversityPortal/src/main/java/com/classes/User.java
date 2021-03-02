package com.classes;

import java.io.Serializable;

/*******************************************************************************************
 * 
 * @author Hayley Carter
 *
 * This class functions like a structure. It defines the user object and all the information
 * it should contain.
 */
public class User implements Serializable {
	public String userID;
	public Name name;

	
	// Initialize a course object.
	public User(String userID, 
				  String firstName, 
				  String lastName) {
		
		this.userID = userID;
		name = new Name(firstName, lastName);
	}
	
	// simple toString method for easier debugging
	public String toString() {
		String result = "";
		result += name.first;
		result += " ";
		result += name.last;
		
		return result;
	}
	
}
