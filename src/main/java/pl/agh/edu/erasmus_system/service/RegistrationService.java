package pl.agh.edu.erasmus_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.agh.edu.erasmus_system.model.Registration;
import pl.agh.edu.erasmus_system.model.Student;
import pl.agh.edu.erasmus_system.repository.RegistrationRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RegistrationService {

    @Autowired
    private RegistrationRepository registrationRepository;

    public boolean checkIfEachStudentIsAcceptedForMax1Contract(boolean isWIEiT, long editionId) {
        List<Student> acceptedStudents;
        if (isWIEiT) {
            acceptedStudents = registrationRepository
                    .findAllByContract_Edition_IdAndIsAccepted(editionId)
                    .stream().filter(registration -> registration.getStudent().getDepartment().toUpperCase().equals("WIEIT"))
                    .map(Registration::getStudent)
                    .collect(Collectors.toList());
        }
        else {
            acceptedStudents = registrationRepository
                    .findAllByContract_Edition_IdAndIsAccepted(editionId)
                    .stream().filter(registration -> !registration.getStudent().getDepartment().toUpperCase().equals("WIEIT"))
                    .map(Registration::getStudent)
                    .collect(Collectors.toList());
        }
        return acceptedStudents.stream().distinct().count() == acceptedStudents.stream().count();
    }
}
