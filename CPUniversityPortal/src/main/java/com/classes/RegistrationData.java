package com.classes;
public class RegistrationData {
    // 10-digit User ID
    // stored as a String because java hates zero-padding
    public String userID;
    // temporary password that is given to the new user
    public String tempPW;

    public RegistrationData(String userID, String tempPW) {
        this.userID = userID;
        this.tempPW = tempPW;
    }
}
