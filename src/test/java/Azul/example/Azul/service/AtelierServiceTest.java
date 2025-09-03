package Azul.example.Azul.service;

import Azul.example.Azul.dto.CreateAtelierDTO;
import Azul.example.Azul.model.*;
import Azul.example.Azul.repository.AtelierRepository;
import Azul.example.Azul.repository.Coachrepo;
import Azul.example.Azul.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AtelierServiceTest {

    @Mock
    private AtelierRepository atelierRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private Coachrepo coachrepo;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @InjectMocks
    private AtelierService atelierService;

    private Atelier atelier;
    private CreateAtelierDTO createAtelierDTO;
    private Admin admin;
    private Coach coach;

    @BeforeEach
    void setUp() {
        // Setup test data
        admin = new Admin();
        admin.setId(1L);
        admin.setEmail("admin@example.com");

        coach = new Coach();
        coach.setId(1L);
        coach.setFullName("Coach Test");

        atelier = new Atelier();
        atelier.setId(1L);
        atelier.setNom("Atelier Test");
        atelier.setDescription("Description Test");
        atelier.setDate(Date.valueOf(LocalDate.now()));
        atelier.setHeure(LocalTime.of(14, 0));
        atelier.setCategorie("ART");
        atelier.setAdmin(admin);
        atelier.setCoach(coach);

        createAtelierDTO = new CreateAtelierDTO();
        createAtelierDTO.setNom("New Atelier");
        createAtelierDTO.setDescription("New Description");
        createAtelierDTO.setDate(LocalDate.now().plusDays(1));
        createAtelierDTO.setHeure(LocalTime.of(15, 0));
        createAtelierDTO.setCategorie("ART");
        createAtelierDTO.setCoachId(1L);

    }




    @Test
    void testCreate_NonAdmin() {
        // Arrange
        Coach regularUser = new Coach();
        regularUser.setEmail("user@example.com");
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getName()).thenReturn("user@example.com");
        when(userRepository.findUserByEmail("user@example.com")).thenReturn(regularUser);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> atelierService.createAtelier(createAtelierDTO));
    }

    @Test
    void testUpdate() {
        // Arrange
        Atelier updatedAtelier = new Atelier();
        updatedAtelier.setNom("Updated Name");
        updatedAtelier.setDescription("Updated Description");
        updatedAtelier.setDate(Date.valueOf(LocalDate.now().plusDays(1)));
        updatedAtelier.setHeure(LocalTime.of(16, 0));
        updatedAtelier.setCoach(coach);

        when(atelierRepository.findById(1L)).thenReturn(Optional.of(atelier));
        when(atelierRepository.save(any(Atelier.class))).thenReturn(updatedAtelier);

        // Act
        Atelier result = atelierService.updateAtelier(1L, updatedAtelier);

        // Assert
        assertNotNull(result);
        assertEquals("Updated Name", result.getNom());
        assertEquals("Updated Description", result.getDescription());
    }


    @Test
    void testDelete() {
        // Arrange
        doNothing().when(atelierRepository).deleteById(1L);

        // Act
        atelierService.deleteAtelier(1L);

        // Assert
        verify(atelierRepository, times(1)).deleteById(1L);
    }




    @Test
    void testGetAll() {
        // Arrange
        when(atelierRepository.findAll()).thenReturn(Arrays.asList(atelier));

        // Act
        List<Atelier> result = atelierService.getAllAteliers();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(atelier.getNom(), result.get(0).getNom());
    }

    @Test
    void testGetById() {
        // Arrange
        when(atelierRepository.findById(1L)).thenReturn(Optional.of(atelier));

        // Act
        Optional<Atelier> result = atelierService.getAtelierById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(atelier.getNom(), result.get().getNom());
    }

    @Test
    void testGetById_NotFound() {
        // Arrange
        when(atelierRepository.findById(999L)).thenReturn(Optional.empty());
        // Act
        Optional<Atelier> result = atelierService.getAtelierById(999L);
        // Assert
        assertTrue(result.isEmpty());
    }


}
