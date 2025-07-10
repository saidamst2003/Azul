package Azul.example.Azul.Mapper;

import Azul.example.Azul.dto.AuthUserDTO;
import Azul.example.Azul.model.Utilisateur;
import Azul.example.Azul.model.Admin;
import Azul.example.Azul.model.Client;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

    public Utilisateur toEntity(AuthUserDTO authUserDTO) {
        if (authUserDTO == null || authUserDTO.role() == null) {
            return null;
        }
        String roleName = authUserDTO.role().name();
        if (roleName.equalsIgnoreCase("ADMIN")) {
            return new Admin(authUserDTO.fullName(), authUserDTO.email(), null);
        } else if (roleName.equalsIgnoreCase("CLIENT")) {
            return new Client(authUserDTO.fullName(), authUserDTO.email(), null);
        }
        // Add more roles as needed
        return null;
    }

    public abstract AuthUserDTO toDTO(Utilisateur user);
}
