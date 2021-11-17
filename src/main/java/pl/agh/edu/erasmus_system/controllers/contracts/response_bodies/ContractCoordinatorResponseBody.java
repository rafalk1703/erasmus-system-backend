package pl.agh.edu.erasmus_system.controllers.contracts.response_bodies;

import lombok.Getter;
import pl.agh.edu.erasmus_system.model.ContractsCoordinator;

@Getter
public class ContractCoordinatorResponseBody {
    private Long id;
    private String name;
    private String email;
    private String code;

    public ContractCoordinatorResponseBody(ContractsCoordinator contractsCoordinator) {
        this.id = contractsCoordinator.getId();
        this.name = contractsCoordinator.getName();
        this.email = contractsCoordinator.getEmail();
        this.code = contractsCoordinator.getCode();
    }
}
