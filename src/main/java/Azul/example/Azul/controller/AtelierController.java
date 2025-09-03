package Azul.example.Azul.controller;

import Azul.example.Azul.dto.CreateAtelierDTO;
import Azul.example.Azul.model.Atelier;
import Azul.example.Azul.service.AtelierService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/ateliers")
@CrossOrigin(origins = "*")
public class AtelierController {

    private final AtelierService atelierService;

    @Autowired
    public AtelierController(AtelierService atelierService) {
        this.atelierService = atelierService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT', 'COACH')")
    public ResponseEntity<List<Atelier>> getAllAteliers() {
        List<Atelier> ateliers = atelierService.getAllAteliers();
        return new ResponseEntity<>(ateliers, HttpStatus.OK);
    }

    @GetMapping("/coach/{coachId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT', 'COACH')")
    public ResponseEntity<List<Atelier>> getAteliersByCoach(@PathVariable Long coachId) {
        List<Atelier> ateliers = atelierService.getAteliersByCoachId(coachId);
        return new ResponseEntity<>(ateliers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT', 'COACH')")
    public ResponseEntity<Atelier> getAtelierById(@PathVariable Long id) {
        return atelierService.getAtelierById(id)
                .map(atelier -> new ResponseEntity<>(atelier, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT', 'COACH')")
    public ResponseEntity<Atelier> createAtelier(@Valid @RequestBody CreateAtelierDTO dto) {
        Atelier atelier = atelierService.createAtelier(dto);
        return ResponseEntity.ok(atelier);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT', 'COACH')")
    public ResponseEntity<Atelier> updateAtelier(@PathVariable Long id,
                                                 @RequestBody Atelier atelierDetails) {
        try {
            Atelier updated = atelierService.updateAtelier(id, atelierDetails);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT', 'COACH')")
    public ResponseEntity<Void> deleteAtelier(@PathVariable Long id) {
        atelierService.deleteAtelier(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}