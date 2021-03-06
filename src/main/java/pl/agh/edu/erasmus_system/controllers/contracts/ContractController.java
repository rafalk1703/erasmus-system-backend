package pl.agh.edu.erasmus_system.controllers.contracts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.agh.edu.erasmus_system.controllers.contracts.response_bodies.*;
import pl.agh.edu.erasmus_system.model.Contract;
import pl.agh.edu.erasmus_system.model.ContractsCoordinator;
import pl.agh.edu.erasmus_system.model.CoordinatorRole;
import pl.agh.edu.erasmus_system.repositories.ContractRepository;
import pl.agh.edu.erasmus_system.services.SessionService;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("api/")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ContractController {

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private SessionService sessionService;

    @RequestMapping(value = "/contracts", method = RequestMethod.GET)
    public ResponseEntity<ContractResponseBody> getAllContracts(@RequestHeader("Session-Code") String sessionCode) {

        ContractsCoordinator coordinator = sessionService.getCoordinatorOf(sessionCode);
        if (coordinator == null || coordinator.getRole().equals(CoordinatorRole.CONTRACTS)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        List<Contract> contracts = contractRepository.findAll();

        return getContractResponseEntity(contracts);
    }

    @RequestMapping(value = "/contracts/{edition_id}", method = RequestMethod.GET)
    public ResponseEntity<ContractResponseBody> getAllContractsByEdition(@PathVariable("edition_id") long editionId,
                                                                         @RequestHeader("Session-Code") String sessionCode) {

        ContractsCoordinator coordinator = sessionService.getCoordinatorOf(sessionCode);
        if (coordinator == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        List<Contract> contractsByEdition;
        switch (coordinator.getRole()) {
            case DEPARTMENT:
                contractsByEdition = contractRepository.findByEdition_Id(editionId);
                break;
            case CONTRACTS:
                contractsByEdition = contractRepository.findByEditionIdAndContractsCoordinator(editionId, coordinator);
                break;
            default:
                contractsByEdition = new LinkedList<>();
        }

        return getContractResponseEntity(contractsByEdition);
    }

    @RequestMapping(value = "/contract/change-number-of-vacancies/{contract_id}", method = RequestMethod.POST)
    public ResponseEntity changeNumberOfVacancies(@PathVariable("contract_id") long contractId,
                                                  @RequestBody ContractVacanciesRequestBody requestBody) {

        ContractsCoordinator coordinator = sessionService.getCoordinatorOf(requestBody);
        if (coordinator == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }


        Contract contract = contractRepository.findById(contractId).get();
        contract.setVacancies(requestBody.getVacancies());
        contractRepository.save(contract);

        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/contracts/{edition_id}/{coordinator_code}", method = RequestMethod.GET)
    public ResponseEntity<ContractResponseBody> getAllContractsByEditionAndCoordinator(@PathVariable("edition_id") long editionId,
                                                                                       @PathVariable("coordinator_code") String coordinatorCode,
                                                                                       @RequestHeader("Session-Code") String sessionCode) {

        ContractsCoordinator coordinator = sessionService.getCoordinatorOf(sessionCode);
        if (coordinator == null || coordinator.getRole().equals(CoordinatorRole.CONTRACTS)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        List<Contract> contractsByEditionAndCoordinator = contractRepository.findByEdition_IdAndContractsCoordinator_Code(editionId, coordinatorCode);
        return getContractResponseEntity(contractsByEditionAndCoordinator);
    }

    private ResponseEntity<ContractResponseBody> getContractResponseEntity(List<Contract> contracts) {

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