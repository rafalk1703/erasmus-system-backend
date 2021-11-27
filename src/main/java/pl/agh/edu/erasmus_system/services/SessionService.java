package pl.agh.edu.erasmus_system.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.agh.edu.erasmus_system.controllers.SessionRequestBody;
import pl.agh.edu.erasmus_system.model.ContractsCoordinator;
import pl.agh.edu.erasmus_system.model.Session;
import pl.agh.edu.erasmus_system.repositories.SessionRepository;

import java.sql.Date;
import java.util.Calendar;
import java.util.Optional;

/**
 * Class groups some methods to cope with session
 */
@Service
public class SessionService {

    @Autowired
    private SessionRepository sessionRepository;

    /**
     * Return coordinator connected with given session
     * @param code code of session
     * @return coordinator connected with given session
     */
    public ContractsCoordinator getCoordinatorOf(String code) {
        Optional<Session> sessionOptional = sessionRepository.findByCode(code);
        if (sessionOptional.isEmpty()) {
            return null;
        }

        Session session = sessionOptional.get();
        if (session.getExpiryDate().before(new Date(Calendar.getInstance().getTime().getTime()))) {
            return null;
        }

        session.extendSessionByOneDay();
        return session.getContractsCoordinator();
    }

    public ContractsCoordinator getCoordinatorOf(SessionRequestBody body) {
        return this.getCoordinatorOf(body.getSessionCode());
    }
}