package pl.agh.edu.erasmus_system.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.agh.edu.erasmus_system.model.Contract;
import pl.agh.edu.erasmus_system.model.Registration;

import java.util.List;

@Repository
public interface RegistrationRepository extends CrudRepository<Registration, Long> {
    /**
     * Returns all registrations for given contract
     * @param contract Contract object
     * @return list of registrations for given contract
     */
    @Query("select r from Registrations r " +
            "left join Contracts c on r.contract = c " +
            "where c = ?1")
    List<Registration> getALLRegistrationsByContract(Contract contract);
  
    List<Registration> findByContract(Contract contract);

    /**
     * Returns amount of accepted students for given contract
     * @param contract Contract object
     * @return amount of accepted students for given contract
     */
    @Query("select count(r) from Registrations r " +
            "left join Contracts c on r.contract = c " +
            "where (c = ?1 and r.isAccepted=true)")
    long countAcceptedStudentsByContract(Contract contract);
}