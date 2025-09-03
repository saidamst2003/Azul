package Azul.example.Azul.service;

import Azul.example.Azul.dto.CreateCoachDTO;
import Azul.example.Azul.dto.UpdateCoachDTO;
import Azul.example.Azul.model.Coach;
import Azul.example.Azul.model.Role;
import Azul.example.Azul.repository.Coachrepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CoachServiceTest {

    @InjectMocks
    private CoachService coachService;

    @Mock
    private Coachrepo coachrepo;

    @Mock
    private PasswordEncoder passwordEncoder;

    private Coach coach;

    @BeforeEach
    void setUp() {
        coach = new Coach();
        coach.setId(1L);
        coach.setFullName("John Doe");
        coach.setEmail("john@example.com");
        coach.setSpecialite("Yoga");
        coach.setRole(Role.COACH);
    }

    @Test
    void testGetAllCoaches() {
        when(coachrepo.findAll()).thenReturn(Arrays.asList(coach));
        List<Coach> result = coachService.getAllCoaches();
        assertEquals(1, result.size());
    }

    @Test
    void testGetCoachById() {
        when(coachrepo.findById(1L)).thenReturn(Optional.of(coach));
        Coach result = coachService.getCoachById(1L);
        assertNotNull(result);
        assertEquals("John Doe", result.getFullName());
    }

    @Test
    void testCreateCoach() {
        CreateCoachDTO dto = new CreateCoachDTO("Jane Doe", "jane@example.com", "secret12", "Fitness");
        when(passwordEncoder.encode("secret12")).thenReturn("enc");
        when(coachrepo.save(any(Coach.class))).thenAnswer(inv -> inv.getArgument(0));

        Coach saved = coachService.createCoach(dto);
        assertEquals(Role.COACH, saved.getRole());
        assertEquals("jane@example.com", saved.getEmail());
        assertEquals("enc", saved.getPassword());
    }

    @Test
    void testUpdateCoach() {
        when(coachrepo.findById(1L)).thenReturn(Optional.of(coach));
        when(passwordEncoder.encode("newpass")).thenReturn("enc2");
        when(coachrepo.save(any(Coach.class))).thenAnswer(inv -> inv.getArgument(0));

        UpdateCoachDTO dto = new UpdateCoachDTO("New Name", "new@example.com", "newpass", "Pilates");
        Coach updated = coachService.updateCoach(1L, dto);

        assertEquals("New Name", updated.getFullName());
        assertEquals("new@example.com", updated.getEmail());
        assertEquals("enc2", updated.getPassword());
        assertEquals("Pilates", updated.getSpecialite());
    }

    @Test
    void testUpdateCoach_NotFound() {
        when(coachrepo.findById(2L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> coachService.updateCoach(2L, new UpdateCoachDTO(null, null, null, null)));
    }

    @Test
    void testDeleteCoach() {
        when(coachrepo.existsById(1L)).thenReturn(true);
        doNothing().when(coachrepo).deleteById(1L);
        coachService.deleteCoach(1L);
        verify(coachrepo, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteCoach_NotFound() {
        when(coachrepo.existsById(9L)).thenReturn(false);
        assertThrows(RuntimeException.class, () -> coachService.deleteCoach(9L));
    }
}


