package Azul.example.Azul.repository;

import Azul.example.Azul.model.Reservation;
import Azul.example.Azul.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    // Trouver toutes les réservations d'un client
    List<Reservation> findByClient(Client client);

    // Trouver toutes les réservations d'un atelier
    List<Reservation> findByAtelierId(Long atelierId);

    // Vérifier si une réservation existe pour un client et un atelier à une date donnée
    boolean existsByClientAndAtelierIdAndDateReservation(Client client, Long atelierId, java.sql.Date dateReservation);
}