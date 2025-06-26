package Azul.example.Azul.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "admins")
@DiscriminatorValue("ADMIN")
public class Admin extends Utilisateur {

    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Atelier> ateliers = new ArrayList<>();

    // Constructeurs
    public Admin() {}

    public Admin(String nom, String email, String motDePasse) {
        super(nom, email, motDePasse);
    }

    // Getters et Setters
    public List<Atelier> getAteliers() { return ateliers; }
    public void setAteliers(List<Atelier> ateliers) { this.ateliers = ateliers; }

}
