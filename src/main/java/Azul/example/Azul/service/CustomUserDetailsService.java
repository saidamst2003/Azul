package Azul.example.Azul.service;

import Azul.example.Azul.model.UserPrincipal;
import Azul.example.Azul.model.Utilisateur;
import Azul.example.Azul.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("[CustomUserDetailsService] Loading user by email: " + email);

        Utilisateur utilisateur = userRepository.findUserByEmail(email);
        if (utilisateur == null) {
            System.err.println("[CustomUserDetailsService] User not found: " + email);
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        System.out.println("[CustomUserDetailsService] User found: " + email + " with role: " + utilisateur.getRole());
        return new UserPrincipal(utilisateur);
    }
}