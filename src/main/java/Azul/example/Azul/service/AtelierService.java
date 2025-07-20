package Azul.example.Azul.service;

import Azul.example.Azul.model.Atelier;
import Azul.example.Azul.repository.AtelierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return atelierRepository.findById(id)
                .map(atelier -> {
                    atelier.setNom(atelierDetails.getNom());
                    atelier.setDescription(atelierDetails.getDescription());
                    atelier.setDate(atelierDetails.getDate());
                    atelier.setHeure(atelierDetails.getHeure());
                    atelier.setCoach(atelierDetails.getCoach());
                    atelier.setAdmin(atelierDetails.getAdmin());
                    return atelierRepository.save(atelier);
                })
                .orElseThrow(() -> new RuntimeException("Atelier not found"));
    }

    public void deleteAtelier(Long id) {
        atelierRepository.deleteById(id);
    }
}