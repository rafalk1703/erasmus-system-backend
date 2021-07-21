package pl.agh.edu.erasmus_system.controller.qualification.response_bodies;

import lombok.Getter;
import pl.agh.edu.erasmus_system.model.Registration;

@Getter
public class QualificationRegistrationResponseBody {
    private Long id;
    private QualificationStudentResponseBody student;
    private Integer priority;
    private Boolean isNominated;
    private Boolean isAccepted;

    public QualificationRegistrationResponseBody(Registration registration) {
        this.id =  registration.getId();
        this.student = new QualificationStudentResponseBody(registration.getStudent());
        this.priority = registration.getPriority();
        this.isNominated = registration.getIsNominated();
        this.isAccepted = registration.getIsAccepted();
    }
}