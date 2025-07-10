package Azul.example.Azul.dto;

import Azul.example.Azul.model.role;

public record AuthUserDTO (
        Long id,
        String fullName,
        String email,
        role role
) {
}
