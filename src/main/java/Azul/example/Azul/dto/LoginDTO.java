package Azul.example.Azul.dto;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

public record LoginDTO(
        @NotBlank(message = "email is required")
        @Email( message = "please enter a valid email")
        String email,

        @NotBlank( message = "password is required" )
        String password
) {

}