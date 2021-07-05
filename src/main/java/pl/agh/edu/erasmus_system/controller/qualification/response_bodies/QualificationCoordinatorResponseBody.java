package pl.agh.edu.erasmus_system.controller.qualification.response_bodies;

import lombok.Getter;
import pl.agh.edu.erasmus_system.model.ContractsCoordinator;

/**
 * POJO class to return values from /qualificationView callback
 */
@Getter
public class QualificationCoordinatorResponseBody {
    private Long id;
    private String name;
    private String surname;
    private String email;

    public QualificationCoordinatorResponseBody(ContractsCoordinator coordinator) {
        this.id = coordinator.getId();
        this.name = coordinator.getName();
        this.surname = coordinator.getSurname();
        this.email = coordinator.getEmail();
    }
}