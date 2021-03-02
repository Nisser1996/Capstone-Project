package com.classes;

import java.io.Serializable;

/*******************************************************************************************
 * 
 * @author Hayley Carter
 *
 * This class functions like a structure. It defines the user object and all the information
 * it should contain.
 */
public class Name implements Serializable {
	public String first;
	public String last;

	
	// Initialize a course object.
	public Name(String first, String last) {
		
		this.first = first;
		this.last = last;
	}
	
	// simple toString method for easier debugging
	public String toString() {
		String result = "";
		result += first;
		result += " ";
		result += last;
		
		return result;
	}
	
}
