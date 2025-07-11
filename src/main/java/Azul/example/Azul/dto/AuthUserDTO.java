package Azul.example.Azul.dto;

import Azul.example.Azul.model.Role;

public record AuthUserDTO (
        Long id,
        String fullName,
        String email,
        Role role
) {
}
