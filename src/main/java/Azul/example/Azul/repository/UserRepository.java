package Azul.example.Azul.repository;

import Azul.example.Azul.model.Utilisateur;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Utilisateur, Long> {
    Utilisateur findByUsername(String username);
    Page<Utilisateur> findByRolesName(String roleName, Pageable pageable);
    Optional<Utilisateur> findUserByEmail(String email);
    boolean existsByEmail(String email);
}
