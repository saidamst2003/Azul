package Azul.example.Azul.repository;


import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UseRepo extends JpaRepository<User, Long> {
    User findUserByEmail(String email);
}
