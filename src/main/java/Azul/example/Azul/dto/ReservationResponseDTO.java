package Azul.example.Azul.dto;

import java.sql.Date;

public class ReservationResponseDTO {
    private Long id;
    private Date dateReservation;
    private Long clientId;
    private String clientNom;
    private String clientEmail;
    private Long atelierId;
    private String atelierNom;
    private String atelierDescription;

    public ReservationResponseDTO() {}

    public ReservationResponseDTO(Long id, Date dateReservation, Long clientId, String clientNom,
                                  String clientEmail, Long atelierId, String atelierNom, String atelierDescription) {
        this.id = id;
        this.dateReservation = dateReservation;
        this.clientId = clientId;
        this.clientNom = clientNom;
        this.clientEmail = clientEmail;
        this.atelierId = atelierId;
        this.atelierNom = atelierNom;
        this.atelierDescription = atelierDescription;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateReservation() {
        return dateReservation;
    }

    public void setDateReservation(Date dateReservation) {
        this.dateReservation = dateReservation;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getClientNom() {
        return clientNom;
    }

    public void setClientNom(String clientNom) {
        this.clientNom = clientNom;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    public Long getAtelierId() {
        return atelierId;
    }

    public void setAtelierId(Long atelierId) {
        this.atelierId = atelierId;
    }

    public String getAtelierNom() {
        return atelierNom;
    }

    public void setAtelierNom(String atelierNom) {
        this.atelierNom = atelierNom;
    }

    public String getAtelierDescription() {
        return atelierDescription;
    }

    public void setAtelierDescription(String atelierDescription) {
        this.atelierDescription = atelierDescription;
    }
}