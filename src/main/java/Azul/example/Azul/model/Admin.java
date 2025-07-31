package Azul.example.Azul.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "admins")
@DiscriminatorValue("ADMIN")
public class Admin extends Utilisateur {

    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference("admin-ateliers")
    private List<Atelier> ateliers = new ArrayList<>();

    // Constructeur vide
    public Admin() {}

    public Admin(@NotBlank(message = "full name is required") String s, @NotBlank(message = "email is required") @Email(message = "please enter a valid email") String email, String encode) {
    }

    @Override
    public void seConnecter() {

    }

    // Constructeur pour la création (sans id)
    public Admin(String fullName, String email, String password, Role role) {
        super(null, fullName, email, password, role);
    }

    // Constructeur complet (avec id)
    public Admin(Long id, String fullName, String email, String password, Role role) {
        super(id, fullName, email, password, role);
    }


}
