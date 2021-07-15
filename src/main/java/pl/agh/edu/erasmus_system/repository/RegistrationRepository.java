package pl.agh.edu.erasmus_system.repository;

import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.agh.edu.erasmus_system.model.Contract;
import pl.agh.edu.erasmus_system.model.Registration;

import java.util.*;


@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Long> {

    List<Registration> findByContract(Contract contract);
}
