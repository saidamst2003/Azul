package Azul.example.Azul.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

public class UserPrincipal implements UserDetails {
    private final Optional<Utilisateur> utilisateur;

    public UserPrincipal(Optional<Utilisateur> user) {
        this.utilisateur = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + utilisateur.get().getRole().name()));
    }

    @Override
    public String getPassword() {
        return  String.valueOf(utilisateur.get().getPassword());
    }

    @Override
    public String getUsername() {
        return String.valueOf(utilisateur.get().getEmail());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Optional<Utilisateur> getUtilisateur() {
        return this.utilisateur;
    }
}
