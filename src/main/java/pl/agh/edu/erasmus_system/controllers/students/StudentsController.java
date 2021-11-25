package pl.agh.edu.erasmus_system.controllers.students;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.agh.edu.erasmus_system.model.ContractsCoordinator;
import pl.agh.edu.erasmus_system.model.Student;
import pl.agh.edu.erasmus_system.repositories.StudentRepository;
import pl.agh.edu.erasmus_system.services.SessionService;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("api/")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class StudentsController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private SessionService sessionService;

    @RequestMapping(value = "/students/{edition_id}", method = RequestMethod.GET)
    public ResponseEntity<List<Student>> getStudentsAllData(@PathVariable("edition_id") long editionId,
                                                            @RequestHeader("Session-Code") String sessionCode) {

        ContractsCoordinator coordinator = sessionService.getCoordinatorOf(sessionCode);
        if (coordinator == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        List<Student> students;
        switch (coordinator.getRole()) {
            case DEPARTMENT:
                students = studentRepository.findStudentsByEditionId(editionId);
                break;
            case CONTRACTS:
                students = studentRepository.findStudentsByEditionAndCoordinator(editionId, coordinator);
                break;
            default:
                students = new LinkedList<>();
        }

        return new ResponseEntity<>(students, HttpStatus.OK);
    }
}
