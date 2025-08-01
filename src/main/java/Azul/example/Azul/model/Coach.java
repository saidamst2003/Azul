package Azul.example.Azul.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "coaches")
@DiscriminatorValue("COACH")
public class Coach extends Utilisateur {
    @Column(nullable = false)
    private String specialite;

    @OneToMany(mappedBy = "coach", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore // Pour éviter les références circulaires
    private List<Atelier> ateliers = new ArrayList<>();

    public Coach() {}

    @Override
    public void seConnecter() {

    }

    public Coach(String fullName, String email, String password, Role role, String specialite) {
        super(null, fullName, email, password, role);
        this.specialite = specialite;
    }

    public String getSpecialite() { return specialite; }
    public void setSpecialite(String specialite) { this.specialite = specialite; }

    public List<Atelier> getAteliers() { return ateliers; }
    public void setAteliers(List<Atelier> ateliers) { this.ateliers = ateliers; }




}