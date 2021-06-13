package pl.agh.edu.erasmus_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.agh.edu.erasmus_system.model.*;
import pl.agh.edu.erasmus_system.repository.*;

import java.util.List;

@RestController
@RequestMapping("api/database/")
@CrossOrigin
public class DatabaseTestController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private EditionRepository editionRepository;

    @Autowired
    private AcademyRepository academyRepository;

    @Autowired
    private ContractCoordinatorRepository contractCoordinatorRepository;

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private RegistrationRepository registrationRepository ;

    @GetMapping("addstudent")
    public void getUser() {
        Student student = new Student();
        student.setDegree(Degree.BACHELOR);
        student.setName("Jan");
        student.setSurname("Kowalski");
        student.setDepartment("WIET");
        student.setYear(Year.FIFTH);
        student.setField("Informatyka");
        student.setEmail("jk@mail.com");
        studentRepository.save(student);

        Edition edition = new Edition();
        edition.setYear(2020);
        editionRepository.save(edition);


        ContractsCoordinator contractsCoordinator = new ContractsCoordinator();
        contractsCoordinator.setName("Jakub Nowak");
        contractsCoordinator.setEmail("jn@mail.com");
        contractsCoordinator.setHash("abcd123");
        contractCoordinatorRepository.save(contractsCoordinator);

        Academy academy = new Academy();
        academy.setName("AGH");
        academy.setCity("Kraków");
        academy.setCountry("Polska");
        academy.setCode("SDSDS");
        academyRepository.save(academy);

        Contract contract = new Contract();
        contract.setContractsCoordinator(contractsCoordinator);
        contract.setErasmusCode("ASDF");
        contract.setAcademy(academy);
        contract.setDegree(Degree.BACHELOR);
        contract.setVacancies(2);
        contract.setStartYear(2019);
        contract.setEndYear(2021);
        contract.setEdition(edition);
        contractRepository.save(contract);

        Registration registration = new Registration();
        registration.setContract(contract);
        registration.setStudent(student);
        registration.setIsAccepted(Boolean.FALSE);
        registration.setIsNominated(Boolean.TRUE);
        registration.setPriority(1);
        registrationRepository.save(registration);

    }

    @GetMapping("getstudent")
    public List<Student> getStudent() {
        return studentRepository.findAll();
    }
}
