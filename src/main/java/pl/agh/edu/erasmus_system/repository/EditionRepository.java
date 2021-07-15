package pl.agh.edu.erasmus_system.repository;

import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.agh.edu.erasmus_system.model.Edition;

import java.util.*;


@Repository
public interface EditionRepository extends JpaRepository<Edition, Long> {
    Optional<Edition> findByYear(String year);

    List<Edition> findByIsActiveIsTrue();

}
