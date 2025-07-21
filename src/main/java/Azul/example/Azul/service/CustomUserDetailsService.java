package Azul.example.Azul.service;


import Azul.example.Azul.model.UserPrincipal;
import Azul.example.Azul.model.Utilisateur;
import Azul.example.Azul.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(
            final UserRepository userRepository
    ) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException
    {

        Utilisateur utilisateur = userRepository.findUserByEmail(email);

        if ( utilisateur == null ) {
            throw new UsernameNotFoundException("this user is not found");
        }
        return new UserPrincipal(Optional.of(utilisateur));
    }
}
