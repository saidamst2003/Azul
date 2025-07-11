package Azul.example.Azul.service;

import Azul.example.Azul.Mapper.UserMapper;
import Azul.example.Azul.dto.AuthUserDTO;
import Azul.example.Azul.dto.RegisterDTO;
import Azul.example.Azul.exception.PasswordIncorrectException;
import Azul.example.Azul.model.Admin;
import Azul.example.Azul.model.Client;
import Azul.example.Azul.model.Coach;
import Azul.example.Azul.model.Role;
import Azul.example.Azul.model.Utilisateur;
import Azul.example.Azul.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;
    private final JwtService jwtService;
    private final BCryptPasswordEncoder encoder;

    public UserService(
            final UserRepository userRepository,
            final AuthenticationManager authenticationManager,
            final UserMapper userMapper,
            final JwtService jwtService
    ) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.userMapper = userMapper;
        this.jwtService = jwtService;
        this.encoder = new BCryptPasswordEncoder(12);
    }

    public Utilisateur registerUser(RegisterDTO registerDTO) {
        if (userRepository.findUserByEmail(registerDTO.email()) != null) {
            throw new IllegalArgumentException("User with this email already exists.");
        }

        Utilisateur newUser;
        String encryptedPassword = encoder.encode(registerDTO.password());
        Role role = registerDTO.role();
        switch (role) {
            case ADMIN -> newUser = new Admin(registerDTO.fullName(), registerDTO.email(), encryptedPassword, Role.ADMIN);
            case CLIENT -> newUser = new Client(registerDTO.fullName(), registerDTO.email(), encryptedPassword, Role.CLIENT);
            case COACH -> newUser = new Coach(registerDTO.fullName(), registerDTO.email(), encryptedPassword, Role.COACH, "specialite par defaut");
            default -> throw new IllegalArgumentException("Unknown role: " + role);
        }
        return userRepository.save(newUser);
    }

    public ResponseEntity<?> verify(Utilisateur user) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getEmail(),
                            user.getPassword()
                    )
            );

            if (authentication.isAuthenticated()) {
                AuthUserDTO authUser = this.getAuthenticatedUser(user.getEmail());
                String token = jwtService.generateJwtToken(authUser);

                Map<String, String> responseSuccess = new HashMap<>();
                responseSuccess.put("token", token);

                return new ResponseEntity<>(responseSuccess, HttpStatus.OK);
            }
            throw new PasswordIncorrectException("Invalid credentials (Authentication failed unexpectedly).");
        } catch (AuthenticationException e) {
            System.err.println("Authentication failed for user: " + user.getEmail() + " Error: " + e.getMessage());
            throw new PasswordIncorrectException("Invalid credentials.");
        }
    }

    public AuthUserDTO getAuthenticatedUser(String email) {
        Utilisateur authenticatedUser = userRepository.findUserByEmail(email);
        if (authenticatedUser == null) {
            throw new PasswordIncorrectException("User not found after authentication (this should not happen).");
        }
        return new AuthUserDTO(
                authenticatedUser.getId(),
                authenticatedUser.getFullName(),
                authenticatedUser.getEmail(),
                authenticatedUser.getRole()
        );
    }
}