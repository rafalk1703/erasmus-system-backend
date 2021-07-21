package pl.agh.edu.erasmus_system.controller.contract_coordinators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.agh.edu.erasmus_system.controller.contract_coordinators.response_bodies.ContractCoordinatorResponseBody;
import pl.agh.edu.erasmus_system.controller.contract_coordinators.response_bodies.ContractCoordinatorSingleResponseBody;
import pl.agh.edu.erasmus_system.model.Contract;
import pl.agh.edu.erasmus_system.model.ContractsCoordinator;
import pl.agh.edu.erasmus_system.repository.ContractCoordinatorRepository;
import pl.agh.edu.erasmus_system.repository.ContractRepository;

import java.util.*;

@RestController
@RequestMapping("api/")
@CrossOrigin
public class ContractCoordinatorController {

    @Autowired
    private ContractCoordinatorRepository contractCoordinatorRepository;

    @Autowired
    private ContractRepository contractRepository;

    @RequestMapping(value = "/allContractCoordinatorsView", method = RequestMethod.GET)
    public ResponseEntity<ContractCoordinatorResponseBody> getAllContractCoordinators() {

        List<ContractsCoordinator> contractsCoordinators = contractCoordinatorRepository.findAll();

        return getContractCoordinatorResponseEntity(contractsCoordinators);
    }

    @RequestMapping(value = "/allContractCoordinatorsView/{edition}", method = RequestMethod.GET)
    public ResponseEntity<ContractCoordinatorResponseBody> getAllContractCoordinatorsByEdition(@PathVariable("edition") String edition) {

        List<Contract> contractsByEdition = contractRepository.findByEdition_Year(edition);
        Set<ContractsCoordinator> contractsCoordinators = new LinkedHashSet<>();

        for (Contract contract : contractsByEdition) {
            contractsCoordinators.add(contract.getContractsCoordinator());
        }

        return getContractCoordinatorResponseEntity(new LinkedList<>(contractsCoordinators));
    }

    private ResponseEntity<ContractCoordinatorResponseBody> getContractCoordinatorResponseEntity(List<ContractsCoordinator> contractsCoordinators) {
        ContractCoordinatorResponseBody response = new ContractCoordinatorResponseBody();

        for (ContractsCoordinator contractsCoordinator : contractsCoordinators) {
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
