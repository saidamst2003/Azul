package Azul.example.Azul.repository;


import org.apache.catalina.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username) ;
    Page<User> findByRolesName(String roleName, Pageable pageable);


    Optional<User> findUserByEmail(String email);

    boolean existsByEmail(String email);
}
