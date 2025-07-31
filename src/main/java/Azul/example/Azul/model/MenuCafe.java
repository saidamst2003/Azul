package Azul.example.Azul.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "menu_cafes")
public class MenuCafe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private Double prix;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "atelier_id")
    @JsonBackReference("atelier-menucafe")
    private Atelier atelier;

    // Constructeurs
    public MenuCafe() {}

    public MenuCafe(String nom, Double prix) {
        this.nom = nom;
        this.prix = prix;
    }

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public Double getPrix() { return prix; }
    public void setPrix(Double prix) { this.prix = prix; }

    public Atelier getAtelier() { return atelier; }
    public void setAtelier(Atelier atelier) { this.atelier = atelier; }
    // Méthodes métier
    public void consulterMenu() {
        // Logique de consultation du menu
    }
}
