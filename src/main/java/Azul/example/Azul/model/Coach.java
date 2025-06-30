package Azul.example.Azul.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "coaches")
@DiscriminatorValue("COACH")
public class Coach extends Utilisateur {

    @Column(nullable = false)
    private String specialite;

    @OneToMany(mappedBy = "coach", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Atelier> ateliers = new ArrayList<>();

    // Constructeurs
    public Coach() {
        super();
    }

    public Coach(String fullName, String email, String password, String specialite) {
        super(fullName, email, password, role.COACH);
        this.specialite = specialite;
    }

    // Getters et Setters
    public String getSpecialite() { return specialite; }
    public void setSpecialite(String specialite) { this.specialite = specialite; }

    public List<Atelier> getAteliers() { return ateliers; }
    public void setAteliers(List<Atelier> ateliers) { this.ateliers = ateliers; }


}