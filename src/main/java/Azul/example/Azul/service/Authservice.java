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

        // Use the role from the DTO if available, otherwise use the path variable
        Role userRole = registerDTO.role() != null ? registerDTO.role() : 
                       role.equalsIgnoreCase("admin") ? Role.ADMIN :
                       role.equalsIgnoreCase("client") ? Role.CLIENT :
                       role.equalsIgnoreCase("coach") ? Role.COACH : Role.CLIENT;

        switch (userRole) {
            case ADMIN -> {
                newUser = new Admin();
                newUser.setRole(Role.ADMIN);
            }
            case CLIENT -> {
                newUser = new Client();
                newUser.setRole(Role.CLIENT);
            }
            case COACH -> {
                newUser = new Coach();
                newUser.setRole(Role.COACH);
            }
            default -> {
                newUser = new Client();
                newUser.setRole(Role.CLIENT);
            }
        }

        newUser.setFullName(registerDTO.fullName());
        newUser.setEmail(registerDTO.email());
        newUser.setPassword(passwordEncoder.encode(registerDTO.password()));

        return userRepository.save(newUser);
    }

}
