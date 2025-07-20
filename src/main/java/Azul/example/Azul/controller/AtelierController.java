package Azul.example.Azul.controller;

import Azul.example.Azul.model.Atelier;
import Azul.example.Azul.service.AtelierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ateliers")
@CrossOrigin
public class AtelierController {
    private final AtelierService atelierService;

    @Autowired
    public AtelierController(AtelierService atelierService) {
        this.atelierService = atelierService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<Atelier> getAllAteliers() {
        return atelierService.getAllAteliers();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Atelier> getAtelierById(@PathVariable Long id) {
        return atelierService.getAtelierById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Atelier> createAtelier(@RequestBody Atelier atelier) {
        Atelier created = atelierService.createAtelier(atelier);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Atelier> updateAtelier(@PathVariable Long id, @RequestBody Atelier atelier) {
        Atelier updated = atelierService.updateAtelier(id, atelier);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteAtelier(@PathVariable Long id) {
        atelierService.deleteAtelier(id);
        return ResponseEntity.noContent().build();
    }
} 