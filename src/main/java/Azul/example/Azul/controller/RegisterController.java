package Azul.example.Azul.controller;

import Azul.example.Azul.dto.RegisterDTO;
import Azul.example.Azul.model.Utilisateur;
import Azul.example.Azul.service.Authservice;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/register")
@CrossOrigin(origins = "http://localhost:4200")
public class RegisterController {

    private final Authservice authService;

    public RegisterController(final Authservice authService) {
        this.authService = authService;
    }

    @PostMapping("/{role}")
    public ResponseEntity<Utilisateur> registerUser(@Valid @RequestBody RegisterDTO registerDTO,@PathVariable String role) {
        Utilisateur registeredUser = authService.registerUser(registerDTO, role);
        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
    }


}
