package Azul.example.Azul.service;

import Azul.example.Azul.dto.CreateAtelierDTO;
import Azul.example.Azul.model.*;
import Azul.example.Azul.repository.AdminRepo;
import Azul.example.Azul.repository.AtelierRepository;
import Azul.example.Azul.repository.Coachrepo;
import Azul.example.Azul.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AtelierService {

    private final AtelierRepository atelierRepository;
    private final UserRepository userRepository;
    private final Coachrepo coachrepo;
    @Autowired
    public AtelierService(AtelierRepository atelierRepository, UserRepository userRepository, Coachrepo coachrepo, AdminRepo adminRepo) {
        this.atelierRepository = atelierRepository;
        this.userRepository = userRepository;
        this.coachrepo = coachrepo;
    }

    public List<Atelier> getAllAteliers() {
        return atelierRepository.findAll();
    }

    public List<Atelier> getAllAteliersWithCoachDetails() {
        return atelierRepository.findAllWithCoachDetails();
    }

    public List<Atelier> getAllAteliersWithCoachDetailsOnly() {
        return atelierRepository.findAllWithCoachOnly();
    }

    public Optional<Atelier> getAtelierById(Long id) {
        return atelierRepository.findById(id);
    }

	public List<Atelier> getAteliersByCoachId(Long coachId) {
		return atelierRepository.findByCoachId(coachId);
	}

    //  Image par catégorie
    private String getImageUrlByCategorie(String categorie) {
        if (categorie == null) return null;

        return switch (categorie.toUpperCase()) {
            case "ART" -> "https://images.unsplash.com/photo-1460661419201-fd4cecdf8a8b?auto=format&fit=crop&w=600&q=80";
            case "ECRITURE" -> "https://images.unsplash.com/photo-1455390582262-044cdead277a?auto=format&fit=crop&w=600&q=80";
            case "JARDINAGE" -> "https://images.unsplash.com/photo-1416879595882-3373a0480b5b?auto=format&fit=crop&w=600&q=80";
            case "BIJOUX" -> "https://images.unsplash.com/photo-1515562141207-7a88fb7ce338?auto=format&fit=crop&w=600&q=80";
            case "MAKEUP" -> "https://images.unsplash.com/photo-1522337360788-8b13dee7a37e?auto=format&fit=crop&w=600&q=80";
            case "YOGA" -> "https://images.unsplash.com/photo-1544367567-0f2fcb009e0b?auto=format&fit=crop&w=600&q=80";
            case "FITNESS" -> "https://images.unsplash.com/photo-1571019613454-1cb2f99b2d8b?auto=format&fit=crop&w=600&q=80";
            case "NATATION" -> "https://images.unsplash.com/photo-1530549387789-4c1017266635?auto=format&fit=crop&w=600&q=80";
            default -> null;
        };
    }

    public Atelier createAtelier(CreateAtelierDTO dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Utilisateur user = userRepository.findUserByEmail(email);

        if (!(user instanceof Admin admin)) {
            throw new RuntimeException("Seul un admin peut créer un atelier");
        }

        Atelier atelier = new Atelier();
        atelier.setNom(dto.getNom());
        atelier.setDescription(dto.getDescription());
        atelier.setDate(java.sql.Date.valueOf(dto.getDate()));
        atelier.setHeure(dto.getHeure());
        atelier.setCategorie(dto.getCategorie());
        atelier.setAdmin(admin);

        // Récupérer coach
        Coach coach = coachrepo.findById(dto.getCoachId())
                .orElseThrow(() -> new RuntimeException("Coach non trouvé"));
        atelier.setCoach(coach);

        // Image par catégorie
        String imageUrl = getImageUrlByCategorie(dto.getCategorie());
        if (imageUrl != null) {
            Image image = new Image();
            image.setUrl(imageUrl);
            image.setAtelier(atelier);
            List<Image> images = new ArrayList<>();
            images.add(image);
            atelier.setPhotos(images);
        }

        return atelierRepository.save(atelier);
    }


    // Update
    public Atelier updateAtelier(Long id, Atelier atelierDetails) {
        return atelierRepository.findById(id)
                .map(atelier -> {
                    atelier.setNom(atelierDetails.getNom());
                    atelier.setDescription(atelierDetails.getDescription());
                    atelier.setDate(atelierDetails.getDate());
                    atelier.setHeure(atelierDetails.getHeure());
                    atelier.setCoach(atelierDetails.getCoach());
                    return atelierRepository.save(atelier);
                })
                .orElseThrow(() -> new RuntimeException("Atelier not found"));
    }


    // ️ Delete
    public void deleteAtelier(Long id) {
        atelierRepository.deleteById(id);
    }
}
