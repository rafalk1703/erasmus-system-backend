package pl.agh.edu.erasmus_system.controller.contracts.response_bodies;

import lombok.Getter;
import pl.agh.edu.erasmus_system.model.ContractsCoordinator;

@Getter
public class ContractCoordinatorResponseBody {
    private Long id;
    private String name;
    private String surname;
    private String email;

    public ContractCoordinatorResponseBody(ContractsCoordinator contractsCoordinator) {
        this.id = contractsCoordinator.getId();
        this.name = contractsCoordinator.getName();
        this.surname = contractsCoordinator.getSurname();
        this.email = contractsCoordinator.getEmail();
    }
}
