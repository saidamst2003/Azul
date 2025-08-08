package Azul.example.Azul.repository;

import Azul.example.Azul.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    Optional<Reservation> findByAtelierIdAndUserId(Long atelierId, Long userId);
    List<Reservation> findAllByUserId(Long userId);
    List<Reservation> findAllByAtelierId(Long atelierId);
}
