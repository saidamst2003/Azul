package Azul.example.Azul.service;

import Azul.example.Azul.model.*;
import Azul.example.Azul.repository.AtelierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class AtelierService {
    private final AtelierRepository atelierRepository;
    private final FileStorageService fileStorageService;

    @Autowired
    public AtelierService(AtelierRepository atelierRepository, FileStorageService fileStorageService) {
        this.atelierRepository = atelierRepository;
        this.fileStorageService = fileStorageService;
    }

    public List<Atelier> getAllAteliers() {
        return atelierRepository.findAll();
    }

    public Optional<Atelier> getAtelierById(Long id) {
        return atelierRepository.findById(id);
    }

    public Atelier createAtelier(Atelier atelier, MultipartFile file) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Utilisateur currentUser = userPrincipal.getUtilisateur().orElseThrow(() -> new RuntimeException("User not found in principal"));

        if (currentUser instanceof Admin) {
            atelier.setAdmin((Admin) currentUser);
        } else {
            throw new RuntimeException("Only admins can create ateliers");
        }

        String filename = fileStorageService.save(file);
        Photo photo = new Photo(filename);
        photo.setAtelier(atelier);
        atelier.getPhotos().add(photo);

        return atelierRepository.save(atelier);
    }

    public Atelier updateAtelier(Long id, Atelier atelierDetails) {
        return atelierRepository.findById(id)
                .map(atelier -> {
                    atelier.setNom(atelierDetails.getNom());
                    atelier.setDescription(atelierDetails.getDescription());
                    atelier.setDate(atelierDetails.getDate());
                    atelier.setHeure(atelierDetails.getHeure());
                    atelier.setCoach(atelierDetails.getCoach());
                    // L'admin ne doit pas être modifié
                    return atelierRepository.save(atelier);
                })
                .orElseThrow(() -> new RuntimeException("Atelier not found"));
    }

    public void deleteAtelier(Long id) {
        atelierRepository.deleteById(id);
    }
}