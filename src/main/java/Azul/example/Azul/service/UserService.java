package Azul.example.Azul.service;


import Azul.example.Azul.dto.RegisterDTO;
import Azul.example.Azul.model.Utilisateur;
import Azul.example.Azul.repository.UseRepo;

import org.apache.catalina.User;
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

    private final UseRepo userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final BCryptPasswordEncoder encoder;

    public UserService(
            final UseRepo userRepository,
            final AuthenticationManager authenticationManager,
            final JwtService jwtService
    ) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.encoder = new BCryptPasswordEncoder(12);
    }

    public Utilisateur registerUser(RegisterDTO registerDTO) {
        if (userRepository.findUserByEmail(registerDTO.email()) != null) {
            throw new IllegalArgumentException("User with this email already exists.");
        }

