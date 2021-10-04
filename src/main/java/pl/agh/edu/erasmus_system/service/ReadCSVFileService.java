package pl.agh.edu.erasmus_system.service;

import com.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.agh.edu.erasmus_system.Utils.PasswordManagement;
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

    @Value("${application.domain}")
    public String domainName;

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
                if (contractCoordinatorRepository.findByName(Array.get(arrays, 0).toString()).isEmpty()) {
                    ContractsCoordinator contractsCoordinator = new ContractsCoordinator();
                    contractsCoordinator.setName(Array.get(arrays, 0).toString());
                    contractsCoordinator.setCode(Array.get(arrays, 1).toString());
                    //TODO Set coordinator email

                    String password = PasswordManagement.generatePassword();
                    contractsCoordinator.setHash(PasswordManagement.generateHash(password));

                    contractCoordinatorRepository.save(contractsCoordinator);

                    EmailSender sender = EmailSender.getSender();
                    String emailMessage = new StringBuilder()
                            .append("Witamy w Erasmus System! ")
                            .append(domainName)
                            .append("/login?email=")
                            .append("email") //TODO Add email variable
                            .append(System.lineSeparator())
                            .append("Zostało utworzone Twoje konto")
                            .append(System.lineSeparator())
                            .append("Oto Twoje hasło do systemu: ")
                            .append(password)
                            .toString();
                    sender.sendEmailTo("dkulma@student.agh.edu.pl", "Hasło do Erasmus System", emailMessage); //TODO Change email-to
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
                    if (!contractRepository.findByErasmusCodeAndContractsCoordinator_CodeAndEditionAndDegree(contract.getErasmusCode(), contractsCoordinator.getCode(), edition, "1st").isPresent()) {
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
                    if (!contractRepository.findByErasmusCodeAndContractsCoordinator_CodeAndEditionAndDegree(contract.getErasmusCode(), contractsCoordinator.getCode(), edition, "2st").isPresent()) {
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
                    if (!contractRepository.findByErasmusCodeAndContractsCoordinator_CodeAndEditionAndDegree(contract.getErasmusCode(), contractsCoordinator.getCode(), edition, "3st").isPresent()) {
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
                Student student = null;
                if (studentRepository.findStudentByEditionAndEmail(edition.getId(), Array.get(arrays, 7).toString()).isEmpty()) {
                    student = new Student();
                    student.setName(Array.get(arrays, 4).toString());
                    student.setSurname(Array.get(arrays, 5).toString());
                    student.setDepartment(Array.get(arrays, 3).toString());
                    student.setEmail(Array.get(arrays, 7).toString());
                    student.setYear(Array.get(arrays, 2).toString());
                    student.setField(Array.get(arrays, 22).toString());
                    student.setPhoneNumber(Array.get(arrays, 23).toString());
                    student.setEarlierParticipation(Array.get(arrays, 24).toString());
                    student.setPeriodOfStay(Array.get(arrays, 20).toString());
                    studentRepository.save(student);
                } else {
                    student = studentRepository.findStudentByEditionAndEmail(edition.getId(), Array.get(arrays, 7).toString()).get();
                }
                registration.setIsNominated(false);
                registration.setIsAccepted(false);
                registration.setStudent(student);
                registration.setPriority(Integer.parseInt(Array.get(arrays, 21).toString()));
                ContractsCoordinator contractsCoordinator = contractCoordinatorRepository.findByName(Array.get(arrays, 15).toString()).get();
                String erasmusCode = Array.get(arrays, 17).toString();
                String degree = Array.get(arrays, 19).toString() + "st";
                Contract contract = contractRepository.findByErasmusCodeAndContractsCoordinator_CodeAndEditionAndDegree(erasmusCode, contractsCoordinator.getCode(), edition, degree).get();
                registration.setContract(contract);
                registrationRepository.save(registration);
            }

        }
    }

    public void updateRegistrations(File file, long id) {
        List<String[]> r = null;
        try (CSVReader reader = new CSVReader(new FileReader(file))) {
            r = reader.readAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Edition edition = editionRepository.findById(id).get();

        int listIndex = 0;
        for (String[] arrays : r) {
            if (listIndex++ > 0) {

                Registration registration = new Registration();
                Student student = null;
                if (studentRepository.findStudentByEditionAndEmail(edition.getId(), Array.get(arrays, 7).toString()).isEmpty()) {
                    student = new Student();
                    student.setName(Array.get(arrays, 4).toString());
                    student.setSurname(Array.get(arrays, 5).toString());
                    student.setDepartment(Array.get(arrays, 3).toString());
                    student.setEmail(Array.get(arrays, 7).toString());
                    student.setYear(Array.get(arrays, 2).toString());
                    student.setField(Array.get(arrays, 22).toString());
                    student.setPhoneNumber(Array.get(arrays, 23).toString());
                    student.setEarlierParticipation(Array.get(arrays, 24).toString());
                    student.setPeriodOfStay(Array.get(arrays, 20).toString());
                    studentRepository.save(student);
                    registration.setIsNominated(false);
                    registration.setIsAccepted(false);
                    registration.setStudent(student);
                    registration.setPriority(Integer.parseInt(Array.get(arrays, 21).toString()));
                    ContractsCoordinator contractsCoordinator = contractCoordinatorRepository.findByName(Array.get(arrays, 15).toString()).get();
                    String erasmusCode = Array.get(arrays, 17).toString();
                    String degree = Array.get(arrays, 19).toString() + "st";
                    Contract contract = contractRepository.findByErasmusCodeAndContractsCoordinator_CodeAndEditionAndDegree(erasmusCode, contractsCoordinator.getCode(), edition, degree).get();
                    registration.setContract(contract);
                    registrationRepository.save(registration);
                } else {
                    student = studentRepository.findStudentByEditionAndEmail(edition.getId(), Array.get(arrays, 7).toString()).get();
                    if (!student.getName().equals(Array.get(arrays, 4).toString()))
                        student.setName(Array.get(arrays, 4).toString());
                    if (!student.getSurname().equals(Array.get(arrays, 5).toString()))
                        student.setSurname(Array.get(arrays, 5).toString());
                    if (!student.getDepartment().equals(Array.get(arrays, 3).toString()))
                        student.setDepartment(Array.get(arrays, 3).toString());
                    if (!student.getYear().equals(Array.get(arrays, 2).toString()))
                        student.setYear(Array.get(arrays, 2).toString());
                    if (!student.getField().equals(Array.get(arrays, 22).toString()))
                        student.setField(Array.get(arrays, 22).toString());
                    if (!student.getPhoneNumber().equals(Array.get(arrays, 23).toString()))
                        student.setPhoneNumber(Array.get(arrays, 23).toString());
                    if (!student.getEarlierParticipation().equals(Array.get(arrays, 24).toString()))
                        student.setEarlierParticipation(Array.get(arrays, 24).toString());
                    if (!student.getPeriodOfStay().equals(Array.get(arrays, 20).toString()))
                        student.setPeriodOfStay(Array.get(arrays, 20).toString());
                    studentRepository.save(student);
                    registration = registrationRepository.findByStudent_IdAndPriority(student.getId(), Integer.parseInt(Array.get(arrays, 21).toString())).get();

                    ContractsCoordinator contractsCoordinator = contractCoordinatorRepository.findByName(Array.get(arrays, 15).toString()).get();
                    String erasmusCode = Array.get(arrays, 17).toString();
                    String degree = Array.get(arrays, 19).toString() + "st";

                    if (!registration.getContract().getErasmusCode().equals(erasmusCode) ||
                            !registration.getContract().getErasmusCode().equals(degree) ||
                            !registration.getContract().getContractsCoordinator().equals(contractsCoordinator)) {
                        Contract contract = contractRepository.findByErasmusCodeAndContractsCoordinator_CodeAndEditionAndDegree(erasmusCode, contractsCoordinator.getCode(), edition, degree).get();
                        registration.setContract(contract);
                        registrationRepository.save(registration);
                    }
                }

            }

        }
    }


}
