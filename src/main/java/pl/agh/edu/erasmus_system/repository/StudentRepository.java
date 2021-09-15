package pl.agh.edu.erasmus_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.agh.edu.erasmus_system.model.Student;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByEmail(String email);

    Boolean existsByEmail(String email);

    Optional<Student> findByEmailAndYear(String email, String year);

}
