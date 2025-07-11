package Azul.example.Azul.Mapper;

import Azul.example.Azul.dto.AuthUserDTO;
import Azul.example.Azul.model.Role;
import Azul.example.Azul.model.Utilisateur;
import Azul.example.Azul.model.Admin;
import Azul.example.Azul.model.Client;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    default Utilisateur toEntity(AuthUserDTO authUserDTO) {
        if (authUserDTO.role() == Role.ADMIN) {
            Admin admin = new Admin();
            admin.setFullName(authUserDTO.fullName());
            admin.setEmail(authUserDTO.email());
            admin.setRole(authUserDTO.role());
            return admin;
        } else if (authUserDTO.role() == Role.CLIENT) {
            Client client = new Client();
            client.setFullName(authUserDTO.fullName());
            client.setEmail(authUserDTO.email());
            client.setRole(authUserDTO.role());
            return client;
        }
        throw new IllegalArgumentException("Role non supporté: " + authUserDTO.role());
    }

    AuthUserDTO toDTO(Utilisateur user);
}
