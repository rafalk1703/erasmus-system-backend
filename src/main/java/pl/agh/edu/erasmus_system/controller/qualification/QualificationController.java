package pl.agh.edu.erasmus_system.controller.qualification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.agh.edu.erasmus_system.controller.qualification.request_bodies.QualificationSaveRequestBody;
import pl.agh.edu.erasmus_system.controller.qualification.response_bodies.*;
import pl.agh.edu.erasmus_system.model.Contract;
import pl.agh.edu.erasmus_system.model.ContractsCoordinator;
import pl.agh.edu.erasmus_system.model.Registration;
import pl.agh.edu.erasmus_system.repository.ContractRepository;
import pl.agh.edu.erasmus_system.repository.RegistrationRepository;
import pl.agh.edu.erasmus_system.service.QualificationService;
import pl.agh.edu.erasmus_system.service.SessionService;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Class contains endpoints for qualification view
 */
@RestController
@RequestMapping("api/qualification")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class QualificationController {

    @Autowired
    private QualificationService qualificationService;

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private RegistrationRepository registrationRepository;

    @Autowired
    private SessionService sessionService;

    /**
     * Get content of qualification view table
     * @return OK(200) with contracts details in response body
     * UNAUTHORIZED(401) if session expired
     */
    @RequestMapping(value = "/editionView/{edition_id}", method = RequestMethod.GET)
    public ResponseEntity<QualificationResponseBody> getContracts(@PathVariable("edition_id") long editionId,
                                                                  @RequestHeader("Session-Code") String sessionCode) {

        ContractsCoordinator coordinator = sessionService.getCoordinatorOf(sessionCode);
        if (coordinator == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        QualificationResponseBody response = new QualificationResponseBody();

        List<Contract> contracts;
        switch (coordinator.getRole()) {
            case DEPARTMENT:
                contracts = contractRepository.findByEdition_Id(editionId);
                break;
            case CONTRACTS:
                contracts = contractRepository.findByEditionIdAndContractsCoordinator(editionId, coordinator);
                break;
            default:
                contracts = new LinkedList<>();
        }

        for (Contract contract : contracts) {
            QualificationCoordinatorResponseBody coordinatorResponseBody =
                    new QualificationCoordinatorResponseBody(contract.getContractsCoordinator());

            List<QualificationRegistrationResponseBody> registrationResponseBody = new ArrayList<>();
            for (Registration registration : registrationRepository.getALLRegistrationsByContract(contract)) {
                registrationResponseBody.add(new QualificationRegistrationResponseBody(registration));
            }

            long acceptedStudentsAmount = qualificationService.countAcceptedStudentsByContract(contract);

            QualificationSingleResponseBody single = new QualificationSingleResponseBody(
                    contract.getId(),
                    coordinatorResponseBody,
                    contract.getErasmusCode(),
                    contract.getDegree(),
                    contract.getVacancies(),
                    registrationResponseBody,
                    acceptedStudentsAmount);
            response.addContract(single);
        }

        response.setStudentsRegistrations(qualificationService.determineStudentsRegistrations());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity saveQualification(@RequestBody QualificationSaveRequestBody requestBody) {

        ContractsCoordinator coordinator = sessionService.getCoordinatorOf(requestBody);
        if (coordinator == null) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        requestBody.getRegistrations().forEach(
               registration -> {
                    Registration registrationToUpdate = registrationRepository.findById(registration.getRegistrationId()).get();
                    registrationToUpdate.setIsAccepted(registration.getAcceptanceStatus());
                    registrationRepository.save(registrationToUpdate);
               }
        );
        return new ResponseEntity(HttpStatus.OK);
    }
}