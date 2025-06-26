package Azul.example.Azul.dto;


import javax.management.relation.Role;

public record UtilisateurDTO(
        Long id,
        String fullName,
        String email,
        Role role
) {}
