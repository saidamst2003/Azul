package Azul.example.Azul.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonIgnore
    private List<Atelier> ateliers = new ArrayList<>();

    public Coach() {}

    @Override
    public void seConnecter() {
        // logique de connexion spécifique au coach
    }

    public Coach(String fullName, String email, String password, Role role, String specialite) {
        super(null, fullName, email, password, role);
        this.specialite = specialite;
    }

    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }

    public List<Atelier> getAteliers() {
        return ateliers;
    }

    public void setAteliers(List<Atelier> ateliers) {
        this.ateliers = ateliers;
    }

    // Override باش مانخليوش password يتعرض فالـ JSON
    @Override
    @JsonIgnore
    public String getPassword() {
        return super.getPassword();
    }


    @Override
    @JsonProperty
    public void setPassword(String password) {
        super.setPassword(password);
    }
}
