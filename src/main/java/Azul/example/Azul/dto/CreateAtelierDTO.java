package Azul.example.Azul.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.sql.Date;
import java.time.LocalTime;

public class CreateAtelierDTO {
    @NotBlank(message = "Le nom est obligatoire")
    private String nom;

    @NotBlank(message = "La description est obligatoire")
    private String description;

    @NotNull(message = "La date est obligatoire")
    @Future(message = "La date doit être dans le futur")
    private Date date;

    @NotNull(message = "L'heure est obligatoire")
    private LocalTime heure;

    @NotNull(message = "Le coach est obligatoire")
    private Long coachId;

    private Long adminId;

    // Constructeurs
    public CreateAtelierDTO() {}

    public CreateAtelierDTO(String nom, String description, Date date, LocalTime heure, Long coachId, Long adminId) {
        this.nom = nom;
        this.description = description;
        this.date = date;
        this.heure = heure;
        this.coachId = coachId;
        this.adminId = adminId;
    }

    // Getters et Setters
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }

    public LocalTime getHeure() { return heure; }
    public void setHeure(LocalTime heure) { this.heure = heure; }

    public Long getCoachId() { return coachId; }
    public void setCoachId(Long coachId) { this.coachId = coachId; }

    public Long getAdminId() { return adminId; }
    public void setAdminId(Long adminId) { this.adminId = adminId; }
}