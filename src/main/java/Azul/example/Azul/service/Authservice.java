package Azul.example.Azul.service;

import Azul.example.Azul.dto.RegisterDTO;
import Azul.example.Azul.model.*;
import Azul.example.Azul.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class Authservice {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // Constructeur pour l'injection de dépendances
    public Authservice(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Enregistre un nouvel utilisateur avec un rôle spécifié
    public Utilisateur registerUser(RegisterDTO registerDTO, String role) {
        Utilisateur newUser;

        if (role.equalsIgnoreCase("admin")) {
            newUser = new Admin(
                    registerDTO.fullName(),
                    registerDTO.email(),
                    passwordEncoder.encode(registerDTO.password()),
                    Role.ADMIN
            );
        } else if (role.equalsIgnoreCase("client")) {
            newUser = new Client(
                    registerDTO.fullName(),
                    registerDTO.email(),
                    passwordEncoder.encode(registerDTO.password()),
                    Role.CLIENT
            );
        } else if (role.equalsIgnoreCase("coach")) {
            // Ici pas de spécialité fournie → valeur fixe par défaut
            newUser = new Coach(
                    registerDTO.fullName(),
                    registerDTO.email(),
                    passwordEncoder.encode(registerDTO.password()),
                    Role.COACH,
                    "Aucune spécialité"
            );
        } else {
            throw new IllegalArgumentException("Rôle non reconnu : " + role);
        }

        return userRepository.save(newUser);
    }

}
