package Azul.example.Azul.controller;

import Azul.example.Azul.model.Atelier;
import Azul.example.Azul.service.AtelierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public List<Atelier> getAllAteliers() {
        return atelierService.getAllAteliers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Atelier> getAtelierById(@PathVariable Long id) {
        return atelierService.getAtelierById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(consumes = { "multipart/form-data" })
    public ResponseEntity<Atelier> createAtelier(@RequestPart("atelier") Atelier atelier, @RequestPart("file") MultipartFile file) {
        Atelier created = atelierService.createAtelier(atelier, file);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Atelier> updateAtelier(@PathVariable Long id, @RequestBody Atelier atelier) {
        Atelier updated = atelierService.updateAtelier(id, atelier);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAtelier(@PathVariable Long id) {
        atelierService.deleteAtelier(id);
        return ResponseEntity.noContent().build();
    }
} 