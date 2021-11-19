package pl.agh.edu.erasmus_system.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.agh.edu.erasmus_system.model.ContractsCoordinator;
import pl.agh.edu.erasmus_system.model.Student;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByEmail(String email);

    Boolean existsByEmail(String email);

    Optional<Student> findByEmailAndYear(String email, String year);

    @Query("select r.student from Registrations r " +
            "left join Contracts c on r.contract = c " +
            "where c.edition.id = ?1 and r.student.email = ?2")
    Optional<Student> findStudentByEditionAndEmail(long editionId, String email);

    @Query("select distinct r.student from Registrations r " +
            "left join Contracts c on r.contract = c " +
            "where c.edition.id = ?1 " +
            "order by r.student.name, r.student.surname")
    List<Student> findStudentsByEditionId(long editionId);

    @Query("select distinct r.student from Registrations r " +
            "left join Contracts c on r.contract = c " +
            "where c.edition.id = ?1 and c.contractsCoordinator = ?2 " +
            "order by r.student.name, r.student.surname")
    List<Student> findStudentsByEditionAndCoordinator(long editionId, ContractsCoordinator coordinator);
}
