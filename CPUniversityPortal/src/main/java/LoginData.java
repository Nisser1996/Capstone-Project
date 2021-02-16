public class LoginData {
    // 10-digit User ID
    // stored as a String because java hates zero-padding
    public String userID;
    // 128 Character Hash Value
    public String hash;
    // 16-Character Salt Value
    public byte[] salt;
    // no, you may not store the actual password anywhere

    // function to get the password salt in a printable format for SQL queries
    // make sure to prepend with 0x
    public String getSaltAsHex() {
        char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
        char[] hexChars = new char[salt.length * 2];
        for (int j = 0; j < salt.length; j++) {
            int v = salt[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }
}
