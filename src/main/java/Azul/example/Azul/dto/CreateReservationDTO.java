package Azul.example.Azul.dto;


import java.sql.Date;

public class CreateReservationDTO {
    private Long atelierId;
    private Long clientId;
    private Date dateReservation;

    // Constructeurs
    public CreateReservationDTO() {}

    public CreateReservationDTO(Long atelierId, Long clientId, Date dateReservation) {
        this.atelierId = atelierId;
        this.clientId = clientId;
        this.dateReservation = dateReservation;
    }

    // Getters et Setters
    public Long getAtelierId() {
        return atelierId;
    }

    public void setAtelierId(Long atelierId) {
        this.atelierId = atelierId;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Date getDateReservation() {
        return dateReservation;
    }

    public void setDateReservation(Date dateReservation) {
        this.dateReservation = dateReservation;
    }
}