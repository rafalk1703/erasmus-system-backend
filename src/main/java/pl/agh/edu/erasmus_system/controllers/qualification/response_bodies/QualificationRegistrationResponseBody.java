package pl.agh.edu.erasmus_system.controllers.qualification.response_bodies;

import lombok.Getter;

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