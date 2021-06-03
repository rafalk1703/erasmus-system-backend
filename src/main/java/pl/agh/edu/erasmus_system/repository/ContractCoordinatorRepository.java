package pl.agh.edu.erasmus_system.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.agh.edu.erasmus_system.model.Student;


@Repository
public interface ContractCoordinatorRepository extends JpaRepository<Student, Long> {

}