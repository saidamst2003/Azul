package Azul.example.Azul.repository;

import Azul.example.Azul.model.Admin;
import Azul.example.Azul.model.Coach;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepo extends JpaRepository<Admin, Long> {

    // Recherche par email
    Optional<Admin> findByEmail(String email);

}
