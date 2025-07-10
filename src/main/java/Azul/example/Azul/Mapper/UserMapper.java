package Azul.example.Azul.Mapper;

import Azul.example.Azul.dto.AuthUserDTO;
import Azul.example.Azul.model.Utilisateur;
import org.mapstruct.Mapper;

import javax.management.relation.Role;

@Mapper(componentModel = "spring")
public interface UserMapper {

    Utilisateur toEntity(AuthUserDTO authUserDTO);
    AuthUserDTO toDTO(Utilisateur user);

    default Role map(Role role) {
        return role;
    }
}
