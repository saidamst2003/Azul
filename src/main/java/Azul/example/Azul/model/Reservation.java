package Azul.example.Azul.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name = "reservations")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Date dateReservation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    @JsonBackReference("client-reservations")
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "atelier_id")
    @JsonBackReference("atelier-reservations")
    private Atelier atelier;

    // Constructeurs
    public Reservation() {}

    public Reservation(Date dateReservation) {
        this.dateReservation = dateReservation;
    }

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Date getDateReservation() { return dateReservation; }
    public void setDateReservation(Date dateReservation) { this.dateReservation = dateReservation; }

    public Client getClient() { return client; }
    public void setClient(Client client) { this.client = client; }

    public Atelier getAtelier() { return atelier; }
    public void setAtelier(Atelier atelier) { this.atelier = atelier; }


}
