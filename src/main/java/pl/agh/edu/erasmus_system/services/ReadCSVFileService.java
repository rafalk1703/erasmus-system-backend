package pl.agh.edu.erasmus_system.services;

import com.opencsv.CSVReader;
import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.agh.edu.erasmus_system.Utils.PasswordManagement;
import pl.agh.edu.erasmus_system.model.*;
import pl.agh.edu.erasmus_system.repositories.*;

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

    @Value("${application.domain}")
    public String domainName;

    public void saveCoordinatorsToDatabase(File file) {
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
                    contractsCoordinator.setRole(CoordinatorRole.CONTRACTS);
                    contractsCoordinator.setEmail(Array.get(arrays, 2).toString());
                    contractsCoordinator.setIfAccepted(false);

                    String password = PasswordManagement.generatePassword();
                    contractsCoordinator.setHash(PasswordManagement.generateHash(password));

                    contractCoordinatorRepository.save(contractsCoordinator);

                    EmailSender sender = EmailSender.getSender();
                    String emailMessage = new StringBuilder()
                            .append("Witamy w Erasmus System! ")
                            .append(domainName)
                            .append("/")
                            .append(System.lineSeparator())
                            .append("Zosta??o utworzone Twoje konto")
                            .append(System.lineSeparator())
                            .append("Oto Twoje has??o do systemu: ")
                            .append(password)
                            .toString();
//                    sender.sendEmailTo("dkulma@student.agh.edu.pl", "Has??o do Erasmus System", emailMessage); //TODO Change email-to
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
        if (editionRepository.findByYear(year).isEmpty())
            editionRepository.save(edition);

        int listIndex = 0;
        for (String[] arrays : r) {
            if (listIndex++ > 0) {
                if (contractCoordinatorRepository.findByName(Array.get(arrays, 4).toString()).isEmpty()) {
                    continue;
                }

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
                    contract.setDegree("Ist");
                    if (contractRepository.findByErasmusCodeAndContractsCoordinator_CodeAndEditionAndDegree(contract.getErasmusCode(), contractsCoordinator.getCode(), edition, "Ist").isEmpty()) {
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
                    contract.setDegree("IIst");
                    if (contractRepository.findByErasmusCodeAndContractsCoordinator_CodeAndEditionAndDegree(contract.getErasmusCode(), contractsCoordinator.getCode(), edition, "IIst").isEmpty()) {
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
                    contract.setDegree("IIIst");
                    if (contractRepository.findByErasmusCodeAndContractsCoordinator_CodeAndEditionAndDegree(contract.getErasmusCode(), contractsCoordinator.getCode(), edition, "IIIst").isEmpty()) {
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

        Map<String, String> map = Stream.of(new String[][] {
                { "1", "Imi??" },
                { "2", "Nawisko" },
                { "3", "Wydzia??" },
                { "4", "Rok" },
                { "5", "Kierunek" },
                { "6", "Adres" },
                { "7", "Telefon" },
                { "8", "Email" },
                { "9", "??rednia" },
                { "10", "J??zyk" },
                { "11", "Poziom Egzaminu" },
                { "12", "Rodzaj certyfikatu" },
                { "13", "Umowa 1" },
                { "14", "Umowa 2" },
                { "15", "Umowa 3" },
                { "16", "Okres" },
                { "17", "Czy uczestniczy??" },
        }).collect(Collectors.toMap(data -> data[0], data -> data[1]));


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
                Student student = null;
                if (studentRepository.findStudentByEditionAndEmail(edition.getId(), Array.get(arrays, map2.get("Email")).toString()).isEmpty()) {
                    student = new Student();
                    student.setName(Array.get(arrays, map2.get("Imi??")).toString());
                    student.setSurname(Array.get(arrays, map2.get("Nawisko")).toString());
                    student.setDepartment(Array.get(arrays, map2.get("Wydzia??")).toString());
                    student.setEmail(Array.get(arrays, map2.get("Email")).toString());
                    student.setYear(Array.get(arrays, map2.get("Rok")).toString());
                    student.setField(Array.get(arrays, map2.get("Kierunek")).toString());
                    student.setPhoneNumber(Array.get(arrays, map2.get("Telefon")).toString());
                    student.setEarlierParticipation(Array.get(arrays, map2.get("Czy uczestniczy??")).toString());
                    student.setPeriodOfStay(Array.get(arrays, map2.get("Okres")).toString());
                    student.setAverageGrade(Array.get(arrays, map2.get("??rednia")).toString());
                    student.setExamLevel(Array.get(arrays, map2.get("Poziom Egzaminu")).toString());
                    student.setTypeOfCertificate(Array.get(arrays, map2.get("Rodzaj certyfikatu")).toString());
                    student.setForeignLanguage(Array.get(arrays, map2.get("J??zyk")).toString());
                    student.setAddress(Array.get(arrays, map2.get("Adres")).toString());
                    studentRepository.save(student);
                } else {
                    student = studentRepository.findStudentByEditionAndEmail(edition.getId(), Array.get(arrays, map2.get("Email")).toString()).get();
                }
                for (int i = 0; i < agreements.size(); i++) {
                    if (!agreements.get(i).equals("BRAK")) {
                        Registration registration = new Registration();
                        registration.setIsNominated(false);
                        registration.setIsAccepted(false);
                        registration.setStudent(student);
                        registration.setPriority(i+1);

                        if (contractCoordinatorRepository.findByName(getCoordinatorNameFromAgreement(agreements.get(i))).isEmpty()) {
                            continue;
                        }

                        ContractsCoordinator contractsCoordinator = contractCoordinatorRepository
                                .findByName(getCoordinatorNameFromAgreement(agreements.get(i))).get();

                        String erasmusCode = getErasmusCodeFromAgreement(agreements.get(i));
                        String degree = getDegreeFromYear(Array.get(arrays, map2.get("Rok")).toString());
                        if (contractRepository.findByErasmusCodeAndContractsCoordinator_CodeAndEditionAndDegree(erasmusCode, contractsCoordinator.getCode(), edition, degree).isEmpty()) {
                            continue;
                        }
                        Contract contract = contractRepository.findByErasmusCodeAndContractsCoordinator_CodeAndEditionAndDegree(erasmusCode, contractsCoordinator.getCode(), edition, degree).get();
                        registration.setContract(contract);
                        if (registrationRepository.findByContractAndStudentAndPriority(registration.getContract(), registration.getStudent(), registration.getPriority()).isEmpty())
                            registrationRepository.save(registration);
                    }
                }
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

        Map<String, String> map = Stream.of(new String[][] {
                { "1", "Imi??" },
                { "2", "Nawisko" },
                { "3", "Wydzia??" },
                { "4", "Rok" },
                { "5", "Kierunek" },
                { "6", "Adres" },
                { "7", "Telefon" },
                { "8", "Email" },
                { "9", "??rednia" },
                { "10", "J??zyk" },
                { "11", "Poziom Egzaminu" },
                { "12", "Rodzaj certyfikatu" },
                { "13", "Umowa 1" },
                { "14", "Umowa 2" },
                { "15", "Umowa 3" },
                { "16", "Okres" },
                { "17", "Czy uczestniczy??" },
        }).collect(Collectors.toMap(data -> data[0], data -> data[1]));


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

                Student student = null;
                //1 dodanie nowego studenta
                if (studentRepository.findStudentByEditionAndEmail(edition.getId(), Array.get(arrays, map2.get("Email")).toString()).isEmpty()) {
                    student = new Student();
                    student.setName(Array.get(arrays, map2.get("Imi??")).toString());
                    student.setSurname(Array.get(arrays, map2.get("Nawisko")).toString());
                    student.setDepartment(Array.get(arrays, map2.get("Wydzia??")).toString());
                    student.setEmail(Array.get(arrays, map2.get("Email")).toString());
                    student.setYear(Array.get(arrays, map2.get("Rok")).toString());
                    student.setField(Array.get(arrays, map2.get("Kierunek")).toString());
                    student.setPhoneNumber(Array.get(arrays, map2.get("Telefon")).toString());
                    student.setEarlierParticipation(Array.get(arrays, map2.get("Czy uczestniczy??")).toString());
                    student.setPeriodOfStay(Array.get(arrays, map2.get("Okres")).toString());
                    student.setAverageGrade(Array.get(arrays, map2.get("??rednia")).toString());
                    student.setExamLevel(Array.get(arrays, map2.get("Poziom Egzaminu")).toString());
                    student.setTypeOfCertificate(Array.get(arrays, map2.get("Rodzaj certyfikatu")).toString());
                    student.setForeignLanguage(Array.get(arrays, map2.get("J??zyk")).toString());
                    student.setAddress(Array.get(arrays, map2.get("Adres")).toString());
                    studentRepository.save(student);
                    for (int i = 0; i < agreements.size(); i++) {
                        if (!agreements.get(i).equals("BRAK")) {
                            Registration registration = new Registration();
                            registration.setIsNominated(false);
                            registration.setIsAccepted(false);
                            registration.setStudent(student);
                            registration.setPriority(i+1);
                            ContractsCoordinator contractsCoordinator = contractCoordinatorRepository
                                    .findByName(getCoordinatorNameFromAgreement(agreements.get(i))).get();

                            String erasmusCode = getErasmusCodeFromAgreement(agreements.get(i));
                            String degree = getDegreeFromYear(Array.get(arrays, map2.get("Rok")).toString());

                            if (contractRepository.findByErasmusCodeAndContractsCoordinator_CodeAndEditionAndDegree(erasmusCode, contractsCoordinator.getCode(), edition, degree).isEmpty()) {
                                continue;
                            }

                            Contract contract = contractRepository.findByErasmusCodeAndContractsCoordinator_CodeAndEditionAndDegree(erasmusCode, contractsCoordinator.getCode(), edition, degree).get();
                            registration.setContract(contract);
                            if (registrationRepository.findByContractAndStudentAndPriority(registration.getContract(), registration.getStudent(), registration.getPriority()).isEmpty())
                                registrationRepository.save(registration);
                        }
                    }
                } else {
                    // 2 student istnieje ale zmienia dane
                    student = studentRepository.findStudentByEditionAndEmail(edition.getId(), Array.get(arrays, map2.get("Email")).toString()).get();
                    if (!student.getName().equals(Array.get(arrays, map2.get("Imi??")).toString()))
                        student.setName(Array.get(arrays, map2.get("Imi??")).toString());
                    if (!student.getSurname().equals(Array.get(arrays, map2.get("Nawisko")).toString()))
                        student.setSurname(Array.get(arrays, map2.get("Nawisko")).toString());
                    if (!student.getDepartment().equals(Array.get(arrays, map2.get("Wydzia??")).toString()))
                        student.setDepartment(Array.get(arrays, map2.get("Wydzia??")).toString());
//                    Je??eli student zmienia degree to zmienia wszystkie umowy
                    if (!getDegreeFromYear(student.getYear()).equals(getDegreeFromYear(Array.get(arrays, map2.get("Rok")).toString()))) {
                        for (Registration registration : registrationRepository.findByStudent_Id(student.getId())) {
                            registrationRepository.delete(registration);
                        }

                        for (int i = 0; i < agreements.size(); i++) {
                            if (!agreements.get(i).equals("BRAK")) {
                                Registration registration = new Registration();
                                registration.setIsNominated(false);
                                registration.setIsAccepted(false);
                                registration.setStudent(student);
                                registration.setPriority(i+1);

                                if (contractCoordinatorRepository.findByName(getCoordinatorNameFromAgreement(agreements.get(i))).isEmpty()) {
                                    continue;
                                }

                                ContractsCoordinator contractsCoordinator = contractCoordinatorRepository
                                        .findByName(getCoordinatorNameFromAgreement(agreements.get(i))).get();

                                String erasmusCode = getErasmusCodeFromAgreement(agreements.get(i));
                                String degree = getDegreeFromYear(Array.get(arrays, map2.get("Rok")).toString());
                                if (contractRepository.findByErasmusCodeAndContractsCoordinator_CodeAndEditionAndDegree(erasmusCode, contractsCoordinator.getCode(), edition, degree).isEmpty()) {
                                    continue;
                                }
                                Contract contract = contractRepository.findByErasmusCodeAndContractsCoordinator_CodeAndEditionAndDegree(erasmusCode, contractsCoordinator.getCode(), edition, degree).get();
                                registration.setContract(contract);
                                if (registrationRepository.findByContractAndStudentAndPriority(registration.getContract(), registration.getStudent(), registration.getPriority()).isEmpty())
                                    registrationRepository.save(registration);
                            }
                        }
                        student.setYear(Array.get(arrays, map2.get("Rok")).toString());
                    }
                    if (!student.getYear().equals(Array.get(arrays, map2.get("Rok")).toString()))
                        student.setYear(Array.get(arrays, map2.get("Rok")).toString());
                    if (!student.getField().equals(Array.get(arrays, map2.get("Kierunek")).toString()))
                        student.setField(Array.get(arrays, map2.get("Kierunek")).toString());
                    if (!student.getPhoneNumber().equals(Array.get(arrays, map2.get("Telefon")).toString()))
                        student.setPhoneNumber(Array.get(arrays, map2.get("Telefon")).toString());
                    if (!student.getEarlierParticipation().equals(Array.get(arrays, map2.get("Czy uczestniczy??")).toString()))
                        student.setEarlierParticipation(Array.get(arrays, map2.get("Czy uczestniczy??")).toString());
                    if (!student.getPeriodOfStay().equals(Array.get(arrays, map2.get("Okres")).toString()))
                        student.setPeriodOfStay(Array.get(arrays, map2.get("Okres")).toString());
                    if (!student.getExamLevel().equals(Array.get(arrays, map2.get("Poziom Egzaminu")).toString()))
                        student.setExamLevel(Array.get(arrays, map2.get("Poziom Egzaminu")).toString());
                    if (!student.getTypeOfCertificate().equals(Array.get(arrays, map2.get("Rodzaj certyfikatu")).toString()))
                        student.setTypeOfCertificate(Array.get(arrays, map2.get("Rodzaj certyfikatu")).toString());
                    if (!student.getForeignLanguage().equals(Array.get(arrays, map2.get("J??zyk")).toString()))
                        student.setForeignLanguage(Array.get(arrays, map2.get("J??zyk")).toString());
                    if (!student.getAverageGrade().equals(Array.get(arrays, map2.get("??rednia")).toString()))
                        student.setAverageGrade(Array.get(arrays, map2.get("??rednia")).toString());
                    if (!student.getAddress().equals(Array.get(arrays, map2.get("Adres")).toString()))
                        student.setAddress(Array.get(arrays, map2.get("Adres")).toString());

                    studentRepository.save(student);

                    // 3 student istnieje ale zmienia umowy
                    for (int i = 0; i < agreements.size(); i++) {
                        if (agreements.get(i).equals("BRAK") && registrationRepository.findByStudent_IdAndPriority(student.getId(), i+1).isEmpty()){
                            continue;
                        }


                        if (!agreements.get(i).equals("BRAK") && registrationRepository.findByStudent_IdAndPriority(student.getId(), i+1).isEmpty()) {
                            Registration registration = new Registration();
                            registration.setIsNominated(false);
                            registration.setIsAccepted(false);
                            registration.setStudent(student);
                            registration.setPriority(i+1);
                            ContractsCoordinator contractsCoordinator = contractCoordinatorRepository
                                    .findByName(getCoordinatorNameFromAgreement(agreements.get(i))).get();
                            String erasmusCode = getErasmusCodeFromAgreement(agreements.get(i));
                            String degree = getDegreeFromYear(Array.get(arrays, map2.get("Rok")).toString());

                            if (contractRepository.findByErasmusCodeAndContractsCoordinator_CodeAndEditionAndDegree(erasmusCode, contractsCoordinator.getCode(), edition, degree).isEmpty()) {
                                continue;
                            }

                            Contract contract = contractRepository.findByErasmusCodeAndContractsCoordinator_CodeAndEditionAndDegree(erasmusCode, contractsCoordinator.getCode(), edition, degree).get();
                            registration.setContract(contract);
                            if (registrationRepository.findByContractAndStudentAndPriority(registration.getContract(), registration.getStudent(), registration.getPriority()).isEmpty())
                                registrationRepository.save(registration);
                        }


                        if (agreements.get(i).equals("BRAK") && registrationRepository.findByStudent_IdAndPriority(student.getId(), i+1).isPresent()) {
                            Registration registration = registrationRepository.findByStudent_IdAndPriority(student.getId(), i + 1).get();
                            registrationRepository.delete(registration);
                        }

                        if (!agreements.get(i).equals("BRAK") && registrationRepository.findByStudent_IdAndPriority(student.getId(), i+1).isPresent()) {
                            Registration registration = registrationRepository.findByStudent_IdAndPriority(student.getId(), i + 1).get();

                            String erasmusCode = registration.getContract().getErasmusCode();
                            String degree = registration.getContract().getDegree();
                            String coordinatorName = registration.getContract().getContractsCoordinator().getName();
                            ContractsCoordinator contractsCoordinator = contractCoordinatorRepository.findByName(getCoordinatorNameFromAgreement(agreements.get(i))).get();
                            if (!getErasmusCodeFromAgreement(agreements.get(i)).equals(erasmusCode) ||
                                    !getDegreeFromYear(Array.get(arrays, map2.get("Rok")).toString()).equals(degree) ||
                                    !getCoordinatorNameFromAgreement(agreements.get(i)).equals(coordinatorName)) {

                                if (contractRepository.findByErasmusCodeAndContractsCoordinator_CodeAndEditionAndDegree(erasmusCode, contractsCoordinator.getCode(), edition, degree).isEmpty()) {
                                    continue;
                                }

                                Contract contract = contractRepository
                                        .findByErasmusCodeAndContractsCoordinator_CodeAndEditionAndDegree(getErasmusCodeFromAgreement(agreements.get(i)),
                                                contractsCoordinator.getCode(), edition, getDegreeFromYear(Array.get(arrays, map2.get("Rok")).toString())).get();
                                registration.setContract(contract);
                                if (registrationRepository.findByContractAndStudentAndPriority(registration.getContract(), registration.getStudent(), registration.getPriority()).isEmpty())
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
