package pl.agh.edu.erasmus_system.service;

import com.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.agh.edu.erasmus_system.model.User;
import pl.agh.edu.erasmus_system.repository.UserRepository;

import java.io.FileReader;
import java.lang.reflect.Array;
import java.util.List;


@Service
public class ReadCSVFileService {

    @Autowired
    private UserRepository userRepository;

    public void saveDataToDatabase(String fileName) {
        List<String[]> r = null;
        try (CSVReader reader = new CSVReader(new FileReader(fileName))) {
            r = reader.readAll();
        } catch (Exception e) {
            e.printStackTrace();
        }

        int listIndex = 0;
        for (String[] arrays : r) {
            if (listIndex++ > 0) {

                User user = new User();
                user.setFirstName(Array.get(arrays, 1).toString());
                user.setLastName(Array.get(arrays, 2).toString());
                user.setEmail(Array.get(arrays, 3).toString());
                userRepository.save(user);
            }

        }


    }
}
