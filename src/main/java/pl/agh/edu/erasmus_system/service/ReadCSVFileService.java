package pl.agh.edu.erasmus_system.service;

import com.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.agh.edu.erasmus_system.model.*;
import pl.agh.edu.erasmus_system.repository.*;

import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.util.List;


@Service
public class ReadCSVFileService {

    @Autowired
    private ContractCoordinatorRepository contractCoordinatorRepository;

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private RegistrationRepository registrationRepository;

    @Autowired
    private EditionRepository editionRepository;



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
                contractsCoordinator.setCode(Array.get(arrays, 1).toString());
                if (!contractCoordinatorRepository.findByName(Array.get(arrays, 0).toString()).isPresent()) {
                    contractCoordinatorRepository.save(contractsCoordinator);
                }
            }

        }
    }

    public void saveCoordinatorsToDatabase(File file) {
        System.out.println("działa");
        List<String[]> r = null;
        try (CSVReader reader = new CSVReader(new FileReader(file))) {
            r = reader.readAll();
        } catch (Exception e) {
            e.printStackTrace();
        }

        int listIndex = 0;
        for (String[] arrays : r) {
            if (listIndex++ > 0) {
                ContractsCoordinator contractsCoordinator = new ContractsCoordinator();
                contractsCoordinator.setName(Array.get(arrays, 0).toString());
                System.out.println(Array.get(arrays, 0).toString());
                contractsCoordinator.setCode(Array.get(arrays, 1).toString());
                if (!contractCoordinatorRepository.findByName(Array.get(arrays, 0).toString()).isPresent()) {
                    contractCoordinatorRepository.save(contractsCoordinator);
                }
            }

        }
    }

    public void saveContractsToDatabase(File file, String year) {
        List<String[]> r = null;
        try (CSVReader reader = new CSVReader(new FileReader(file))) {
            r = reader.readAll();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Edition edition = new Edition();
        edition.setYear(year);
        edition.setIsActive(true);
        if (!editionRepository.findByYear(year).isPresent())
            editionRepository.save(edition);

        int listIndex = 0;
        for (String[] arrays : r) {
            if (listIndex++ > 0) {

                if (!Array.get(arrays, 7).toString().equals("0")) {
                    Contract contract = new Contract();
                    contract.setEdition(edition);
                    contract.setErasmusCode(Array.get(arrays, 0).toString());
                    contract.setAcademy(Array.get(arrays, 1).toString());
                    contract.setStartYear(Array.get(arrays, 2).toString());
                    contract.setEndYear(Array.get(arrays, 3).toString());
                    ContractsCoordinator contractsCoordinator = contractCoordinatorRepository.findByName(Array.get(arrays, 4).toString()).get();
                    contract.setContractsCoordinator(contractsCoordinator);
                    contract.setFaculty(Array.get(arrays, 5).toString());
                    contract.setVacancies(Integer.parseInt(Array.get(arrays, 7).toString()));
                    contract.setDegree("1st");
                    if (!contractRepository.findByErasmusCodeAndContractsCoordinator_Code(contract.getErasmusCode(), contractsCoordinator.getCode()).isPresent()) {
                        contractRepository.save(contract);
                    }
                }
                if (!Array.get(arrays, 8).toString().equals("0")) {
                    Contract contract = new Contract();
                    contract.setEdition(edition);
                    contract.setErasmusCode(Array.get(arrays, 0).toString());
                    contract.setAcademy(Array.get(arrays, 1).toString());
                    contract.setStartYear(Array.get(arrays, 2).toString());
                    contract.setEndYear(Array.get(arrays, 3).toString());
                    ContractsCoordinator contractsCoordinator = contractCoordinatorRepository.findByName(Array.get(arrays, 4).toString()).get();
                    contract.setContractsCoordinator(contractsCoordinator);
                    contract.setFaculty(Array.get(arrays, 5).toString());
                    contract.setVacancies(Integer.parseInt(Array.get(arrays, 8).toString()));
                    contract.setDegree("2st");
                    if (!contractRepository.findByErasmusCodeAndContractsCoordinator_Code(contract.getErasmusCode(), contractsCoordinator.getCode()).isPresent()) {
                        contractRepository.save(contract);
                    }
                }
                if (!Array.get(arrays, 9).toString().equals("0")) {
                    Contract contract = new Contract();
                    contract.setEdition(edition);
                    contract.setErasmusCode(Array.get(arrays, 0).toString());
                    contract.setAcademy(Array.get(arrays, 1).toString());
                    contract.setStartYear(Array.get(arrays, 2).toString());
                    contract.setEndYear(Array.get(arrays, 3).toString());
                    ContractsCoordinator contractsCoordinator = contractCoordinatorRepository.findByName(Array.get(arrays, 4).toString()).get();
                    contract.setContractsCoordinator(contractsCoordinator);
                    contract.setFaculty(Array.get(arrays, 5).toString());
                    contract.setVacancies(Integer.parseInt(Array.get(arrays, 9).toString()));
                    contract.setDegree("3st");
                    if (!contractRepository.findByErasmusCodeAndContractsCoordinator_Code(contract.getErasmusCode(), contractsCoordinator.getCode()).isPresent()) {
                        contractRepository.save(contract);
                    }
                }
            }

        }
    }

    public void saveRegistrationsToDatabase(File file, String year) {
        List<String[]> r = null;
        try (CSVReader reader = new CSVReader(new FileReader(file))) {
            r = reader.readAll();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Edition edition = editionRepository.findByYear(year).get();

        int listIndex = 0;
        for (String[] arrays : r) {
            if (listIndex++ > 0) {
                Registration registration = new Registration();
                Student student = new Student();
                student.setName(Array.get(arrays, 4).toString());
                student.setSurname(Array.get(arrays, 5).toString());
                student.setDepartment(Array.get(arrays, 3).toString());
                student.setEmail(Array.get(arrays, 7).toString());
                student.setYear(Array.get(arrays, 2).toString());
                student.setField(Array.get(arrays, 22).toString());
                studentRepository.save(student);
                registration.setIsNominated(false);
                registration.setIsAccepted(false);
                registration.setStudent(student);
                registration.setPriority(Integer.parseInt(Array.get(arrays, 21).toString()));
                ContractsCoordinator contractsCoordinator = contractCoordinatorRepository.findByName(Array.get(arrays, 15).toString()).get();
                String erasmusCode = Array.get(arrays, 17).toString();
                Contract contract = contractRepository.findByErasmusCodeAndContractsCoordinator_CodeAndEdition(erasmusCode, contractsCoordinator.getCode(), edition).get();
                registration.setContract(contract);
                registrationRepository.save(registration);
            }

        }
    }


}
