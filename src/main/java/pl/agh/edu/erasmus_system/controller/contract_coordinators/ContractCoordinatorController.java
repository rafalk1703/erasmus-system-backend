package pl.agh.edu.erasmus_system.controller.contract_coordinators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.agh.edu.erasmus_system.Utils.PasswordManagement;
import pl.agh.edu.erasmus_system.Utils.SessionGenerator;
import pl.agh.edu.erasmus_system.controller.contract_coordinators.request_bodies.LoginRequestBody;
import pl.agh.edu.erasmus_system.controller.contract_coordinators.response_bodies.ContractCoordinatorResponseBody;
import pl.agh.edu.erasmus_system.controller.contract_coordinators.response_bodies.ContractCoordinatorSingleResponseBody;
import pl.agh.edu.erasmus_system.controller.contract_coordinators.response_bodies.LoginResponseBody;
import pl.agh.edu.erasmus_system.model.Contract;
import pl.agh.edu.erasmus_system.model.ContractsCoordinator;
import pl.agh.edu.erasmus_system.model.Session;
import pl.agh.edu.erasmus_system.repository.ContractCoordinatorRepository;
import pl.agh.edu.erasmus_system.repository.ContractRepository;
import pl.agh.edu.erasmus_system.repository.SessionRepository;
import pl.agh.edu.erasmus_system.service.SessionService;

import java.util.*;

@RestController
@RequestMapping("api/")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ContractCoordinatorController {

    @Autowired
    private ContractCoordinatorRepository contractCoordinatorRepository;

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private SessionService sessionService;

    /**
     * @param requestBody- body of request with params: email and password
     * @return OK (200) when credentials are correct - with response contains sessionCode and coordinatorRole
     * UNAUTHORIZED (401) when credentials are wrong
     */
    @RequestMapping(value ="/login", method = RequestMethod.POST)
    public ResponseEntity<LoginResponseBody> login(@RequestBody LoginRequestBody requestBody) {
        String email = requestBody.getEmail();
        String password = requestBody.getPassword();

        Optional<ContractsCoordinator> coordinatorOptional = contractCoordinatorRepository.findByEmail(email);
        if(coordinatorOptional.isPresent()){
            ContractsCoordinator coordinator = coordinatorOptional.get();
            if(PasswordManagement.check(password, coordinator.getHash())){
                Session newSession = SessionGenerator.getNewSession(coordinator, sessionRepository);
                sessionRepository.save(newSession);
                return new ResponseEntity<>(new LoginResponseBody(newSession.getCode(), coordinator.getRole().toString()), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(value = "/delete/all", method = RequestMethod.GET)
    public ResponseEntity deleteAllContractCoordinators(@RequestHeader("Session-Code") String sessionCode) {

        ContractsCoordinator coordinator = sessionService.getCoordinatorOf(sessionCode);
        if (coordinator == null) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        List<ContractsCoordinator> contractsCoordinators = contractCoordinatorRepository.findAll();
        for (var contractCoordinator : contractsCoordinators) {
            contractCoordinatorRepository.delete(contractCoordinator);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/allContractCoordinatorsView", method = RequestMethod.GET)
    public ResponseEntity<ContractCoordinatorResponseBody> getAllContractCoordinators(@RequestHeader("Session-Code") String sessionCode) {

        List<ContractsCoordinator> contractsCoordinators = contractCoordinatorRepository.findAll();

        return getContractCoordinatorResponseEntity(sessionCode, contractsCoordinators);
    }

    @RequestMapping(value = "/allContractCoordinatorsView/{edition_Id}", method = RequestMethod.GET)
    public ResponseEntity<ContractCoordinatorResponseBody> getAllContractCoordinatorsByEdition(@PathVariable("edition_id") long editionId,
                                                                                               @RequestHeader("Session-Code") String sessionCode) {
        List<Contract> contractsByEdition = contractRepository.findByEdition_Id(editionId);
        Set<ContractsCoordinator> contractsCoordinators = new LinkedHashSet<>();

        for (Contract contract : contractsByEdition) {
            contractsCoordinators.add(contract.getContractsCoordinator());
        }

        return getContractCoordinatorResponseEntity(sessionCode, new LinkedList<>(contractsCoordinators));
    }

    private ResponseEntity<ContractCoordinatorResponseBody> getContractCoordinatorResponseEntity(String sessionCode,
                                                                                                 List<ContractsCoordinator> contractsCoordinators) {
        ContractsCoordinator coordinator = sessionService.getCoordinatorOf(sessionCode);
        if (coordinator == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

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