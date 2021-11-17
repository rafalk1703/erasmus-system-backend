package pl.agh.edu.erasmus_system.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.agh.edu.erasmus_system.services.GenerateCSVFileService;
import pl.agh.edu.erasmus_system.services.RegistrationService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@RestController
@RequestMapping("api/CSVFile")
@CrossOrigin
public class GenerateCSVFileController {

    @Autowired
    private GenerateCSVFileService generateCSVFileService;

    @Autowired
    private RegistrationService registrationService;

    @RequestMapping(value="/generate/{edition_id}", method= RequestMethod.GET)
    public void downloadCSV(HttpServletResponse response, @PathVariable("edition_id") long editionId) throws IOException {

        if (!registrationService.checkIfEachStudentIsAcceptedForMax1Contract(false, editionId))
            return;
        response.setContentType("text/csv");
        response.setCharacterEncoding("UTF-8");


        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=studenci_spoza_WIEiT.csv";
        response.setHeader(headerKey, headerValue);

        generateCSVFileService.generateCSVFile(response, editionId);


    }

    @RequestMapping(value="/generate/WIEIT/{edition_id}", method= RequestMethod.GET)
    public void downloadCSVWIEIT(HttpServletResponse response, @PathVariable("edition_id") long editionId) throws IOException {

        if (!registrationService.checkIfEachStudentIsAcceptedForMax1Contract(true, editionId))
            return;
        response.setContentType("text/csv");
        response.setCharacterEncoding("UTF-8");


        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=studenci_WIEiT.csv";
        response.setHeader(headerKey, headerValue);

        generateCSVFileService.generateCSVWIEITFile(response, editionId);
    }

}
