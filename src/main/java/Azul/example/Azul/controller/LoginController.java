package Azul.example.Azul.controller;

import Azul.example.Azul.dto.LoginDTO;
import Azul.example.Azul.model.Utilisateur;
import Azul.example.Azul.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/login")
@CrossOrigin
public class LoginController {

    private final UserService userService;

    public LoginController (
            final UserService userService
    ) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> loginUser (@Valid @RequestBody LoginDTO loginDTO) {
        // Chercher l'utilisateur en base pour avoir le bon type (Client/Admin/Coach)
        Utilisateur utilisateur = userService.findByEmail(loginDTO.email());
        if (utilisateur == null) {
            return ResponseEntity.badRequest().body("Email ou mot de passe incorrect");
        }
        utilisateur.setPassword(loginDTO.password()); // mot de passe en clair pour l'authentification
        return userService.verify(utilisateur);
    }
}
