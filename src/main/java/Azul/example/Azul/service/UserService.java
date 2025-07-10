package Azul.example.Azul.service;


import Azul.example.Azul.model.Utilisateur;
import Azul.example.Azul.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import Azul.example.Azul.dto.RegisterDTO;
import Azul.example.Azul.dto.UtilisateurDTO;
import Azul.example.Azul.security.JwtService;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Autowired
    public UserService(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }
    public Utilisateur saveUser(Utilisateur utilisateur) {
        return userRepository.save(utilisateur);
    }
    public Page<Utilisateur> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public Page<Utilisateur> getUsersByRole(String role, Pageable pageable) {
        return userRepository.findByRolesName(role, pageable);
    }

    public Optional<Utilisateur> getUserByUsername(String username) {
        return Optional.ofNullable(userRepository.findByUsername(username));
    }

    public Optional<Utilisateur> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    // Ajout de la méthode d'inscription
    public UtilisateurDTO register(RegisterDTO registerDTO) {
        // Vérifier si l'utilisateur existe déjà
        if (userRepository.findByEmail(registerDTO.email()).isPresent()) {
            throw new RuntimeException("Email déjà utilisé");
        }
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setFullName(registerDTO.fullName());
        utilisateur.setEmail(registerDTO.email());
        utilisateur.setPassword(registerDTO.password()); // À hasher en prod !
        utilisateur.setRole(registerDTO.role());
        userRepository.save(utilisateur);
        return new UtilisateurDTO(utilisateur.getId(), utilisateur.getFullName(), utilisateur.getEmail(), utilisateur.getRole());
    }

    // Ajout de la méthode de connexion
    public String login(String email, String password) {
        Utilisateur utilisateur = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        if (!utilisateur.getPassword().equals(password)) { // À hasher en prod !
            throw new RuntimeException("Mot de passe incorrect");
        }
        UtilisateurDTO utilisateurDTO = new UtilisateurDTO(utilisateur.getId(), utilisateur.getFullName(), utilisateur.getEmail(), utilisateur.getRole());
        return jwtService.generateJwtToken(utilisateurDTO);
    }
}