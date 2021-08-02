package pl.agh.edu.erasmus_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.agh.edu.erasmus_system.model.Contract;
import pl.agh.edu.erasmus_system.model.Registration;
import pl.agh.edu.erasmus_system.repository.RegistrationRepository;

import java.util.*;

@Service
public class QualificationService {

    @Autowired
    private RegistrationRepository registrationRepository;

    public long countAcceptedStudentsByContract(Contract contract) {
        return registrationRepository.countAcceptedStudentsByContract(contract);
    }

    public Map<Long, ArrayList<Long>> determineStudentsRegistrations() {
        Map<Long, ArrayList<Long>> studentsRegistrations = new HashMap<>();

        for (Registration registration : registrationRepository.findAll()) {
            Long studentId = registration.getStudent().getId();
            if (!studentsRegistrations.containsKey(studentId)) {
                studentsRegistrations.put(studentId, new ArrayList<>(Arrays.asList(registration.getId())));
            } else {
                studentsRegistrations.get(studentId).add(registration.getId());
            }
        }

        return studentsRegistrations;
    }
}