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
        Utilisateur utilisateur = new Utilisateur() {
            @Override
            public void seConnecter() {

            }
        };

        utilisateur.setEmail(loginDTO.email());
        utilisateur.setPassword(loginDTO.password());

        return userService.verify(utilisateur);

    }
}
