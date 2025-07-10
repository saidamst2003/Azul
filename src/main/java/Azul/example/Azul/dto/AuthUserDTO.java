package Azul.example.Azul.dto;

import javax.management.relation.Role;

public record AuthUserDTO (
        Long id,
        String fullName,
        String email,
        Role role
) {
}
