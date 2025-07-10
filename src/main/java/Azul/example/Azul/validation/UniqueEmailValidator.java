package Azul.example.Azul.validation;

import Azul.example.Azul.model.Utilisateur;
import Azul.example.Azul.repository.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.catalina.User;

import java.util.Optional;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    private final UserRepository userRepository;

    public UniqueEmailValidator (
            final UserRepository userRepository
    ) {
        this.userRepository = userRepository;
    }



    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        Optional<User> foundEmail = userRepository.findUserByEmail(email);

        return foundEmail == null;
    }
}