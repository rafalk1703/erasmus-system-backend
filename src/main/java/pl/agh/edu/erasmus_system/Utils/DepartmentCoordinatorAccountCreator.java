package pl.agh.edu.erasmus_system.Utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import pl.agh.edu.erasmus_system.model.ContractsCoordinator;
import pl.agh.edu.erasmus_system.model.CoordinatorRole;
import pl.agh.edu.erasmus_system.repository.ContractCoordinatorRepository;

@Component
public class DepartmentCoordinatorAccountCreator implements ApplicationRunner {

    private ContractCoordinatorRepository coordinatorRepository;

    @Autowired
    public DepartmentCoordinatorAccountCreator(ContractCoordinatorRepository coordinatorRepository) {
        this.coordinatorRepository = coordinatorRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (coordinatorRepository.findAll().size() == 0) {
            ContractsCoordinator coordinator = new ContractsCoordinator();
            coordinator.setEmail("es.admin");
            coordinator.setHash(PasswordManagement.generateHash("Esystem"));
            coordinator.setRole(CoordinatorRole.DEPARTMENT);
            coordinator.setName("Department coordinator");
            coordinator.setCode("DC");
            coordinator.setIfAccepted(false);

            coordinatorRepository.save(coordinator);
        }
    }
}
