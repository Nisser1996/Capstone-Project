import java.io.Serializable;

/*******************************************************************************************
 * 
 * @author Hayley Carter
 *
 * This class functions like a structure. It defines the user object and all the information
 * it should contain.
 */
public class User implements Serializable {
	String userID;
	String firstName;
	String lastName;

	
	// Initialize a course object.
	public User(String userID, 
				  String firstName, 
				  String lastName) {
		
		this.userID = userID;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	// simple toString method for easier debugging
	public String toString() {
		String result = "";
		
		result += userID;
		result += ", ";
		result += firstName;
		result += ", ";
		result += lastName;
		
		return result;
	}
	
}