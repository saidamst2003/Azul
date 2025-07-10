package Azul.example.Azul.controller;

import Azul.example.Azul.dto.RegisterDTO;
import Azul.example.Azul.model.Utilisateur;
import Azul.example.Azul.service.Authservice;
import jakarta.validation.Valid;
import org.apache.catalina.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/register")
@CrossOrigin
public class RegisterController {

    private final Authservice authService;

    public RegisterController(final Authservice authService) {
        this.authService = authService;
    }

    @PostMapping("/{role}")
    public ResponseEntity<User> register(@Valid @RequestBody RegisterDTO registerDTO, @PathVariable String role) {
        Utilisateur registeredUser = authService.registerUser(registerDTO, role);
        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
    }
}
