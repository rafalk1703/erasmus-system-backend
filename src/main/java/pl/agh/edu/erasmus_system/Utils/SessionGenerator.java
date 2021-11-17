package pl.agh.edu.erasmus_system.Utils;

import pl.agh.edu.erasmus_system.model.Session;
import pl.agh.edu.erasmus_system.model.ContractsCoordinator;
import pl.agh.edu.erasmus_system.repositories.SessionRepository;

import java.security.SecureRandom;
import java.sql.Date;
import java.util.Calendar;

/**
 * Class contains function to generate unique session token
 */
public class SessionGenerator {

    /**
     * Return Session object with unique code in database
     * Expiry date is set on next day, object is not saved in database
     * @param coordinator - ContractsCoordinator object for who session is created
     * @param repository - repository with sessions
     * @return - Session object, ready to save in database
     */
    public static Session getNewSession(ContractsCoordinator coordinator, SessionRepository repository) {
        Date expiryDate = getExpiryDate();
        String code = getUniqueCode(repository);
        return new Session(coordinator, expiryDate, code);
    }

    /**
     * Return Date object set on next day
     * @return Date object
     */
    private static Date getExpiryDate() {
        Calendar nextDay = Calendar.getInstance();
        nextDay.add(Calendar.DAY_OF_MONTH, 1);
        return new Date(nextDay.getTime().getTime());
    }

    /** Return unique session code in database
     * @param repository repository with Sessions
     * @return code as String
     */
    private static String getUniqueCode(SessionRepository repository) {
        String code = generateCode();
        while (repository.findByCode(code).isPresent()) {
            code = generateCode();
        }
        return code;
    }

    /**
     * Generate pseudo-random 40 digits code
     * @return code as String
     */
    private static String generateCode() {
        SecureRandom random = new SecureRandom();
        StringBuilder builder = new StringBuilder();
        for (int i=0; i<40; i++) {
           builder.append(random.nextInt(10));
        }
        return builder.toString();
    }
}