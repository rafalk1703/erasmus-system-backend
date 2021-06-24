package pl.agh.edu.erasmus_system.controller.contracts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.agh.edu.erasmus_system.controller.contracts.response_bodies.*;
import pl.agh.edu.erasmus_system.model.Contract;
import pl.agh.edu.erasmus_system.repository.ContractRepository;

import java.util.List;

@RestController
@RequestMapping("api/")
@CrossOrigin
public class ContractController {

    @Autowired
    private ContractRepository contractRepository;

    @RequestMapping(value = "/allContractsView", method = RequestMethod.GET)
    public ResponseEntity<ContractResponseBody> getAllContracts() {
        ContractResponseBody response = new ContractResponseBody();
        List<Contract> contracts = contractRepository.findAll();

        for (Contract contract : contracts) {
            ContractCoordinatorResponseBody contractCoordinatorResponseBody =
                    new ContractCoordinatorResponseBody(contract.getContractsCoordinator());
            ContractEditionResponseBody contractEditionResponseBody =
                    new ContractEditionResponseBody(contract.getEdition());
            ContractSingleResponseBody single =
            new ContractSingleResponseBody(
                    contract.getId(),
                    contractCoordinatorResponseBody,
                    contractEditionResponseBody,
                    contract.getErasmusCode(),
                    contract.getVacancies(),
                    contract.getDegree(),
                    contract.getStartYear(),
                    contract.getEndYear(),
                    contract.getFaculty());
            response.add(single);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }



}
