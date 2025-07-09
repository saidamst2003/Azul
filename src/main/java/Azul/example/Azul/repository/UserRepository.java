package Azul.example.Azul.repository;


import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username) ;


    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}
