package pl.agh.edu.erasmus_system.controller.contracts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.agh.edu.erasmus_system.controller.contracts.response_bodies.*;
import pl.agh.edu.erasmus_system.model.Contract;
import pl.agh.edu.erasmus_system.model.ContractsCoordinator;
import pl.agh.edu.erasmus_system.repository.ContractRepository;
import pl.agh.edu.erasmus_system.service.SessionService;

import java.util.List;

@RestController
@RequestMapping("api/")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ContractController {

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private SessionService sessionService;

    @RequestMapping(value = "/allContractsView", method = RequestMethod.GET)
    public ResponseEntity<ContractResponseBody> getAllContracts(@RequestHeader("Session-Code") String sessionCode) {

        List<Contract> contracts = contractRepository.findAll();

        return getContractResponseEntity(sessionCode, contracts);
    }

    @RequestMapping(value = "/allContractsView/{edition}", method = RequestMethod.GET)
    public ResponseEntity<ContractResponseBody> getAllContractsByEdition(@PathVariable("edition") String edition,
                                                                         @RequestHeader("Session-Code") String sessionCode) {

        List<Contract> contractsByEdition = contractRepository.findByEdition_Year(edition);
        return getContractResponseEntity(sessionCode, contractsByEdition);
    }

    @RequestMapping(value = "/allContractsView/{edition}/{coordinator_code}", method = RequestMethod.GET)
    public ResponseEntity<ContractResponseBody> getAllContractsByEditionAndCoordinator(@PathVariable("edition") String edition,
                                                                                       @PathVariable("coordinator_code") String coordinatorCode,
                                                                                       @RequestHeader("Session-Code") String sessionCode) {

        List<Contract> contractsByEditionAndCoordinator = contractRepository.findByEdition_YearAndContractsCoordinator_Code(edition, coordinatorCode);
        return getContractResponseEntity(sessionCode, contractsByEditionAndCoordinator);
    }

    private ResponseEntity<ContractResponseBody> getContractResponseEntity(String sessionCode, List<Contract> contracts) {

        ContractsCoordinator coordinator = sessionService.getCoordinatorOf(sessionCode);
        if (coordinator == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        ContractResponseBody response = new ContractResponseBody();

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