package pl.agh.edu.erasmus_system.service;

import com.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.agh.edu.erasmus_system.model.Contract;
import pl.agh.edu.erasmus_system.model.ContractsCoordinator;
import pl.agh.edu.erasmus_system.model.Edition;
import pl.agh.edu.erasmus_system.repository.ContractCoordinatorRepository;
import pl.agh.edu.erasmus_system.repository.ContractRepository;

import java.io.FileReader;
import java.lang.reflect.Array;
import java.util.List;


@Service
public class ReadCSVFileService {

    @Autowired
    private ContractCoordinatorRepository contractCoordinatorRepository;

    @Autowired
    private ContractRepository contractRepository;

    public void saveCoordinatorsToDatabase(String fileName) {
        List<String[]> r = null;
        try (CSVReader reader = new CSVReader(new FileReader(fileName))) {
            r = reader.readAll();
        } catch (Exception e) {
            e.printStackTrace();
        }

        int listIndex = 0;
        for (String[] arrays : r) {
            if (listIndex++ > 0) {

                ContractsCoordinator contractsCoordinator = new ContractsCoordinator();
                contractsCoordinator.setName(Array.get(arrays, 0).toString());
//                System.out.println(Array.get(arrays, 1).toString());
                contractsCoordinator.setCode(Array.get(arrays, 1).toString());
                contractCoordinatorRepository.save(contractsCoordinator);
            }

        }
    }

    public void saveContractsToDatabase(String fileName) {
        List<String[]> r = null;
        try (CSVReader reader = new CSVReader(new FileReader(fileName))) {
            r = reader.readAll();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Edition edition = new Edition();
        edition.setYear(2021);
        int listIndex = 0;
        for (String[] arrays : r) {
            if (listIndex++ > 0) {

                Contract contract = new Contract();
                contract.setEdition(edition);
//                contract.setDegree();

                ContractsCoordinator contractsCoordinator = new ContractsCoordinator();
                contractsCoordinator.setName(Array.get(arrays, 0).toString());
//                System.out.println(Array.get(arrays, 1).toString());
                contractsCoordinator.setCode(Array.get(arrays, 1).toString());
                contractCoordinatorRepository.save(contractsCoordinator);
            }

        }
    }
}
