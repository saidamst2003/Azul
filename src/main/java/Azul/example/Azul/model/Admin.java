package Azul.example.Azul.model;

import jakarta.persistence.*;

import java.util.ArrayList;

@Entity
@Table(name = "admins")
@DiscriminatorValue("ADMIN")
public class Admin extends Utilisateur {

//    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<Atelier> ateliers = new ArrayList<>();

    // Constructeurs
    public Admin() {}

    public Admin(String nom, String email, String motDePasse) {
        super(nom, email, motDePasse);
    }

//    // Getters et Setters
//    public List<Atelier> getAteliers() { return ateliers; }
//    public void setAteliers(List<Atelier> ateliers) { this.ateliers = ateliers; }

    // Implémentation des méthodes
    @Override
    public void seConnecter() {
        // Logique de connexion pour admin
    }

    public void creerAtelier() {
        // Logique de création d'atelier
    }

    public void modifierAtelier() {
        // Logique de modification d'atelier
    }

    public void supprimerAtelier() {
        // Logique de suppression d'atelier
    }

    public void gererCoaches() {
        // Logique de gestion des coaches
    }

    public void voirStatistiques() {
        // Logique pour voir les statistiques
    }

    public void gererAccesOrganisateur() {
        // Logique de gestion d'accès organisateur
    }
}
