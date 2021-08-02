package pl.agh.edu.erasmus_system.controller.qualification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.agh.edu.erasmus_system.controller.qualification.response_bodies.*;
import pl.agh.edu.erasmus_system.model.Contract;
import pl.agh.edu.erasmus_system.model.Registration;
import pl.agh.edu.erasmus_system.repository.ContractRepository;
import pl.agh.edu.erasmus_system.repository.RegistrationRepository;
import pl.agh.edu.erasmus_system.service.QualificationService;

import java.util.ArrayList;
import java.util.List;

/**
 * Class contains endpoints for qualification view
 */
@RestController
@RequestMapping("api/")
@CrossOrigin
public class QualificationController {

    @Autowired
    private QualificationService qualificationService;

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private RegistrationRepository registrationRepository;

    /**
     * Get content of qualification view table.
     *
     * @return OK(200) with contracts details in response body
     */
    @RequestMapping(value = "/qualificationView", method = RequestMethod.GET)
    public ResponseEntity<QualificationResponseBody> getContracts() {
        QualificationResponseBody response = new QualificationResponseBody();
        List<Contract> contracts = contractRepository.findAll();
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
                    contract.getVacancies(),
                    registrationResponseBody,
                    acceptedStudentsAmount);
            response.addContract(single);
        }

        response.addStudentsRegistrations(qualificationService.determineStudentsRegistrations());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}