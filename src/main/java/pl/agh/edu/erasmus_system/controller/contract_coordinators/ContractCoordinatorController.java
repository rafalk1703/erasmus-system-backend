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
import pl.agh.edu.erasmus_system.model.*;
import pl.agh.edu.erasmus_system.repository.ContractCoordinatorRepository;
import pl.agh.edu.erasmus_system.repository.ContractRepository;
import pl.agh.edu.erasmus_system.repository.RegistrationRepository;
import pl.agh.edu.erasmus_system.repository.SessionRepository;
import pl.agh.edu.erasmus_system.service.EmailSender;
import pl.agh.edu.erasmus_system.service.QualificationService;
import pl.agh.edu.erasmus_system.service.SessionService;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

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
    private RegistrationRepository registrationRepository;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private QualificationService qualificationService;

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
        if (coordinator == null || !coordinator.getRole().equals(CoordinatorRole.DEPARTMENT)) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        List<ContractsCoordinator> contractsCoordinators = contractCoordinatorRepository.findAll();
        for (var contractCoordinator : contractsCoordinators) {
            contractCoordinatorRepository.delete(contractCoordinator);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/acceptContracts", method = RequestMethod.GET)
    public ResponseEntity coordinatorAcceptsContracts(@RequestHeader("Session-Code") String sessionCode) {

        ContractsCoordinator coordinator = sessionService.getCoordinatorOf(sessionCode);
        coordinator.setIfAccepted(true);
        contractCoordinatorRepository.save(coordinator);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/notify/{coordinator_id}", method = RequestMethod.GET)
    public ResponseEntity notifyCoordinator(@PathVariable("coordinator_id") long coordinatorId,
                                                      @RequestHeader("Session-Code") String sessionCode) {

        ContractsCoordinator coordinator = sessionService.getCoordinatorOf(sessionCode);

        if (coordinator == null || !coordinator.getRole().equals(CoordinatorRole.DEPARTMENT)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }


        if (contractCoordinatorRepository.findById(coordinatorId).isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        ContractsCoordinator contractsCoordinator = contractCoordinatorRepository.findById(coordinatorId).get();

        String contractsCoordinatorEmail = contractsCoordinator.getEmail();
        EmailSender sender = EmailSender.getSender();
        String emailMessage = new StringBuilder()
                .append("Witamy w Erasmus System! ")
                .append(System.lineSeparator())
                .append("Przypominamy o nominowaniu studentów w systemie Erasmus")
                .toString();
//                    sender.sendEmailTo("dkulma@student.agh.edu.pl", "Powiadomienie z Erasmus System", emailMessage); //TODO Change email-to
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/notifyAll/{edition_id}", method = RequestMethod.GET)
    public ResponseEntity notifyAllCoordinators(@PathVariable("edition_id") long editionId,
                                                @RequestHeader("Session-Code") String sessionCode) {

        ContractsCoordinator coordinator = sessionService.getCoordinatorOf(sessionCode);

        if (coordinator == null || !coordinator.getRole().equals(CoordinatorRole.DEPARTMENT)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        List<Contract> contractsByEdition = contractRepository.findByEdition_Id(editionId);
        Set<ContractsCoordinator> contractsCoordinators = new LinkedHashSet<>();

        for (Contract contract : contractsByEdition) {
            contractsCoordinators.add(contract.getContractsCoordinator());
        }

        List<ContractsCoordinator> noAcceptedCoordinators = contractsCoordinators.stream()
                .filter(contractsCoordinator -> contractsCoordinator.getIfAccepted().equals(false))
                .collect(Collectors.toList());

        for (ContractsCoordinator contractsCoordinator : noAcceptedCoordinators) {
            String contractsCoordinatorEmail = contractsCoordinator.getEmail();
            EmailSender sender = EmailSender.getSender();
            String emailMessage = new StringBuilder()
                    .append("Witamy w Erasmus System! ")
                    .append(System.lineSeparator())
                    .append("Przypominamy o nominowaniu studentów w systemie Erasmus")
                    .toString();
//                    sender.sendEmailTo("dkulma@student.agh.edu.pl", "Powiadomienie z Erasmus System", emailMessage); //TODO Change email-to
        }


        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/allContractCoordinatorsView", method = RequestMethod.GET)
    public ResponseEntity<ContractCoordinatorResponseBody> getAllContractCoordinators(@RequestHeader("Session-Code") String sessionCode) {

        List<ContractsCoordinator> contractsCoordinators = contractCoordinatorRepository.findAll();

        return getContractCoordinatorResponseEntity(sessionCode, contractsCoordinators);
    }

    @RequestMapping(value = "/allContractCoordinatorsView/{edition_id}", method = RequestMethod.GET)
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
        if (coordinator == null || !coordinator.getRole().equals(CoordinatorRole.DEPARTMENT)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        ContractCoordinatorResponseBody response = new ContractCoordinatorResponseBody();

        for (ContractsCoordinator contractsCoordinator : contractsCoordinators) {
            ContractCoordinatorSingleResponseBody single =
                    new ContractCoordinatorSingleResponseBody(
                            contractsCoordinator.getId(),
                            contractsCoordinator.getName(),
                            contractsCoordinator.getEmail(),
                            contractsCoordinator.getCode(),
                            contractsCoordinator.getIfAccepted()
                    );
            response.add(single);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/ifAccepted", method = RequestMethod.GET)
    public ResponseEntity<Boolean> ifAccepted(@RequestHeader("Session-Code") String sessionCode) {

        ContractsCoordinator coordinator = sessionService.getCoordinatorOf(sessionCode);

        return new ResponseEntity<>(coordinator.getIfAccepted(), HttpStatus.OK);
    }

    @RequestMapping(value = "/ifHasContracts/{edition_id}", method = RequestMethod.GET)
    public ResponseEntity<Boolean> ifHasContracts(@PathVariable("edition_id") long editionId,
                                                  @RequestHeader("Session-Code") String sessionCode) {

        ContractsCoordinator coordinator = sessionService.getCoordinatorOf(sessionCode);

        List<Contract> contractsByEdition = contractRepository.findByEdition_Id(editionId);

        Set<ContractsCoordinator> contractsCoordinators = new LinkedHashSet<>();

        for (Contract contract : contractsByEdition) {
            contractsCoordinators.add(contract.getContractsCoordinator());
        }

        if (contractsCoordinators.stream().map(ContractsCoordinator::getCode).noneMatch(code -> code.equals(coordinator.getCode()))) {
            return new ResponseEntity<>(false, HttpStatus.OK);
        }

        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @RequestMapping(value = "/ifAllContractsQualified/{edition_id}", method = RequestMethod.GET)
    public ResponseEntity<Boolean> ifAllContractsQualified(@PathVariable("edition_id") long editionId,
                                                           @RequestHeader("Session-Code") String sessionCode) {

        ContractsCoordinator coordinator = sessionService.getCoordinatorOf(sessionCode);
        if (coordinator == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        List<Contract> contracts = new LinkedList<>();
        switch (coordinator.getRole()) {
            case CONTRACTS:
                contracts = contractRepository.findByEditionIdAndContractsCoordinator(editionId, coordinator);
                break;
            case DEPARTMENT:
                contracts = contractRepository.findByEdition_Id(editionId);
        }

        for (Contract contract: contracts) {
            List<Registration> registrations = new LinkedList<>();
            switch (coordinator.getRole()) {
                case CONTRACTS:
                    registrations = registrationRepository.findAllByContractAndIsNominated(contract);
                    break;
                case DEPARTMENT:
                    registrations = registrationRepository.findAllByContractAndIsAccepted(contract);
            }
            if (registrations.size() < contract.getVacancies()) {
                for (Registration registration: registrations) {
                    Boolean registrationStatus = false;
                    switch (coordinator.getRole()) {
                        case CONTRACTS:
                            registrationStatus = registration.getIsNominated();
                            break;
                        case DEPARTMENT:
                            registrationStatus = registration.getIsAccepted();
                    }
                    if (!registrationStatus &&
                            qualificationService.determineStudentsRegistrations(editionId, coordinator).get(registration.getStudent().getId()).getTickedAmount() < 1) {
                        return new ResponseEntity<>(false, HttpStatus.OK);
                    }
                }
            }
        }
        return new ResponseEntity<>(true, HttpStatus.OK);

    }

}