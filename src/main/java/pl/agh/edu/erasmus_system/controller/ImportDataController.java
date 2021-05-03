package pl.agh.edu.erasmus_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.agh.edu.erasmus_system.service.ReadCSVFileService;

@RestController
@RequestMapping("api/")
public class ImportDataController {

    @Autowired
    private ReadCSVFileService readCSVFileService;

    @GetMapping("import")
    public void importData() {
        readCSVFileService.saveDataToDatabase("/Users/rafalkrajewski/Desktop/erasmus-system/inz-backend/erasmus-system-backend/src/main/resources/file.csv");
    }
}