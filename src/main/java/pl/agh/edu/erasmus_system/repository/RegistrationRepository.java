package pl.agh.edu.erasmus_system.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.agh.edu.erasmus_system.model.Contract;
import pl.agh.edu.erasmus_system.model.ContractsCoordinator;
import pl.agh.edu.erasmus_system.model.Registration;
import pl.agh.edu.erasmus_system.model.Student;

import java.util.List;
import java.util.Optional;

@Repository
public interface RegistrationRepository extends CrudRepository<Registration, Long> {
    /**
     * Returns all registrations for given contract
     * @param contract Contract object
     * @return list of registrations for given contract
     */
    @Query("select r from Registrations r " +
            "left join Contracts c on r.contract = c " +
            "where c = ?1 " +
            "order by r.priority, r.id")
    List<Registration> getALLRegistrationsByContract(Contract contract);
  
    List<Registration> findByContract(Contract contract);

    /**
     * Returns amount of nominated students for given contract
     * @param contract Contract object
     * @return amount of nominated students for given contract
     */
    @Query("select count(r) from Registrations r " +
            "left join Contracts c on r.contract = c " +
            "where (c = ?1 and r.isNominated=true)")
    long countNominatedStudentsByContract(Contract contract);

    /**
     * Returns amount of accepted students for given contract
     * @param contract Contract object
     * @return amount of accepted students for given contract
     */
    @Query("select count(r) from Registrations r " +
            "left join Contracts c on r.contract = c " +
            "where (c = ?1 and r.isAccepted=true)")
    long countAcceptedStudentsByContract(Contract contract);


    @Query("select r from Registrations r " +
            "left join Contracts c on r.contract = c " +
            "where c.edition.id = ?1 and r.isAccepted=true")
    List<Registration> findAllByContract_Edition_IdAndIsAccepted(long editionId);

    Optional<Registration> findByStudent_IdAndPriority(long studentId, Integer priority);

    Optional<Registration> findByContractAndStudentAndPriority(Contract contract, Student student, int priority);

    @Query("select r from Registrations r " +
            "left join Contracts c on r.contract = c " +
            "where c.edition.id = ?1")
    List<Registration> findByEditionId(long editionId);

    @Query("select r from Registrations r " +
            "left join Contracts c on r.contract = c " +
            "where c.edition.id = ?1 and c.contractsCoordinator = ?2")
    List<Registration> findByEditionIdAndCoordinator(long editionId, ContractsCoordinator coordinator);
}