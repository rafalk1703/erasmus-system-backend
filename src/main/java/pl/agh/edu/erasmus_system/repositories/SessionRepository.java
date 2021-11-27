package pl.agh.edu.erasmus_system.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.agh.edu.erasmus_system.model.Session;

import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {

    Optional<Session> findByCode(String code);
}