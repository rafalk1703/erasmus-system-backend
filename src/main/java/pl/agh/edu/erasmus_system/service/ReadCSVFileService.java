package pl.agh.edu.erasmus_system.service;

import com.opencsv.CSVReader;
import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.agh.edu.erasmus_system.model.*;
import pl.agh.edu.erasmus_system.repository.*;

import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;


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

    public void saveRegistrationsToDatabase2(File file, String year) {
        List<String[]> r = null;
        try (CSVReader reader = new CSVReader(new FileReader(file))) {
            r = reader.readAll();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Edition edition = editionRepository.findByYear(year).get();

        System.out.println("test1");

        int listIndex = 0;

        Map<String, String> map = Stream.of(new String[][] {
                { "1", "Imię" },
                { "2", "Nawisko" },
                { "3", "Wydział" },
                { "4", "Rok" },
                { "5", "Kierunek" },
                { "6", "Adres" },
                { "7", "Telefon" },
                { "8", "Email" },
                { "9", "Średnia" },
                { "10", "Język" },
                { "11", "Poziom Egzaminu" },
                { "12", "Rodzaj certyfikatu" },
                { "13", "Umowa 1" },
                { "14", "Umowa 2" },
                { "15", "Umowa 3" },
                { "16", "Okres" },
                { "17", "Czy uczestniczył" },
        }).collect(Collectors.toMap(data -> data[0], data -> data[1]));


        System.out.println("mapa " + map);

        Map<String, Integer> map2 = new HashedMap<>();
        Pattern p = Pattern.compile("\\((\\d+)\\)");
        String [] header = r.get(0);
        for (int i = 0; i < header.length; i++) {
            if (header[i].matches("\\(\\d+\\).*")) {
                Matcher match = p.matcher(header[i]);
                if (match.find()) {
                    String columnName = map.get(match.group(1));
                    System.out.println("header = " + header[i]);
                    System.out.println("column name = " + columnName);
                    System.out.println("numer z kolumny  = " + match.group(1));
                    map2.put(columnName, i);
                }
            }
        }

        System.out.println(map2);
        for (String[] arrays : r) {
            if (listIndex++ > 0) {

                List<String> agreements = Arrays.asList(Array.get(arrays, map2.get("Umowa 1")).toString(),
                        Array.get(arrays, map2.get("Umowa 2")).toString(),
                        Array.get(arrays, map2.get("Umowa 3")).toString());
                Student student = null;
                if (studentRepository.findStudentByEditionAndEmail(edition.getId(), Array.get(arrays, map2.get("Email")).toString()).isEmpty()) {
                    student = new Student();
                    student.setName(Array.get(arrays, map2.get("Imię")).toString());
                    student.setSurname(Array.get(arrays, map2.get("Nawisko")).toString());
                    student.setDepartment(Array.get(arrays, map2.get("Wydział")).toString());
                    student.setEmail(Array.get(arrays, map2.get("Email")).toString());
                    student.setYear(Array.get(arrays, map2.get("Rok")).toString());
                    student.setField(Array.get(arrays, map2.get("Kierunek")).toString());
                    student.setPhoneNumber(Array.get(arrays, map2.get("Telefon")).toString());
                    student.setEarlierParticipation(Array.get(arrays, map2.get("Czy uczestniczył")).toString());
                    student.setPeriodOfStay(Array.get(arrays, map2.get("Okres")).toString());
                    student.setAverageGrade(Array.get(arrays, map2.get("Średnia")).toString());
                    student.setExamLevel(Array.get(arrays, map2.get("Poziom Egzaminu")).toString());
                    student.setTypeOfCertificate(Array.get(arrays, map2.get("Rodzaj certyfikatu")).toString());
                    student.setForeignLanguage(Array.get(arrays, map2.get("Język")).toString());
                    System.out.println("USTAWIAM ADRES " + Array.get(arrays, map2.get("Adres")).toString());
                    student.setAddress(Array.get(arrays, map2.get("Adres")).toString());
                    System.out.println("student adress " + student.getAddress());
                    studentRepository.save(student);
                    System.out.println("add student " + student.getName());
                } else {
                    student = studentRepository.findStudentByEditionAndEmail(edition.getId(), Array.get(arrays, map2.get("Email")).toString()).get();
                    System.out.println("add student2 " + student.getName());
                }
                System.out.println(agreements.toString());
                for (int i = 0; i < agreements.size(); i++) {
                    System.out.println("agregregrge " + agreements.get(i));
                    if (!agreements.get(i).equals("BRAK")) {
                        Registration registration = new Registration();
                        registration.setIsNominated(false);
                        registration.setIsAccepted(false);
                        registration.setStudent(student);
                        registration.setPriority(i+1);
                        System.out.println("contractsCoordinator " + getCoordinatorNameFromAgreement(agreements.get(i)));
                        ContractsCoordinator contractsCoordinator = contractCoordinatorRepository
                                .findByName(getCoordinatorNameFromAgreement(agreements.get(i))).get();
                        System.out.println("coordinator code " + contractsCoordinator.getCode());

                        System.out.println();
                        String erasmusCode = getErasmusCodeFromAgreement(agreements.get(i));
                        System.out.println(" erasmus code " + erasmusCode);
                        String degree = getDegreeFromYear(Array.get(arrays, map2.get("Rok")).toString());
                        System.out.println(" degree code " + degree);
                        Contract contract = contractRepository.findByErasmusCodeAndContractsCoordinator_CodeAndEditionAndDegree(erasmusCode, contractsCoordinator.getCode(), edition, degree).get();
                        System.out.println(" contract " + contract.getErasmusCode());
                        registration.setContract(contract);
                        registrationRepository.save(registration);
                    }
                }
            }

        }
    }

    public void updateRegistrations2(File file, long id) {
        List<String[]> r = null;
        try (CSVReader reader = new CSVReader(new FileReader(file))) {
            r = reader.readAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Edition edition = editionRepository.findById(id).get();

        int listIndex = 0;

        Map<String, String> map = Stream.of(new String[][] {
                { "1", "Imię" },
                { "2", "Nawisko" },
                { "3", "Wydział" },
                { "4", "Rok" },
                { "5", "Kierunek" },
                { "6", "Adres" },
                { "7", "Telefon" },
                { "8", "Email" },
                { "9", "Średnia" },
                { "10", "Język" },
                { "11", "Poziom Egzaminu" },
                { "12", "Rodzaj certyfikatu" },
                { "13", "Umowa 1" },
                { "14", "Umowa 2" },
                { "15", "Umowa 3" },
                { "16", "Okres" },
                { "17", "Czy uczestniczył" },
        }).collect(Collectors.toMap(data -> data[0], data -> data[1]));


        System.out.println("mapa " + map);

        Map<String, Integer> map2 = new HashedMap<>();
        Pattern p = Pattern.compile("\\((\\d+)\\)");
        String [] header = r.get(0);
        for (int i = 0; i < header.length; i++) {
            if (header[i].matches("\\(\\d+\\).*")) {
                Matcher match = p.matcher(header[i]);
                if (match.find()) {
                    String columnName = map.get(match.group(1));
                    map2.put(columnName, i);
                }
            }
        }

        for (String[] arrays : r) {
            if (listIndex++ > 0) {

                List<String> agreements = Arrays.asList(Array.get(arrays, map2.get("Umowa 1")).toString(),
                        Array.get(arrays, map2.get("Umowa 2")).toString(),
                        Array.get(arrays, map2.get("Umowa 3")).toString());

                System.out.println(agreements.toString());
                Student student = null;
                //1 dodanie nowego studenta
                if (studentRepository.findStudentByEditionAndEmail(edition.getId(), Array.get(arrays, map2.get("Email")).toString()).isEmpty()) {
                    System.out.println("DODANIE NOWEGO STUDENTA");
                    student = new Student();
                    student.setName(Array.get(arrays, map2.get("Imię")).toString());
                    student.setSurname(Array.get(arrays, map2.get("Nawisko")).toString());
                    student.setDepartment(Array.get(arrays, map2.get("Wydział")).toString());
                    student.setEmail(Array.get(arrays, map2.get("Email")).toString());
                    student.setYear(Array.get(arrays, map2.get("Rok")).toString());
                    student.setField(Array.get(arrays, map2.get("Kierunek")).toString());
                    student.setPhoneNumber(Array.get(arrays, map2.get("Telefon")).toString());
                    student.setEarlierParticipation(Array.get(arrays, map2.get("Czy uczestniczył")).toString());
                    student.setPeriodOfStay(Array.get(arrays, map2.get("Okres")).toString());
                    student.setAverageGrade(Array.get(arrays, map2.get("Średnia")).toString());
                    student.setExamLevel(Array.get(arrays, map2.get("Poziom Egzaminu")).toString());
                    student.setTypeOfCertificate(Array.get(arrays, map2.get("Rodzaj certyfikatu")).toString());
                    student.setForeignLanguage(Array.get(arrays, map2.get("Język")).toString());
                    student.setAddress(Array.get(arrays, map2.get("Adres")).toString());
                    studentRepository.save(student);
                    for (int i = 0; i < agreements.size(); i++) {
                        System.out.println("agregregrge " + agreements.get(i));
                        if (!agreements.get(i).equals("BRAK")) {
                            Registration registration = new Registration();
                            registration.setIsNominated(false);
                            registration.setIsAccepted(false);
                            registration.setStudent(student);
                            registration.setPriority(i+1);
                            System.out.println("contractsCoordinator " + getCoordinatorNameFromAgreement(agreements.get(i)));
                            ContractsCoordinator contractsCoordinator = contractCoordinatorRepository
                                    .findByName(getCoordinatorNameFromAgreement(agreements.get(i))).get();
                            System.out.println("coordinator code " + contractsCoordinator.getCode());

                            System.out.println();
                            String erasmusCode = getErasmusCodeFromAgreement(agreements.get(i));
                            System.out.println(" erasmus code " + erasmusCode);
                            String degree = getDegreeFromYear(Array.get(arrays, map2.get("Rok")).toString());
                            System.out.println(" degree code " + degree);
                            Contract contract = contractRepository.findByErasmusCodeAndContractsCoordinator_CodeAndEditionAndDegree(erasmusCode, contractsCoordinator.getCode(), edition, degree).get();
                            System.out.println(" contract " + contract.getErasmusCode());
                            registration.setContract(contract);
                            registrationRepository.save(registration);
                        }
                    }
                } else {
                    System.out.println("STUDENT ISNIJE");
                    // 2 student istnieje ale zmienia dane
                    student = studentRepository.findStudentByEditionAndEmail(edition.getId(), Array.get(arrays, map2.get("Email")).toString()).get();
                    System.out.println("student znalezniony " + student.getSurname());
                    if (!student.getName().equals(Array.get(arrays, map2.get("Imię")).toString())){
                        System.out.println("petla 1");
                        student.setName(Array.get(arrays, map2.get("Imię")).toString());
                    }

                    if (!student.getSurname().equals(Array.get(arrays, map2.get("Nawisko")).toString()))
                    {
                        System.out.println("przed " + student.getSurname() + " po " + Array.get(arrays, map2.get("Nawisko")).toString());
                        student.setSurname(Array.get(arrays, map2.get("Nawisko")).toString());
                    }

                    if (!student.getDepartment().equals(Array.get(arrays, map2.get("Wydział")).toString()))
                        student.setDepartment(Array.get(arrays, map2.get("Wydział")).toString());
                    if (!student.getYear().equals(Array.get(arrays, map2.get("Rok")).toString()))
                        student.setYear(Array.get(arrays, map2.get("Rok")).toString());
                    if (!student.getField().equals(Array.get(arrays, map2.get("Kierunek")).toString()))
                        student.setField(Array.get(arrays, map2.get("Kierunek")).toString());
                    if (!student.getPhoneNumber().equals(Array.get(arrays, map2.get("Telefon")).toString()))
                        student.setPhoneNumber(Array.get(arrays, map2.get("Telefon")).toString());
                    if (!student.getEarlierParticipation().equals(Array.get(arrays, map2.get("Czy uczestniczył")).toString()))
                        student.setEarlierParticipation(Array.get(arrays, map2.get("Czy uczestniczył")).toString());
                    if (!student.getPeriodOfStay().equals(Array.get(arrays, map2.get("Okres")).toString()))
                        student.setPeriodOfStay(Array.get(arrays, map2.get("Okres")).toString());
                    if (!student.getExamLevel().equals(Array.get(arrays, map2.get("Poziom Egzaminu")).toString()))
                        student.setExamLevel(Array.get(arrays, map2.get("Poziom Egzaminu")).toString());
                    if (!student.getTypeOfCertificate().equals(Array.get(arrays, map2.get("Rodzaj certyfikatu")).toString()))
                        student.setTypeOfCertificate(Array.get(arrays, map2.get("Rodzaj certyfikatu")).toString());
                    if (!student.getForeignLanguage().equals(Array.get(arrays, map2.get("Język")).toString()))
                        student.setForeignLanguage(Array.get(arrays, map2.get("Język")).toString());
                    if (!student.getAverageGrade().equals(Array.get(arrays, map2.get("Średnia")).toString()))
                        student.setAverageGrade(Array.get(arrays, map2.get("Średnia")).toString());
                    System.out.println("student przed adres " + student.getSurname());
                    if (!student.getAddress().equals(Array.get(arrays, map2.get("Adres")).toString()))
                        student.setAddress(Array.get(arrays, map2.get("Adres")).toString());

                    System.out.println("student przed zapisanie " + student.getSurname());
                    studentRepository.save(student);

                    // 3 student istnieje ale zmienia umowy
                    for (int i = 0; i < agreements.size(); i++) {
                        if (agreements.get(i).equals("BRAK") && registrationRepository.findByStudent_IdAndPriority(student.getId(), i+1).isEmpty()){
                            System.out.println("pętla 1");
                            continue;
                        }


                        if (!agreements.get(i).equals("BRAK") && registrationRepository.findByStudent_IdAndPriority(student.getId(), i+1).isEmpty()) {
                            System.out.println("pętla 2");
                            Registration registration = new Registration();
                            registration.setIsNominated(false);
                            registration.setIsAccepted(false);
                            registration.setStudent(student);
                            registration.setPriority(i+1);
                            ContractsCoordinator contractsCoordinator = contractCoordinatorRepository
                                    .findByName(getCoordinatorNameFromAgreement(agreements.get(i))).get();
                            String erasmusCode = getErasmusCodeFromAgreement(agreements.get(i));
                            String degree = getDegreeFromYear(Array.get(arrays, map2.get("Rok")).toString());
                            Contract contract = contractRepository.findByErasmusCodeAndContractsCoordinator_CodeAndEditionAndDegree(erasmusCode, contractsCoordinator.getCode(), edition, degree).get();
                            registration.setContract(contract);
                            registrationRepository.save(registration);
                        }


                        if (agreements.get(i).equals("BRAK") && registrationRepository.findByStudent_IdAndPriority(student.getId(), i+1).isPresent()) {
                            System.out.println("pętla 3");
                            Registration registration = registrationRepository.findByStudent_IdAndPriority(student.getId(), i + 1).get();
                            registrationRepository.delete(registration);
                        }

                        if (!agreements.get(i).equals("BRAK") && registrationRepository.findByStudent_IdAndPriority(student.getId(), i+1).isPresent()) {
                            System.out.println("pętla 4");
                            Registration registration = registrationRepository.findByStudent_IdAndPriority(student.getId(), i + 1).get();

                            String erasmusCode = registration.getContract().getErasmusCode();
                            String degree = registration.getContract().getDegree();
                            String coordinatorName = registration.getContract().getContractsCoordinator().getName();
                            ContractsCoordinator contractsCoordinator = contractCoordinatorRepository.findByName(getCoordinatorNameFromAgreement(agreements.get(i))).get();
                            if (!getErasmusCodeFromAgreement(agreements.get(i)).equals(erasmusCode) ||
                                    !getDegreeFromYear(Array.get(arrays, map2.get("Rok")).toString()).equals(degree) ||
                                    !getCoordinatorNameFromAgreement(agreements.get(i)).equals(coordinatorName)) {
                                Contract contract = contractRepository
                                        .findByErasmusCodeAndContractsCoordinator_CodeAndEditionAndDegree(getErasmusCodeFromAgreement(agreements.get(i)),
                                                contractsCoordinator.getCode(), edition, getDegreeFromYear(Array.get(arrays, map2.get("Rok")).toString())).get();
                                registration.setContract(contract);
                                registrationRepository.save(registration);
                            }
                        }
                    }
                }

            }
        }
    }

    private String getCoordinatorNameFromAgreement(String agreement) {
        Pattern p = Pattern.compile("\\((.*)\\)");
        Matcher match = p.matcher(agreement);
        if (match.find()) {
            return match.group(1).trim();
        }
        return "";
    }

    private String getErasmusCodeFromAgreement(String agreement) {
        return agreement.replaceAll("\\((.*)\\)", "").trim();
    }

    private String getDegreeFromYear(String year) {
        Pattern p = Pattern.compile("\\((.*)\\)");
        Matcher match = p.matcher(year);
        if (match.find()) {
            return match.group(1).replaceAll("\\s+","");
        }
        return "";
    }


}
