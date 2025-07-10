package Azul.example.Azul.security;

import Azul.example.Azul.dto.RegisterDTO;
import Azul.example.Azul.dto.UtilisateurDTO;
import Azul.example.Azul.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UtilisateurDTO> register(@RequestBody RegisterDTO registerDTO) {
        UtilisateurDTO utilisateurDTO = userService.register(registerDTO);
        return ResponseEntity.ok(utilisateurDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String email, @RequestParam String password) {
        String jwt = userService.login(email, password);
        return ResponseEntity.ok(jwt);
    }
} 