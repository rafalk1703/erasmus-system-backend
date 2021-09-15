package pl.agh.edu.erasmus_system.controller;

import com.lowagie.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.agh.edu.erasmus_system.service.GeneratePdfFileService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("api/file")
@CrossOrigin
public class GeneratePdfFileController {

    @Autowired
    private GeneratePdfFileService generatePdfFileService;

    @RequestMapping(value="/generate/{edition_id}", method= RequestMethod.GET)
    public void downloadPDFResource(HttpServletResponse response, @PathVariable("edition_id") long editionId) {
        try {
            Path file = Paths.get(generatePdfFileService.generatePdf(editionId).getAbsolutePath());
            if (Files.exists(file)) {
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/pdf");
                response.addHeader("Content-Disposition",
                        "attachment; filename=" + "Lista_wydzialowa_WIEiT");
                Files.copy(file, response.getOutputStream());
                response.getOutputStream().flush();
            }
        } catch (IOException | DocumentException ex) {
            ex.printStackTrace();
        }
    }

}
