package pl.agh.edu.erasmus_system.controller.contract_coordinators.response_bodies;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
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
