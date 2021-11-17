package pl.agh.edu.erasmus_system.services;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;
import pl.agh.edu.erasmus_system.model.Registration;
import pl.agh.edu.erasmus_system.model.Student;
import pl.agh.edu.erasmus_system.repositories.RegistrationRepository;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GenerateCSVFileService {

    @Autowired
    private RegistrationRepository registrationRepository;


    public void generateCSVFile(HttpServletResponse response, long editionId) throws IOException {

        List<Registration> acceptedRegistrations = registrationRepository
                .findAllByContract_Edition_IdAndIsAccepted(editionId)
                .stream().filter(registration -> !registration.getStudent().getDepartment().toUpperCase().equals("WIEIT"))
                .collect(Collectors.toList());
        Map<Student, String> acceptedStudents = acceptedRegistrations.stream()
                .collect(Collectors.toMap(Registration::getStudent, registration -> registration.getContract().getErasmusCode() +
                        " (" +registration.getContract().getContractsCoordinator().getCode() + ")"));


        List<StudentWithContract> studentWithContracts = new LinkedList<>();

        int index = 1;
        for (var student : acceptedStudents.entrySet()) {
            String lp = String.valueOf(index++);
            StudentWithContract studentWithContract = StudentWithContract.builder()
                    .lp(lp)
                    .contract(student.getValue())
                    .address(student.getKey().getAddress())
                    .department(student.getKey().getDepartment())
                    .name(student.getKey().getName())
                    .surname(student.getKey().getSurname())
                    .email(student.getKey().getEmail())
                    .field(student.getKey().getField())
                    .phoneNumber(student.getKey().getPhoneNumber())
                    .periodOfStay(student.getKey().getPeriodOfStay())
                    .earlierParticipation(student.getKey().getEarlierParticipation())
                    .averageGrade(student.getKey().getAverageGrade())
                    .examLevel(student.getKey().getExamLevel())
                    .typeOfCertificate(student.getKey().getTypeOfCertificate())
                    .foreignLanguage(student.getKey().getForeignLanguage())
                    .year(student.getKey().getYear())
                    .build();
            studentWithContracts.add(studentWithContract);

        }


        ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
        String[] csvHeader = {"Lp.", "Imię", "Nazwisko", "Wydział", "Rok", "Kierunek", "Adres", "Telefon", "Email", "Średnia ważona", "Język obcy", "Poziom egzaminu", "Rodzaj certyfikatu", "Planowane daty pobytu", "Czy student uczestniczył Programie Erasmus+", "Umowa" };
        String[] nameMapping = {"lp", "name", "surname", "department", "year", "field", "address", "phoneNumber", "email", "averageGrade", "foreignLanguage", "examLevel", "typeOfCertificate", "periodOfStay", "earlierParticipation", "contract"};

        csvWriter.writeHeader(csvHeader);

        for (StudentWithContract student : studentWithContracts) {
            csvWriter.write(student, nameMapping);
        }

        csvWriter.close();
    }

    public void generateCSVWIEITFile(HttpServletResponse response, long editionId) throws IOException {

        List<Registration> acceptedRegistrations = registrationRepository
                .findAllByContract_Edition_IdAndIsAccepted(editionId)
                .stream().filter(registration -> registration.getStudent().getDepartment().toUpperCase().equals("WIEIT"))
                .collect(Collectors.toList());
        Map<Student, String> acceptedStudents = acceptedRegistrations.stream()
                .collect(Collectors.toMap(Registration::getStudent, registration -> registration.getContract().getErasmusCode() +
                        " (" +registration.getContract().getContractsCoordinator().getCode() + ")"));

        List<StudentWithContract> studentWithContracts = new LinkedList<>();

        int index = 1;
        for (var student : acceptedStudents.entrySet()) {
            String lp = String.valueOf(index++);

            StudentWithContract studentWithContract = StudentWithContract.builder()
                    .lp(lp)
                    .contract(student.getValue())
                    .address(student.getKey().getAddress())
                    .department(student.getKey().getDepartment())
                    .name(student.getKey().getName())
                    .surname(student.getKey().getSurname())
                    .email(student.getKey().getEmail())
                    .field(student.getKey().getField())
                    .phoneNumber(student.getKey().getPhoneNumber())
                    .periodOfStay(student.getKey().getPeriodOfStay())
                    .earlierParticipation(student.getKey().getEarlierParticipation())
                    .averageGrade(student.getKey().getAverageGrade())
                    .examLevel(student.getKey().getExamLevel())
                    .typeOfCertificate(student.getKey().getTypeOfCertificate())
                    .foreignLanguage(student.getKey().getForeignLanguage())
                    .year(student.getKey().getYear())
                    .build();
            studentWithContracts.add(studentWithContract);

        }


        ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
        String[] csvHeader = {"Lp.", "Imię", "Nazwisko", "Wydział", "Rok", "Kierunek", "Adres", "Telefon", "Email", "Średnia ważona", "Język obcy", "Poziom egzaminu", "Rodzaj certyfikatu", "Planowane daty pobytu", "Czy student uczestniczył Programie Erasmus+", "Umowa" };
        String[] nameMapping = {"lp", "name", "surname", "department", "year", "field", "address", "phoneNumber", "email", "averageGrade", "foreignLanguage", "examLevel", "typeOfCertificate", "periodOfStay", "earlierParticipation", "contract"};

        csvWriter.writeHeader(csvHeader);

        for (StudentWithContract student : studentWithContracts) {
            csvWriter.write(student, nameMapping);
        }

        csvWriter.close();
    }

    @Builder
    @Getter
    @Setter
    public static class StudentWithContract {

        private String lp;
        private String name;
        private String surname;
        private String year;
        private String email;
        private String department;
        private String field;
        private String phoneNumber;
        private String periodOfStay;
        private String earlierParticipation;
        private String averageGrade;
        private String examLevel;
        private String typeOfCertificate;
        private String foreignLanguage;
        private String address;
        private String contract;
    }
}
