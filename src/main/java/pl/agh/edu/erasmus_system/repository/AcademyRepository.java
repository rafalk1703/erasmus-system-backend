package pl.agh.edu.erasmus_system.repository;

import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.agh.edu.erasmus_system.model.Academy;
import pl.agh.edu.erasmus_system.model.ContractsCoordinator;

import java.util.Optional;


@Repository
public interface AcademyRepository extends JpaRepository<Academy, Long> {

    Optional<Academy> findByCode(String code);

}
