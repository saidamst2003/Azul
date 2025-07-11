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

        if(role.equalsIgnoreCase("admin")) {
            newUser = new Admin();
            newUser.setRole(Role.ADMIN);
        } else if (role.equalsIgnoreCase("conducteur")) {
            newUser = new Client();
            newUser.setRole(Role.CLIENT);
        } else {
            newUser = new Coach();
            newUser.setRole(Role.COACH);
        }

        newUser.setFullName(registerDTO.fullName());
        newUser.setEmail(registerDTO.email());
        newUser.setPassword(passwordEncoder.encode(registerDTO.password()));

        return userRepository.save(newUser);
    }

}
