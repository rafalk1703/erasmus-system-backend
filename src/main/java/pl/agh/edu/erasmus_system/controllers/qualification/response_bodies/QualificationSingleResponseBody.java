package pl.agh.edu.erasmus_system.controllers.qualification.response_bodies;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * POJO class contains part of body for /qualificationView response
 */
@Getter
@AllArgsConstructor
public class QualificationSingleResponseBody {
    private Long id;
    private QualificationCoordinatorResponseBody contractsCoordinator;
    private String erasmusCode;
    private String degree;
    private Integer vacancies;
    private List<QualificationRegistrationResponseBody> registrations;
    private Long tickedStudentsAmount;
}