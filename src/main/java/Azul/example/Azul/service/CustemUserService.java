package Azul.example.Azul.service;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;



@Service
public class CustemUserService implements UserDetailsService {



    private final UserReposetory userReposetory;

    public CustemUserService(UserReposetory userReposetory) {
        this.userReposetory = userReposetory;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userReposetory.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getAuthorities()
        );
    }
}