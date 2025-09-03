package Azul.example.Azul.service;

import Azul.example.Azul.dto.CreateReservationDTO;
import Azul.example.Azul.dto.ReservationResponseDTO;
import Azul.example.Azul.model.*;
import Azul.example.Azul.repository.AtelierRepository;
import Azul.example.Azul.repository.ReservationRepository;
import Azul.example.Azul.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @InjectMocks
    private ReservationService reservationService;

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AtelierRepository atelierRepository;

    private Client client;
    private Atelier atelier;
    private Reservation reservation;
    private CreateReservationDTO dto;

    @BeforeEach
    void setUp() {
        client = new Client();
        client.setId(10L);
        client.setFullName("Client Test");
        client.setEmail("client@test.com");

        atelier = new Atelier();
        atelier.setId(20L);
        atelier.setNom("Atelier Test");
        atelier.setDescription("Desc");
        atelier.setDate(Date.valueOf(LocalDate.now().plusDays(1)));

        reservation = new Reservation();
        reservation.setId(30L);
        reservation.setClient(client);
        reservation.setAtelier(atelier);
        reservation.setDateReservation(atelier.getDate());

        dto = new CreateReservationDTO();
        dto.setClientId(10L);
        dto.setAtelierId(20L);
        dto.setDateReservation(atelier.getDate());
    }


}


