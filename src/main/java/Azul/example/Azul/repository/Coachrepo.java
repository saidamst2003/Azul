package Azul.example.Azul.repository;

import Azul.example.Azul.model.Coach;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface Coachrepo extends JpaRepository<Coach, Long> {

    // Recherche par email
    Optional<Coach> findByEmail(String email);

    // add coach specific queries here if needed

}
