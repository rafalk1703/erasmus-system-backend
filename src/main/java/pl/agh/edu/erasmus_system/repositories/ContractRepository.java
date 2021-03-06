package pl.agh.edu.erasmus_system.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.agh.edu.erasmus_system.model.Contract;
import pl.agh.edu.erasmus_system.model.ContractsCoordinator;
import pl.agh.edu.erasmus_system.model.Edition;

import java.util.Optional;
import java.util.List;


@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {

    Optional<Contract> findByErasmusCodeAndContractsCoordinator_Code(String erasmusCode, String contractCoordinatorCode);

    Optional<Contract> findByErasmusCodeAndContractsCoordinator_CodeAndDegree(String erasmusCode, String contractCoordinatorCode, String degree);

    Optional<Contract> findByErasmusCodeAndContractsCoordinator_CodeAndEdition(String erasmusCode, String contractCoordinatorCode, Edition edition);

    Optional<Contract> findByErasmusCodeAndContractsCoordinator_CodeAndEditionAndDegree(String erasmusCode, String contractCoordinatorCode, Edition edition, String degree);

    @Query("select c from Contracts c " +
            "where c.edition.id = ?1 " +
            "order by c.id")
    List<Contract> findByEdition_Id(long editionId);

    List<Contract> findByEdition_Year(String editionYear);

    List<Contract> findByEdition_YearAndContractsCoordinator_Code(String editionYear, String code);

    List<Contract> findByEdition_IdAndContractsCoordinator_Code(long editionId, String code);

    @Query("select c from Contracts c " +
            "where c.edition.id = ?1 and c.contractsCoordinator = ?2 " +
            "order by c.id")
    List<Contract> findByEditionIdAndContractsCoordinator(long editionId, ContractsCoordinator coordinator);
}
