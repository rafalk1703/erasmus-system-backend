package pl.agh.edu.erasmus_system.controller.contract_coordinators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.agh.edu.erasmus_system.controller.contract_coordinators.response_bodies.ContractCoordinatorResponseBody;
import pl.agh.edu.erasmus_system.controller.contract_coordinators.response_bodies.ContractCoordinatorSingleResponseBody;
import pl.agh.edu.erasmus_system.model.Contract;
import pl.agh.edu.erasmus_system.model.ContractsCoordinator;
import pl.agh.edu.erasmus_system.repository.ContractCoordinatorRepository;

import java.util.*;

@RestController
@RequestMapping("api/")
@CrossOrigin
public class ContractCoordinatorController {

    @Autowired
    private ContractCoordinatorRepository contractCoordinatorRepository;

    @RequestMapping(value = "/allContractCoordinatorsView", method = RequestMethod.GET)
    public ResponseEntity<ContractCoordinatorResponseBody> getAllContractCoordinators() {
        ContractCoordinatorResponseBody response = new ContractCoordinatorResponseBody();
        List<ContractsCoordinator> contracts = contractCoordinatorRepository.findAll();
        for (ContractsCoordinator contractsCoordinator : contracts) {
            System.out.println(contractsCoordinator.getCode());
            ContractCoordinatorSingleResponseBody single =
                    new ContractCoordinatorSingleResponseBody(
                            contractsCoordinator.getId(),
                            contractsCoordinator.getName(),
                            contractsCoordinator.getEmail(),
                            contractsCoordinator.getCode()
                    );
            response.add(single);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
