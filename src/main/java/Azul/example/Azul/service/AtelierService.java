package Azul.example.Azul.service;

import Azul.example.Azul.model.Atelier;
import Azul.example.Azul.repository.AtelierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AtelierService {

    private final AtelierRepository atelierRepository;

    @Autowired
    public AtelierService(AtelierRepository atelierRepository) {
        this.atelierRepository = atelierRepository;
    }

    public List<Atelier> getAllAteliers() {
        return atelierRepository.findAll();
    }

    public Optional<Atelier> getAtelierById(Long id) {
        return atelierRepository.findById(id);
    }

    public Atelier createAtelier(Atelier atelier) {
        return atelierRepository.save(atelier);
    }

    public Atelier updateAtelier(Long id, Atelier atelierDetails) {
        Atelier atelier = atelierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Atelier non trouvé pour cet id :: " + id));

        atelier.setNom(atelierDetails.getNom());
        atelier.setDescription(atelierDetails.getDescription());
        atelier.setDate(atelierDetails.getDate());
        atelier.setHeure(atelierDetails.getHeure());
        atelier.setCoach(atelierDetails.getCoach());
        // Vous pouvez ajouter d'autres champs à mettre à jour ici

        return atelierRepository.save(atelier);
    }

    public void deleteAtelier(Long id) {
        Atelier atelier = atelierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Atelier non trouvé pour cet id :: " + id));
        atelierRepository.delete(atelier);
    }

    // Méthodes de recherche personnalisées
    public List<Atelier> findByNom(String nom) {
        return atelierRepository.findByNomContainingIgnoreCase(nom);
    }

    public List<Atelier> findByDate(Date date) {
        return atelierRepository.findByDate(date);
    }

    public List<Atelier> findByCoachId(Long coachId) {
        return atelierRepository.findByCoachId(coachId);
    }

    public List<Atelier> findByDateBetween(Date dateDebut, Date dateFin) {
        return atelierRepository.findByDateBetween(dateDebut, dateFin);
    }
}
