package Azul.example.Azul.controller;

import Azul.example.Azul.dto.CreateAtelierDTO;
import Azul.example.Azul.model.Admin;
import Azul.example.Azul.model.Atelier;
import Azul.example.Azul.model.Coach;
import Azul.example.Azul.model.Utilisateur;
import Azul.example.Azul.repository.Coachrepo;
import Azul.example.Azul.repository.UserRepository;
import Azul.example.Azul.service.AtelierService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/api/ateliers")
public class AtelierController {

    private final AtelierService atelierService;
    private final Coachrepo coachrepo;
    private final UserRepository useRepo;

    @Autowired
    public AtelierController(AtelierService atelierService, Coachrepo coachrepo, UserRepository useRepo) {
        this.atelierService = atelierService;
        this.coachrepo = coachrepo;
        this.useRepo = useRepo;
    }

    @GetMapping
    public List<Atelier> getAllAteliers() {
        return atelierService.getAllAteliers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Atelier> getAtelierById(@PathVariable(value = "id") Long atelierId) {
        return atelierService.getAtelierById(atelierId)
                .map(atelier -> ResponseEntity.ok().body(atelier))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Atelier> createAtelier(@Valid @RequestBody CreateAtelierDTO atelierDTO) {
        Coach coach = coachrepo.findById(atelierDTO.getCoachId())
                .orElseThrow(() -> new RuntimeException("Coach not found"));

        Utilisateur adminUser = useRepo.findById(atelierDTO.getAdminId())
                .orElseThrow(() -> new RuntimeException("Admin not found"));
        
        if (!(adminUser instanceof Admin)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        Atelier atelier = new Atelier();
        atelier.setNom(atelierDTO.getNom());
        atelier.setDescription(atelierDTO.getDescription());
        atelier.setDate(atelierDTO.getDate());
        atelier.setHeure(atelierDTO.getHeure());
        atelier.setCoach(coach);
        atelier.setAdmin((Admin) adminUser);

        return ResponseEntity.status(HttpStatus.CREATED).body(atelierService.createAtelier(atelier));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Atelier> updateAtelier(@PathVariable(value = "id") Long atelierId,
                                                 @Valid @RequestBody Atelier atelierDetails) {
        Atelier updatedAtelier = atelierService.updateAtelier(atelierId, atelierDetails);
        return ResponseEntity.ok(updatedAtelier);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAtelier(@PathVariable(value = "id") Long atelierId) {
        atelierService.deleteAtelier(atelierId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search/nom")
    public List<Atelier> searchAteliersByNom(@RequestParam String nom) {
        return atelierService.findByNom(nom);
    }

    @GetMapping("/search/date")
    public List<Atelier> searchAteliersByDate(@RequestParam Date date) {
        return atelierService.findByDate(date);
    }

    @GetMapping("/search/coach/{coachId}")
    public List<Atelier> searchAteliersByCoach(@PathVariable Long coachId) {
        return atelierService.findByCoachId(coachId);
    }

    @GetMapping("/search/dates")
    public List<Atelier> searchAteliersByDateRange(@RequestParam Date dateDebut, @RequestParam Date dateFin) {
        return atelierService.findByDateBetween(dateDebut, dateFin);
    }
} 