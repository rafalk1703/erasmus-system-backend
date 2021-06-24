package pl.agh.edu.erasmus_system.repository;

import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.agh.edu.erasmus_system.model.Contract;

import java.util.Optional;


@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {

    Optional<Contract> findByErasmusCodeAndContractsCoordinator_Code(String erasmusCode, String contractCoordinatorCode);
}
