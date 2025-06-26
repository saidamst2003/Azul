package Azul.example.Azul.model;

import jakarta.persistence.*;


@Entity
@Table(name = "clients")
@DiscriminatorValue("CLIENT")
public class Client extends Utilisateur {
//
//    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<Reservation> reservations = new ArrayList<>();

    // Constructeurs
    public Client() {}

    public Client(String nom, String email, String motDePasse) {
        super(nom, email, motDePasse);
    }
//
//    // Getters et Setters
//    public List<Reservation> getReservations() { return reservations; }
//    public void setReservations(List<Reservation> reservations) { this.reservations = reservations; }

    // Implémentation des méthodes
    @Override
    public void seConnecter() {
        // Logique de connexion pour client
    }

    public void reserverAtelier() {
        // Logique de réservation
    }

    public void consulterHistorique() {
        // Logique pour consulter l'historique
    }

    public void modifierProfil() {
        // Logique de modification de profil
    }
}