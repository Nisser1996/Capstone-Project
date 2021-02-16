import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.SQLException;

import static java.lang.Integer.TYPE;
import static java.lang.Integer.parseInt;

// AuthenticationManager handles all functions related to account authentication:
// logging in, changing passwords, and adding new users to the database

public class AuthenticationManager {
    databaseController dbc;

    // requires an existing database controller
    public AuthenticationManager(databaseController dbc) {
        this.dbc = dbc;
    }

    // TODO: implement better error codes for return
    public boolean login(String UserID, String password) {
        // verify user ID is correctly formatted
        int intID = parseInt(UserID);
        String reformatID = String.format("%010d", intID);
        if(!UserID.equals(reformatID)) {
            // userID is not properly formatted, may be incorrect input, may be an attempt at injection
            return false;
        }
        try {
            LoginData data = dbc.getLoginData(UserID);
            // get password hash and compare to database hash
            String attemptedHash = getSecurePassword(password, data.salt);
            if(data.hash.equals(attemptedHash)) {
                return true;
            }
        } catch (SQLException e) {
            // invalid login id
        }
        return false;
    }

    public RegistrationData registerStudent(String firstName, String lastName) {
        // generate random password
        String randPassword = generateRandomPassword();
        // salt and get hash
        byte[] salt = getSalt();
        String hash = getSecurePassword(randPassword, salt);
        // add new data to login database
        String UserID = "";
        try {
            UserID = dbc.addLoginData(hash, salt);
            // check if user was created
            if(UserID.equals(""))
                return null; // user could not be created
            // add new user to student database
            dbc.addNewStudent(UserID, firstName, lastName);

        } catch (SQLException e) {
            // TODO
        }

        return new RegistrationData(UserID, randPassword);
    }

    private static String generateRandomPassword() {
        SecureRandom random = new SecureRandom();
        String ret = "";
        // palette of viable human-typeable characters for our random passwords
        char[] palette = "abcdefghijklmnopqrstuvvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*()?.".toCharArray();
        StringBuilder password = new StringBuilder();
        // generate a 15 character password
        for(int i = 0; i < 15; i++) {
            int rand = random.nextInt(palette.length - 1);
            password.append(palette[rand]);
        }
        return password.toString();
    }

    // DO NOT ALTER
    private static String getSecurePassword(String password, byte[] salt) {

        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt);
            byte[] bytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte aByte : bytes) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    // DO NOT ALTER
    private static byte[] getSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

}
