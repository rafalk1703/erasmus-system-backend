package pl.agh.edu.erasmus_system.controller.Editions;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.agh.edu.erasmus_system.model.Edition;
import pl.agh.edu.erasmus_system.repository.EditionRepository;
import java.util.*;

@RestController
@RequestMapping("api/edition")
public class EditionController {

    @Autowired
    private EditionRepository editionRepository;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Edition> getEditions() {
        return editionRepository.findAll();
    }

    @RequestMapping(value = "delete/{edition}", method = RequestMethod.DELETE)
    public void deleteEdition(@PathVariable("edition") String edition) {
        Edition editionToDelete = editionRepository.findByYear(edition).get();
        editionRepository.delete(editionToDelete);
    }



}
