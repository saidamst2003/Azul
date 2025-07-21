package Azul.example.Azul.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clients")
@DiscriminatorValue("CLIENT")
public class Client extends Utilisateur {
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference("client-reservations")
    private List<Reservation> reservations = new ArrayList<>();

    public Client() {}

    public Client(String fullName, String email, String password, Role role) {
        super(null, fullName, email, password, role);
    }

    public Client(@NotBlank(message = "full name is required") String s, @NotBlank(message = "email is required") @Email(message = "please enter a valid email") String email, String encode) {
    }

    public List<Reservation> getReservations() { return reservations; }
    public void setReservations(List<Reservation> reservations) { this.reservations = reservations; }

    @Override
    public void seConnecter() {
        // Logique de connexion pour client
    }

}