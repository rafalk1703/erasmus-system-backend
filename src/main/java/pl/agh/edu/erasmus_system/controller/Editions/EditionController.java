package pl.agh.edu.erasmus_system.controller.Editions;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.agh.edu.erasmus_system.model.Contract;
import pl.agh.edu.erasmus_system.model.Edition;
import pl.agh.edu.erasmus_system.model.Registration;
import pl.agh.edu.erasmus_system.repository.ContractRepository;
import pl.agh.edu.erasmus_system.repository.EditionRepository;
import pl.agh.edu.erasmus_system.repository.RegistrationRepository;

import java.util.*;

@RestController
@RequestMapping("api/edition")
public class EditionController {

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

}
