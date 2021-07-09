package pl.agh.edu.erasmus_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.agh.edu.erasmus_system.service.ReadCSVFileService;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


@RestController
@RequestMapping("api/import")
public class ImportDataController {

    @Autowired
    private ReadCSVFileService readCSVFileService;

    @RequestMapping(value="/uploadCSV", method= RequestMethod.POST)
    public @ResponseBody String handleFileUpload(@RequestParam("file") MultipartFile file) throws IOException {

        File newFile = convert(file);
        if (!file.isEmpty()) {
            try {

                readCSVFileService.saveCoordinatorsToDatabase(newFile);

                //Response response = new Response("Done", name);
                //return response;
                return "";

            } catch (Exception e) {
                //Response response = new Response("fail", name);
                //return response;
                return "You failed to upload " + file.getName() + " => " + e.getMessage();
            }
        } else {

            //Response response = new Response("fail", name);
            //return response;
            return "You failed to upload " + file.getName() + " because the file was empty.";
        }
    }

    private File convert(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        convFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }
//
//    @GetMapping("import")
//    public void importData() {
//        readCSVFileService.saveDataToDatabase("/Users/rafalkrajewski/Desktop/erasmus-system/inz-backend/erasmus-system-backend/src/main/resources/file.csv");
//    }
    @GetMapping("coordinators")
    public void importCoordinators() {
        readCSVFileService.saveCoordinatorsToDatabase("/Users/rafalkrajewski/Desktop/erasmus-system/inz-backend/erasmus-system-backend/src/main/resources/opiekunowie.csv");
    }

    @GetMapping("contracts")
    public void importContracts() {
        readCSVFileService.saveContractsToDatabase("/Users/rafalkrajewski/Desktop/erasmus-system/inz-backend/erasmus-system-backend/src/main/resources/umowy.csv");
    }

    @GetMapping("registrations")
    public void importRegistrations() {
        readCSVFileService.saveRegistrationsToDatabase("/Users/rafalkrajewski/Desktop/erasmus-system/inz-backend/erasmus-system-backend/src/main/resources/zgloszenia.csv");
    }

}
