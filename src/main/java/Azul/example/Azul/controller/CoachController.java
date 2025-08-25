package Azul.example.Azul.controller;

import Azul.example.Azul.dto.CreateCoachDTO;
import Azul.example.Azul.dto.UpdateCoachDTO;
import Azul.example.Azul.model.Coach;
import Azul.example.Azul.model.Role;
import Azul.example.Azul.repository.Coachrepo;
import Azul.example.Azul.service.AtelierService;
import Azul.example.Azul.service.CoachService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/coaches")
@CrossOrigin(origins = "http://localhost:4200")
public class CoachController {

    private final CoachService coachService;
    private final PasswordEncoder passwordEncoder;
    private final Coachrepo coachrepo;

    @Autowired
    public CoachController(CoachService coachService, PasswordEncoder passwordEncoder, Coachrepo coachrepo) {
        this.coachService = coachService;
        this.passwordEncoder = passwordEncoder;

        this.coachrepo = coachrepo;
    }
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT')")
    public ResponseEntity<List<Coach>> getAllCoaches() {
        List<Coach> coaches = coachService.getAllCoaches();
        return ResponseEntity.ok(coaches);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT')")
    public ResponseEntity<Coach> getCoachById(@PathVariable Long id) {
        Coach coach = coachService.getCoachById(id);
        if (coach != null) {
            return ResponseEntity.ok(coach);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/create")
    public ResponseEntity<Coach> createCoach(@Valid @RequestBody CreateCoachDTO dto) {
        Coach coach = new Coach();
        coach.setFullName(dto.fullName());
        coach.setEmail(dto.email());
        coach.setPassword(passwordEncoder.encode(dto.password())); // encode password
        coach.setRole(Role.COACH); // role par défaut
        coach.setSpecialite(dto.specialite());

        Coach savedCoach = coachrepo.save(coach);
        return new ResponseEntity<>(savedCoach, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Coach> updateCoach(@PathVariable Long id, @Valid @RequestBody UpdateCoachDTO coachDetails) {
        try {
            Coach updated = coachService.updateCoach(id, coachDetails);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteCoach(@PathVariable Long id) {
        try {
            coachService.deleteCoach(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}