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

    @Test
    void testCreateReservation() throws Exception {
        when(userRepository.findById(10L)).thenReturn(Optional.of(client));
        when(atelierRepository.findById(20L)).thenReturn(Optional.of(atelier));
        when(reservationRepository.existsByClientAndAtelierIdAndDateReservation(client, 20L, atelier.getDate()))
                .thenReturn(false);
        when(reservationRepository.save(any(Reservation.class)))
                .thenAnswer(invocation -> {
                    Reservation r = invocation.getArgument(0);
                    r.setId(30L);
                    return r;
                });
        when(reservationRepository.findByIdWithDetails(30L)).thenReturn(Optional.of(reservation));

        ReservationResponseDTO response = reservationService.createReservation(dto);

        assertNotNull(response);
        assertEquals(30L, response.getId());
        assertEquals(10L, response.getClientId());
        assertEquals(20L, response.getAtelierId());
        verify(reservationRepository, times(1)).save(any(Reservation.class));
    }





    @Test
    void testGetReservationsByClient() throws Exception {
        when(userRepository.findById(10L)).thenReturn(Optional.of(client));
        when(reservationRepository.findByClientWithDetails(client)).thenReturn(List.of(reservation));

        List<ReservationResponseDTO> list = reservationService.getReservationsByClient(10L);

        assertEquals(1, list.size());
        assertEquals(20L, list.get(0).getAtelierId());
    }



    @Test
    void testGetReservationById() throws Exception {
        when(reservationRepository.findByIdWithDetails(30L)).thenReturn(Optional.of(reservation));
        ReservationResponseDTO resp = reservationService.getReservationById(30L);
        assertEquals(30L, resp.getId());
        assertEquals(20L, resp.getAtelierId());
    }



    @Test
    void testDeleteReservation() throws Exception {
        when(reservationRepository.findById(30L)).thenReturn(Optional.of(reservation));
        doNothing().when(reservationRepository).deleteById(30L);

        reservationService.deleteReservation(30L);
        verify(reservationRepository, times(1)).deleteById(30L);
    }

    @Test
    void testDeleteReservation_NotFound() {
        when(reservationRepository.findById(30L)).thenReturn(Optional.empty());
        Exception ex = assertThrows(Exception.class, () -> reservationService.deleteReservation(30L));
        assertTrue(ex.getMessage().contains("Réservation non trouvée"));
    }
}


