package Azul.example.Azul.service;

import Azul.example.Azul.dto.CreateReservationDTO;
import Azul.example.Azul.dto.ReservationResponseDTO;
import Azul.example.Azul.model.Reservation;
import Azul.example.Azul.model.Client;
import Azul.example.Azul.model.Atelier;
import Azul.example.Azul.repository.ReservationRepository;
import Azul.example.Azul.repository.AtelierRepository;
import Azul.example.Azul.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AtelierRepository atelierRepository;

    // Créer une nouvelle réservation
    public ReservationResponseDTO createReservation(CreateReservationDTO dto) throws Exception {
        // Vérifier que le client existe
        Optional<Client> clientOpt = userRepository.findById(dto.getClientId())
                .filter(user -> user instanceof Client)
                .map(user -> (Client) user);

        if (clientOpt.isEmpty()) {
            throw new Exception("Client non trouvé ou utilisateur non autorisé");
        }

        // Vérifier que l'atelier existe
        Optional<Atelier> atelierOpt = atelierRepository.findById(dto.getAtelierId());
        if (atelierOpt.isEmpty()) {
            throw new Exception("Atelier non trouvé");
        }

        Client client = clientOpt.get();
        Atelier atelier = atelierOpt.get();

        // Vérifier si une réservation existe déjà pour ce client et cet atelier à cette date
        if (reservationRepository.existsByClientAndAtelierIdAndDateReservation(
                client, dto.getAtelierId(), dto.getDateReservation())) {
            throw new Exception("Une réservation existe déjà pour cet atelier à cette date");
        }

        // Créer la réservation
        Reservation reservation = new Reservation();
        reservation.setClient(client);
        reservation.setAtelier(atelier);
        reservation.setDateReservation(dto.getDateReservation());

        Reservation savedReservation = reservationRepository.save(reservation);

        // Récupérer la réservation avec les détails pour éviter les problèmes de lazy loading
        Optional<Reservation> reservationWithDetails = reservationRepository.findByIdWithDetails(savedReservation.getId());

        // Convertir en DTO de réponse
        return convertToResponseDTO(reservationWithDetails.get());
    }

    // Obtenir toutes les réservations d'un client
    public List<ReservationResponseDTO> getReservationsByClient(Long clientId) throws Exception {
        Optional<Client> clientOpt = userRepository.findById(clientId)
                .filter(user -> user instanceof Client)
                .map(user -> (Client) user);

        if (clientOpt.isEmpty()) {
            throw new Exception("Client non trouvé");
        }

        List<Reservation> reservations = reservationRepository.findByClientWithDetails(clientOpt.get());
        return reservations.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    // Obtenir toutes les réservations d'un atelier
    public List<ReservationResponseDTO> getReservationsByAtelier(Long atelierId) {
        List<Reservation> reservations = reservationRepository.findByAtelierIdWithDetails(atelierId);
        return reservations.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    // Supprimer une réservation
    public void deleteReservation(Long reservationId) throws Exception {
        Optional<Reservation> reservationOpt = reservationRepository.findById(reservationId);
        if (reservationOpt.isEmpty()) {
            throw new Exception("Réservation non trouvée");
        }

        reservationRepository.deleteById(reservationId);
    }

    // Obtenir une réservation par ID
    public ReservationResponseDTO getReservationById(Long reservationId) throws Exception {
        Optional<Reservation> reservationOpt = reservationRepository.findByIdWithDetails(reservationId);
        if (reservationOpt.isEmpty()) {
            throw new Exception("Réservation non trouvée");
        }

        return convertToResponseDTO(reservationOpt.get());
    }

    // Méthode utilitaire pour convertir Reservation en ReservationResponseDTO
    private ReservationResponseDTO convertToResponseDTO(Reservation reservation) {
        return new ReservationResponseDTO(
                reservation.getId(),
                reservation.getDateReservation(),
                reservation.getClient().getId(),
                reservation.getClient().getFullName(),
                reservation.getClient().getEmail(),
                reservation.getAtelier().getId(),
                reservation.getAtelier().getNom(),
                reservation.getAtelier().getDescription()
        );
    }
}