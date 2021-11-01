package pl.agh.edu.erasmus_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.agh.edu.erasmus_system.controller.qualification.response_bodies.QualificationStudentRegistrationsResponseBody;
import pl.agh.edu.erasmus_system.model.Contract;
import pl.agh.edu.erasmus_system.model.CoordinatorRole;
import pl.agh.edu.erasmus_system.model.Registration;
import pl.agh.edu.erasmus_system.repository.RegistrationRepository;

import java.util.*;

@Service
public class QualificationService {

    @Autowired
    private RegistrationRepository registrationRepository;

    public long countNominatedStudentsByContract(Contract contract) {
        return registrationRepository.countNominatedStudentsByContract(contract);
    }

    public long countAcceptedStudentsByContract(Contract contract) {
        return registrationRepository.countAcceptedStudentsByContract(contract);
    }

    public Map<Long,QualificationStudentRegistrationsResponseBody> determineStudentsRegistrations(CoordinatorRole coordinatorRole) {
        Map<Long,QualificationStudentRegistrationsResponseBody> studentsRegistrations = new HashMap<>();

        for (Registration registration : registrationRepository.findAll()) {
            long studentId = registration.getStudent().getId();
            if (!studentsRegistrations.containsKey(studentId)) {
                QualificationStudentRegistrationsResponseBody qualificationStudentRegistrationsResponseBody =
                        new QualificationStudentRegistrationsResponseBody();
                qualificationStudentRegistrationsResponseBody.addRegistrationId(registration.getId());
                switch (coordinatorRole) {
                    case CONTRACTS:
                        if (registration.getIsNominated()) {
                            qualificationStudentRegistrationsResponseBody.increaseTickedAmount();
                        }
                        break;
                    case DEPARTMENT:
                        if (registration.getIsAccepted()) {
                            qualificationStudentRegistrationsResponseBody.increaseTickedAmount();
                        }
                        break;
                }
                studentsRegistrations.put(studentId, qualificationStudentRegistrationsResponseBody);
            } else {
                QualificationStudentRegistrationsResponseBody studentRegistrations = studentsRegistrations.get(studentId);
                studentRegistrations.addRegistrationId(registration.getId());

                switch (coordinatorRole) {
                    case CONTRACTS:
                        if (registration.getIsNominated()) {
                            studentRegistrations.increaseTickedAmount();
                        }
                        break;
                    case DEPARTMENT:
                        if (registration.getIsAccepted()) {
                            studentRegistrations.increaseTickedAmount();
                        }
                        break;
                }
            }
        }

        return studentsRegistrations;
    }
}