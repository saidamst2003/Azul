package Azul.example.Azul.dto;

import jakarta.validation.constraints.Email;

public record UpdateCoachDTO(
        String fullName,
        @Email(message = "please enter a valid email")
        String email,
        String password,
        String specialite
) {}


