package pl.agh.edu.erasmus_system.repository;

import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.agh.edu.erasmus_system.model.Academy;


@Repository
public interface AcademyRepository extends JpaRepository<Academy, Long> {

}
