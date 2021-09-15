package pl.agh.edu.erasmus_system.Utils;

import org.mindrot.jbcrypt.BCrypt;

import java.util.Random;

/**
 * Class contains some static methods to cope with things connected with passwords, such
 * like generating salt, creating hash, generating password etc.
 */
public class PasswordManagement {
    /**
     * Number of salt rounds, given as parameter to generate salt by BCrypt
     */
    private static final int saltLength = 10;

    /**
     * Length of generating password
     */
    private static final int passLength = 8;

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

    /**
     * Generate random password with following conditions:
     * - length of 8 characters
     * - at least one capital-case letter
     * - at least one lower-case letter
     * - at least one number
     * - one of the following special characters !,@,#,$
     * @return password
     */
    public static String generatePassword() {
        String capitalCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";
        String specialCharacters = "!@#$";

        StringBuilder password = new StringBuilder();
        Random random = new Random();
        password.append(capitalCaseLetters.charAt(random.nextInt(capitalCaseLetters.length())))
                .append(lowerCaseLetters.charAt(random.nextInt(lowerCaseLetters.length())))
                .append(numbers.charAt(random.nextInt(numbers.length())))
                .append(specialCharacters.charAt(random.nextInt(specialCharacters.length())));

        String combinedChars = new StringBuilder()
                .append(capitalCaseLetters)
                .append(lowerCaseLetters)
                .append(numbers)
                .append(specialCharacters)
                .toString();
        for (int i=0; i<passLength-4; i++) {
            password.append(combinedChars.charAt(random.nextInt(combinedChars.length())));
        }

        return password.toString();
    }
}