package Azul.example.Azul.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "coaches")
public class Coach {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String specialite;

    @OneToMany(mappedBy = "coach", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Atelier> ateliers = new ArrayList<>();

    // Constructeurs
    public Coach() {}

    public Coach(String specialite) {
        this.specialite = specialite;
    }

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getSpecialite() { return specialite; }
    public void setSpecialite(String specialite) { this.specialite = specialite; }

    public List<Atelier> getAteliers() { return ateliers; }
    public void setAteliers(List<Atelier> ateliers) { this.ateliers = ateliers; }


}