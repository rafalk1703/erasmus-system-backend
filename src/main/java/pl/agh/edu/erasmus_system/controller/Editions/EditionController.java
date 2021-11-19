package pl.agh.edu.erasmus_system.controller.Editions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.agh.edu.erasmus_system.Utils.FileUtils;
import pl.agh.edu.erasmus_system.controller.Editions.response_bodies.EditionStatisticsResponseBody;
import pl.agh.edu.erasmus_system.controller.Editions.response_bodies.IfCanDownloadEditionResponseBody;
import pl.agh.edu.erasmus_system.controller.Editions.response_bodies.IfCanDownloadEditionSingleResponseBody;
import pl.agh.edu.erasmus_system.model.*;
import pl.agh.edu.erasmus_system.repository.ContractCoordinatorRepository;
import pl.agh.edu.erasmus_system.repository.ContractRepository;
import pl.agh.edu.erasmus_system.repository.EditionRepository;
import pl.agh.edu.erasmus_system.repository.RegistrationRepository;
import pl.agh.edu.erasmus_system.service.ReadCSVFileService;
import pl.agh.edu.erasmus_system.service.RegistrationService;
import pl.agh.edu.erasmus_system.service.SessionService;

import java.io.File;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("api/edition")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class EditionController {

    @Autowired
    private ReadCSVFileService readCSVFileService;

    @Autowired
    private EditionRepository editionRepository;

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private ContractCoordinatorRepository contractCoordinatorRepository;

    @Autowired
    private RegistrationRepository registrationRepository;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private RegistrationService registrationService;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<List<Edition>> getEditions(@RequestHeader("Session-Code") String sessionCode) {

        ContractsCoordinator coordinator = sessionService.getCoordinatorOf(sessionCode);
        if (coordinator == null || !coordinator.getRole().equals(CoordinatorRole.DEPARTMENT)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(editionRepository.findAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "/delete/{edition_id}", method = RequestMethod.GET)
    public ResponseEntity deleteEdition(@PathVariable("edition_id") long editionId,
                                        @RequestHeader("Session-Code") String sessionCode) {

        ContractsCoordinator coordinator = sessionService.getCoordinatorOf(sessionCode);
        if (coordinator == null || !coordinator.getRole().equals(CoordinatorRole.DEPARTMENT)) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        Edition editionToDelete = editionRepository.findById(editionId).get();
        List<Contract> contractsToDelete = contractRepository.findByEdition_Id(editionId);

        Set<ContractsCoordinator> contractsCoordinators = new LinkedHashSet<>();

        for (Contract contract : contractsToDelete) {
            contractsCoordinators.add(contract.getContractsCoordinator());
        }

        for (ContractsCoordinator contractsCoordinator : contractsCoordinators) {
            contractsCoordinator.setIfAccepted(false);
            contractCoordinatorRepository.save(contractsCoordinator);
        }

        ContractsCoordinator departmentCoordinator = contractCoordinatorRepository.findByRole(CoordinatorRole.DEPARTMENT).get();
        departmentCoordinator.setIfAccepted(false);
        contractCoordinatorRepository.save(departmentCoordinator);

        List<Registration> registrationsToDelete = new LinkedList<>();

        for (Contract contract : contractsToDelete)
            registrationsToDelete.addAll(registrationRepository.findByContract(contract));

        for (Registration registration : registrationsToDelete)
            registrationRepository.delete(registration);

        for (Contract contract : contractsToDelete)
            contractRepository.delete(contract);

        editionRepository.delete(editionToDelete);

        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/active", method = RequestMethod.GET)
    public ResponseEntity<Edition> getActiveEditions(@RequestHeader("Session-Code") String sessionCode) {

        ContractsCoordinator coordinator = sessionService.getCoordinatorOf(sessionCode);
        if (coordinator == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        if (editionRepository.findByIsActiveIsTrue().size() == 0)
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(editionRepository.findByIsActiveIsTrue().get(0), HttpStatus.OK);
    }

    @RequestMapping(value = "/isActive/{edition_id}", method = RequestMethod.GET)
    public ResponseEntity<Boolean> checkIfIsActiveEdition(@PathVariable("edition_id") long editionId,
                                                          @RequestHeader("Session-Code") String sessionCode) {

        ContractsCoordinator coordinator = sessionService.getCoordinatorOf(sessionCode);
        if (coordinator == null || !coordinator.getRole().equals(CoordinatorRole.DEPARTMENT)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(editionRepository.findById(editionId).get().getIsActive(), HttpStatus.OK);
    }

    @RequestMapping(value = "/statistics/{edition_id}", method = RequestMethod.GET)
    public ResponseEntity<EditionStatisticsResponseBody> editionStatistics(@PathVariable("edition_id") long editionId,
                                                                           @RequestHeader("Session-Code") String sessionCode) {

        ContractsCoordinator coordinator = sessionService.getCoordinatorOf(sessionCode);
        if (coordinator == null || !coordinator.getRole().equals(CoordinatorRole.DEPARTMENT)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Edition edition = editionRepository.findById(editionId).get();
        String editionYear = edition.getYear();
        List<Contract> contracts = contractRepository.findByEdition_Id(editionId);
        int numberOfRegistrations = 0;
        int numberOfContracts1Degree = 0;
        int numberOfContracts2Degree = 0;
        int numberOfContracts3Degree = 0;
        int numberOfRegistrations1Degree = 0;
        int numberOfRegistrations2Degree = 0;
        int numberOfRegistrations3Degree = 0;

        for (Contract contract : contracts) {

            if (contract.getDegree().equals("Ist") && contract.getVacancies() > 0)
                numberOfContracts1Degree++;
            if (contract.getDegree().equals("IIst") && contract.getVacancies() > 0)
                numberOfContracts2Degree++;
            if (contract.getDegree().equals("IIIst") && contract.getVacancies() > 0)
                numberOfContracts3Degree++;

            for (Registration registration : registrationRepository.findByContract(contract)) {
                numberOfRegistrations++;
                if (contract.getDegree().equals("Ist") && contract.getVacancies() > 0)
                    numberOfRegistrations1Degree++;
                if (contract.getDegree().equals("IIst") && contract.getVacancies() > 0)
                    numberOfRegistrations2Degree++;
                if (contract.getDegree().equals("IIIst") && contract.getVacancies() > 0)
                    numberOfRegistrations3Degree++;
            }
        }

        EditionStatisticsResponseBody response = new EditionStatisticsResponseBody(editionId, editionYear, contracts.size(), numberOfRegistrations, numberOfContracts1Degree,
                numberOfContracts2Degree, numberOfContracts3Degree, numberOfRegistrations1Degree, numberOfRegistrations2Degree,
                numberOfRegistrations3Degree);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/deactivate/{edition_id}", method = RequestMethod.GET)
    public ResponseEntity deactivateEdition(@PathVariable("edition_id") long editionId,
                                            @RequestHeader("Session-Code") String sessionCode) {

        ContractsCoordinator coordinator = sessionService.getCoordinatorOf(sessionCode);
        if (coordinator == null || !coordinator.getRole().equals(CoordinatorRole.DEPARTMENT)) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        Edition deactivatedEdition = editionRepository.findById(editionId).get();

        List<Contract> contractsByEdition = contractRepository.findByEdition_Id(editionId);
        Set<ContractsCoordinator> contractsCoordinators = new LinkedHashSet<>();

        for (Contract contract : contractsByEdition) {
            contractsCoordinators.add(contract.getContractsCoordinator());
        }

        for (ContractsCoordinator contractsCoordinator : contractsCoordinators) {
            contractsCoordinator.setIfAccepted(false);
            contractCoordinatorRepository.save(contractsCoordinator);
        }

        ContractsCoordinator departmentCoordinator = contractCoordinatorRepository.findByRole(CoordinatorRole.DEPARTMENT).get();
        departmentCoordinator.setIfAccepted(false);
        contractCoordinatorRepository.save(departmentCoordinator);

        deactivatedEdition.setIsActive(false);
        editionRepository.save(deactivatedEdition);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/activate/{edition_id}", method = RequestMethod.GET)
    public ResponseEntity activateEdition(@PathVariable("edition_id") long editionId,
                                          @RequestHeader("Session-Code") String sessionCode) {

        ContractsCoordinator coordinator = sessionService.getCoordinatorOf(sessionCode);
        if (coordinator == null || !coordinator.getRole().equals(CoordinatorRole.DEPARTMENT)) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        Edition deactivatedEdition = editionRepository.findById(editionId).get();
        deactivatedEdition.setIsActive(true);
        editionRepository.save(deactivatedEdition);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/ifCanDownload/{edition_id}/{if_wieit}", method = RequestMethod.GET)
    public ResponseEntity<String> checkIfCanDownload(@PathVariable("edition_id") long editionId,
                                             @PathVariable("if_wieit") String if_wieit,
                                          @RequestHeader("Session-Code") String sessionCode) {

        ContractsCoordinator coordinator = sessionService.getCoordinatorOf(sessionCode);
        if (coordinator == null || !coordinator.getRole().equals(CoordinatorRole.DEPARTMENT)) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        if (registrationService.checkIfEachStudentIsAcceptedForMax1Contract(Boolean.parseBoolean(if_wieit), editionId))
            return new ResponseEntity<>("true", HttpStatus.OK);
        return new ResponseEntity<>("false", HttpStatus.OK);
    }

    @RequestMapping(value = "/editions/ifCanDownload", method = RequestMethod.GET)
    public ResponseEntity<IfCanDownloadEditionResponseBody> checkIfCanDownload(@RequestHeader("Session-Code") String sessionCode) {

        ContractsCoordinator coordinator = sessionService.getCoordinatorOf(sessionCode);
        if (coordinator == null || !coordinator.getRole().equals(CoordinatorRole.DEPARTMENT)) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        List<Edition> allEditions = editionRepository.findAll();

        IfCanDownloadEditionResponseBody responseBody = new IfCanDownloadEditionResponseBody();

        for (Edition edition : allEditions) {
            IfCanDownloadEditionSingleResponseBody singleResponseBody =
                    new IfCanDownloadEditionSingleResponseBody(edition.getId(),
                            String.valueOf(registrationService.checkIfEachStudentIsAcceptedForMax1Contract(false, edition.getId())),
                            String.valueOf(registrationService.checkIfEachStudentIsAcceptedForMax1Contract(true, edition.getId())));
            responseBody.add(singleResponseBody);

        }

        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @RequestMapping(value = "/add", method= RequestMethod.POST)
    public ResponseEntity<String> addNewEdition(@RequestParam("edition_year") String editionYear,
                                              @RequestParam("coordinators_file") MultipartFile coordinatorsFile,
                                              @RequestParam("contracts_file") MultipartFile contractsFile,
                                              @RequestParam("registrations_file") MultipartFile registrationsFile,
                                                @RequestParam("session_code") String sessionCode
                                              ) throws IOException {
        ContractsCoordinator coordinator = sessionService.getCoordinatorOf(sessionCode);
        if (coordinator == null || !coordinator.getRole().equals(CoordinatorRole.DEPARTMENT)) {
            return new ResponseEntity<>("", HttpStatus.UNAUTHORIZED);
        }

        if (!editionRepository.findByIsActiveIsTrue().isEmpty())
            return new ResponseEntity<>("Only one edition can be active", HttpStatus.NOT_ACCEPTABLE);
        File coordinatorsConvertedFile = FileUtils.convert(coordinatorsFile);
        File contractsConvertedFile = FileUtils.convert(contractsFile);
        File registrationsConvertedFile = FileUtils.convert(registrationsFile);
        if (!coordinatorsFile.isEmpty() && !contractsFile.isEmpty() && !registrationsFile.isEmpty()) {
            try {
                readCSVFileService.saveCoordinatorsToDatabase(coordinatorsConvertedFile);
                readCSVFileService.saveContractsToDatabase(contractsConvertedFile, editionYear);
                readCSVFileService.saveRegistrationsToDatabase(registrationsConvertedFile, editionYear);
                coordinatorsConvertedFile.delete();
                contractsConvertedFile.delete();
                registrationsConvertedFile.delete();

                return new ResponseEntity<>(HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>("You failed to upload " + " => " + e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
            }
        } else {
            return new ResponseEntity<>("You failed to upload " + " because the file was empty.", HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @RequestMapping(value = "/update", method= RequestMethod.POST)
    public ResponseEntity<String> updateEdition(@RequestParam("edition_id") long editionId,
                                                @RequestParam("edit_edition_file") MultipartFile editEditionFile,
                                                @RequestParam("session_code") String sessionCode
                                                ) throws IOException {

        ContractsCoordinator coordinator = sessionService.getCoordinatorOf(sessionCode);
        if (coordinator == null || !coordinator.getRole().equals(CoordinatorRole.DEPARTMENT)) {
            return new ResponseEntity<>("", HttpStatus.UNAUTHORIZED);
        }

        if (!editionRepository.findById(editionId).get().getIsActive())
            return new ResponseEntity<>("You cannot edit inactive edition", HttpStatus.NOT_ACCEPTABLE);

        File registrationsConvertedFile = FileUtils.convert(editEditionFile);
        if (!editEditionFile.isEmpty()) {
            try {
                readCSVFileService.updateRegistrations(registrationsConvertedFile, editionId);
                registrationsConvertedFile.delete();

                return new ResponseEntity<>(HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>("You failed to upload " + " => " + e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
            }
        } else {
            return new ResponseEntity<>("You failed to upload " + " because the file was empty.", HttpStatus.NOT_ACCEPTABLE);
        }
    }
}