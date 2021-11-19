package pl.agh.edu.erasmus_system.controllers.contracts.response_bodies;

import java.util.ArrayList;
import java.util.List;
/**
 * POJO class contains body of response /allContractsView
 */
public class ContractResponseBody {
    private List<ContractSingleResponseBody> contracts;

    public ContractResponseBody() {
        this.contracts = new ArrayList<>();
    }

    public void add(ContractSingleResponseBody contract) {
        this.contracts.add(contract);
    }

    public List<ContractSingleResponseBody> getBody() {
        return contracts;
    }

    public void setBody(List<ContractSingleResponseBody> body) {
        this.contracts = body;
    }

}
