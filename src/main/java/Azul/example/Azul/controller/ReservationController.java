package Azul.example.Azul.controller;

import Azul.example.Azul.dto.CreateReservationDTO;
import Azul.example.Azul.dto.ReservationResponseDTO;
import Azul.example.Azul.service.ReservationService;
import Azul.example.Azul.service.AtelierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
@CrossOrigin(origins = "*")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private AtelierService atelierService;

    // Créer une nouvelle réservation (seuls les clients peuvent réserver)
    @PostMapping
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<?> createReservation(@RequestBody CreateReservationDTO dto) {
        try {
            ReservationResponseDTO reservation = reservationService.createReservation(dto);
            return ResponseEntity.ok(reservation);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur lors de la création de la réservation: " + e.getMessage());
        }
    }

    // Obtenir toutes les réservations d'un client
    @GetMapping("/client/{clientId}")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<?> getReservationsByClient(@PathVariable Long clientId) {
        try {
            List<ReservationResponseDTO> reservations = reservationService.getReservationsByClient(clientId);
            return ResponseEntity.ok(reservations);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur lors de la récupération des réservations: " + e.getMessage());
        }
    }

    // Obtenir toutes les réservations d'un atelier (pour les admins/coaches)
    @GetMapping("/atelier/{atelierId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'COACH')")
    public ResponseEntity<?> getReservationsByAtelier(@PathVariable Long atelierId) {
        try {
            List<ReservationResponseDTO> reservations = reservationService.getReservationsByAtelier(atelierId);
            return ResponseEntity.ok(reservations);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur lors de la récupération des réservations: " + e.getMessage());
        }
    }

    // Obtenir une réservation par ID
    @GetMapping("/{reservationId}")
    @PreAuthorize("hasAnyRole('CLIENT', 'ADMIN', 'COACH')")
    public ResponseEntity<?> getReservationById(@PathVariable Long reservationId) {
        try {
            ReservationResponseDTO reservation = reservationService.getReservationById(reservationId);
            return ResponseEntity.ok(reservation);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur lors de la récupération de la réservation: " + e.getMessage());
        }
    }

    // Supprimer une réservation
    @DeleteMapping("/{reservationId}")
    @PreAuthorize("hasAnyRole('CLIENT', 'ADMIN')")
    public ResponseEntity<?> deleteReservation(@PathVariable Long reservationId) {
        try {
            reservationService.deleteReservation(reservationId);
            return ResponseEntity.ok("Réservation supprimée avec succès");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur lors de la suppression de la réservation: " + e.getMessage());
        }
    }

    // Endpoint pour obtenir tous les ateliers disponibles (pour l'affichage)
    @GetMapping("/ateliers")
    public ResponseEntity<?> getAllAteliers() {
        try {
            return ResponseEntity.ok(atelierService.getAllAteliers());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur lors de la récupération des ateliers: " + e.getMessage());
        }
    }
}