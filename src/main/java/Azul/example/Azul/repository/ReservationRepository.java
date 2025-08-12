package Azul.example.Azul.repository;

import Azul.example.Azul.model.Reservation;
import Azul.example.Azul.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    // Trouver toutes les réservations d'un client avec fetch join
    @Query("SELECT r FROM Reservation r JOIN FETCH r.client JOIN FETCH r.atelier WHERE r.client = :client")
    List<Reservation> findByClientWithDetails(@Param("client") Client client);

    // Trouver toutes les réservations d'un atelier avec fetch join
    @Query("SELECT r FROM Reservation r JOIN FETCH r.client JOIN FETCH r.atelier WHERE r.atelier.id = :atelierId")
    List<Reservation> findByAtelierIdWithDetails(@Param("atelierId") Long atelierId);

    // Trouver toutes les réservations d'un client (méthode simple)
    List<Reservation> findByClient(Client client);

    // Trouver toutes les réservations d'un atelier (méthode simple)
    List<Reservation> findByAtelierId(Long atelierId);

    // Vérifier si une réservation existe pour un client et un atelier à une date donnée
    boolean existsByClientAndAtelierIdAndDateReservation(Client client, Long atelierId, java.sql.Date dateReservation);

    // Trouver une réservation par ID avec fetch join
    @Query("SELECT r FROM Reservation r JOIN FETCH r.client JOIN FETCH r.atelier WHERE r.id = :id")
    java.util.Optional<Reservation> findByIdWithDetails(@Param("id") Long id);
}