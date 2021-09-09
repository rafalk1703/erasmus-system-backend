package pl.agh.edu.erasmus_system.Utils;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Class contains some static methods to cope with things connected with passwords, such
 * like generating salt, creating hash etc.
 */
public class PasswordManagement {
    /**
     * Number of salt rounds, given as parameter to generate salt by BCrypt
     */
    private static final int saltLength = 10;

    /**
     * Generate hash and salt using BCrypt, in BCrypt format
     * @param password raw password
     * @return hash as String
     */
    public static String generateHash(String password){
        return BCrypt.hashpw(password, BCrypt.gensalt(saltLength));
    }

    /**
     * Check whether given password and hash of correct password matches
     * @param password- raw password
     * @param hash- hash of password in BCrypt format (which include salt)
     * @return True if matches, False otherwise
     */
    public static Boolean check(String password, String hash){
        return BCrypt.checkpw(password, hash);
    }
}