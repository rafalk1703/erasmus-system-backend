package pl.agh.edu.erasmus_system.controller.qualification.response_bodies;

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
    private Integer vacancies;
    private List<QualificationRegistrationResponseBody> registrations;
}