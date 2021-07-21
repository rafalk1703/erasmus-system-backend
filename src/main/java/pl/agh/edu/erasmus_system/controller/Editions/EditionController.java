package pl.agh.edu.erasmus_system.controller.Editions;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.agh.edu.erasmus_system.Utils.FileUtils;
import pl.agh.edu.erasmus_system.controller.Editions.response_bodies.EditionStatisticsResponseBody;
import pl.agh.edu.erasmus_system.controller.contracts.response_bodies.ContractResponseBody;
import pl.agh.edu.erasmus_system.model.Contract;
import pl.agh.edu.erasmus_system.model.Edition;
import pl.agh.edu.erasmus_system.model.Registration;
import pl.agh.edu.erasmus_system.repository.ContractRepository;
import pl.agh.edu.erasmus_system.repository.EditionRepository;
import pl.agh.edu.erasmus_system.repository.RegistrationRepository;
import pl.agh.edu.erasmus_system.service.ReadCSVFileService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("api/edition")
@CrossOrigin
public class EditionController {

    @Autowired
    private ReadCSVFileService readCSVFileService;

    @Autowired
    private EditionRepository editionRepository;

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private RegistrationRepository registrationRepository;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Edition> getEditions() {
        return editionRepository.findAll();
    }

    @RequestMapping(value = "/delete/{edition}", method = RequestMethod.GET)
    public void deleteEdition(@PathVariable("edition") String edition) {
        Edition editionToDelete = editionRepository.findByYear(edition).get();
        List<Contract> contractsToDelete = contractRepository.findByEdition_Year(edition);
        List<Registration> registrationsToDelete = new LinkedList<>();

        for (Contract contract : contractsToDelete)
            registrationsToDelete.addAll(registrationRepository.findByContract(contract));

        for (Registration registration : registrationsToDelete)
            registrationRepository.delete(registration);

        for (Contract contract : contractsToDelete)
            contractRepository.delete(contract);

        editionRepository.delete(editionToDelete);
    }

    @RequestMapping(value = "/active", method = RequestMethod.GET)
    public List<Edition> getActiveEditions() {
        return editionRepository.findByIsActiveIsTrue();
    }


    @RequestMapping(value = "/isActive/{edition_year}", method = RequestMethod.GET)
    public Boolean checkIfIsActiveEdition(@PathVariable("edition_year") String editionYear) {
        return editionRepository.findByYear(editionYear).get().getIsActive();
    }

    @RequestMapping(value = "/statistics/{edition_year}", method = RequestMethod.GET)
    public ResponseEntity<EditionStatisticsResponseBody> editionStatistics(@PathVariable("edition_year") String editionYear) {

        Edition edition = editionRepository.findByYear(editionYear).get();
        Long id = edition.getId();
        List<Contract> contracts = contractRepository.findByEdition_Year(editionYear);
        int numberOfRegistrations = 0;
        int numberOfContracts1Degree = 0;
        int numberOfContracts2Degree = 0;
        int numberOfContracts3Degree = 0;
        int numberOfRegistrations1Degree = 0;
        int numberOfRegistrations2Degree = 0;
        int numberOfRegistrations3Degree = 0;

        for (Contract contract : contracts) {

            if (contract.getDegree().equals("1st") && contract.getVacancies() > 0)
                numberOfContracts1Degree++;
            if (contract.getDegree().equals("2st") && contract.getVacancies() > 0)
                numberOfContracts2Degree++;
            if (contract.getDegree().equals("3st") && contract.getVacancies() > 0)
                numberOfContracts3Degree++;

            for (Registration registration : registrationRepository.findByContract(contract)) {
                numberOfRegistrations++;
                if (contract.getDegree().equals("1st") && contract.getVacancies() > 0)
                    numberOfRegistrations1Degree++;
                if (contract.getDegree().equals("2st") && contract.getVacancies() > 0)
                    numberOfRegistrations2Degree++;
                if (contract.getDegree().equals("3st") && contract.getVacancies() > 0)
                    numberOfRegistrations3Degree++;
            }

        }

        EditionStatisticsResponseBody response = new EditionStatisticsResponseBody(id, editionYear, contracts.size(), numberOfRegistrations, numberOfContracts1Degree,
                numberOfContracts2Degree, numberOfContracts3Degree, numberOfRegistrations1Degree, numberOfRegistrations2Degree,
                numberOfRegistrations3Degree);

        return new ResponseEntity<>(response, HttpStatus.OK);

    }


    @RequestMapping(value = "/deactivate/{edition}", method = RequestMethod.GET)
    public ResponseEntity deactivateEdition(@PathVariable("edition") String edition) {
        Edition deactivatedEdition = editionRepository.findByYear(edition).get();
        deactivatedEdition.setIsActive(false);
        editionRepository.save(deactivatedEdition);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/activate/{edition}", method = RequestMethod.GET)
    public ResponseEntity activateEdition(@PathVariable("edition") String edition) {
        Edition deactivatedEdition = editionRepository.findByYear(edition).get();
        deactivatedEdition.setIsActive(true);
        editionRepository.save(deactivatedEdition);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/add/{edition_year}", method= RequestMethod.POST)
    public @ResponseBody String addNewEdition(@PathVariable("edition_year") String editionYear,
                                              @RequestParam("coordinators_file") MultipartFile coordinatorsFile,
                                              @RequestParam("contracts_file") MultipartFile contractsFile,
                                              @RequestParam("registrations_file") MultipartFile registrationsFile
                                              ) throws IOException {
        System.out.println("dzialaEndpoint");
        System.out.println(coordinatorsFile.getName());
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

                return "";

            } catch (Exception e) {

                return "You failed to upload " + " => " + e.getMessage();
            }
        } else {

            return "You failed to upload " + " because the file was empty.";
        }
    }



}
