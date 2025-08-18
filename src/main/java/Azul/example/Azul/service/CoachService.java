package Azul.example.Azul.service;

import Azul.example.Azul.dto.CreateCoachDTO;
import Azul.example.Azul.dto.UpdateCoachDTO;
import Azul.example.Azul.model.Coach;
import Azul.example.Azul.repository.CoachRepository;
import Azul.example.Azul.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CoachService {

    @Autowired
    private CoachRepository coachRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Coach> getAllCoaches() {
        return coachRepository.findAll();
    }

    public Coach getCoachById(Long id) {
        return coachRepository.findById(id).orElse(null);
    }

    public Coach createCoach(CreateCoachDTO dto) {
        Coach coach = new Coach(
                dto.fullName(),
                dto.email(),
                passwordEncoder.encode(dto.password()),
                Role.COACH,
                dto.specialite()
        );
        return coachRepository.save(coach);
    }

    public Coach updateCoach(Long id, UpdateCoachDTO dto) {
        Optional<Coach> optionalExisting = coachRepository.findById(id);
        if (optionalExisting.isEmpty()) {
            throw new RuntimeException("Coach not found with id: " + id);
        }

        Coach existing = optionalExisting.get();
        if (dto.fullName() != null && !dto.fullName().isBlank()) existing.setFullName(dto.fullName());
        if (dto.email() != null && !dto.email().isBlank()) existing.setEmail(dto.email());
        if (dto.specialite() != null && !dto.specialite().isBlank()) existing.setSpecialite(dto.specialite());
        if (dto.password() != null && !dto.password().isBlank()) {
            existing.setPassword(passwordEncoder.encode(dto.password()));
        }
        existing.setRole(Role.COACH);
        return coachRepository.save(existing);
    }

    public void deleteCoach(Long id) {
        if (!coachRepository.existsById(id)) {
            throw new RuntimeException("Coach not found with id: " + id);
        }
        coachRepository.deleteById(id);
    }
}