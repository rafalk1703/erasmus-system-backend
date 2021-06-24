package pl.agh.edu.erasmus_system.controller.contract_coordinators.response_bodies;

import java.util.ArrayList;
import java.util.List;

/**
 * POJO class contains body of response /allContractCoordinatorsView
 */
public class ContractCoordinatorResponseBody {
    private List<ContractCoordinatorSingleResponseBody> contractCoordinators;

    public ContractCoordinatorResponseBody() {
        this.contractCoordinators = new ArrayList<>();
    }

    public void add(ContractCoordinatorSingleResponseBody contractCoordinators) {
        this.contractCoordinators.add(contractCoordinators);
    }

    public List<ContractCoordinatorSingleResponseBody> getBody() {
        return contractCoordinators;
    }

    public void setBody(List<ContractCoordinatorSingleResponseBody> body) {
        this.contractCoordinators = body;
    }

}
