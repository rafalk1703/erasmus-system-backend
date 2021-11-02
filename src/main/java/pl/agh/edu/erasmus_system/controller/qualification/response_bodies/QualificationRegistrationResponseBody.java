package pl.agh.edu.erasmus_system.controller.qualification.response_bodies;

import lombok.Getter;
import pl.agh.edu.erasmus_system.model.Registration;

@Getter
public class QualificationRegistrationResponseBody {
    private Long id;
    private QualificationStudentResponseBody student;
    private Integer priority;
    private Boolean registrationStatus;

    public QualificationRegistrationResponseBody(Long registrationId, QualificationStudentResponseBody student,
                                                 Integer priority, Boolean registrationStatus) {
        this.id =  registrationId;
        this.student = student;
        this.priority = priority;
        this.registrationStatus = registrationStatus;
    }
}