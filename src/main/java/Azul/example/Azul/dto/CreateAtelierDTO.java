package Azul.example.Azul.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;

public class CreateAtelierDTO {
    @NotBlank(message = "Le nom est obligatoire")
    private String nom;

    @NotBlank(message = "La description est obligatoire")
    private String description;

    @NotBlank(message = "La catégorie est obligatoire")
    private String categorie;

    @NotNull(message = "La date est obligatoire")
    @Future(message = "La date doit être dans le futur")
    private LocalDate date;

    @NotNull(message = "L'heure est obligatoire")
    private LocalTime heure;

    @NotNull(message = "Le coach est obligatoire")
    private Long coachId;

    // Constructeurs
    public CreateAtelierDTO() {}

    public CreateAtelierDTO(String nom, String description, String categorie, LocalDate date, LocalTime heure, Long coachId) {
        this.nom = nom;
        this.description = description;
        this.categorie = categorie;
        this.date = date;
        this.heure = heure;
        this.coachId = coachId;
    }


    // Getters et Setters
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCategorie() { return categorie; }
    public void setCategorie(String categorie) { this.categorie = categorie; }


    public @NotNull(message = "La date est obligatoire") @Future(message = "La date doit être dans le futur") LocalDate getDate() {
        return date;
    }

    public void setDate(@NotNull(message = "La date est obligatoire") @Future(message = "La date doit être dans le futur") LocalDate date) {
        this.date = date;
    }

    public LocalTime getHeure() { return heure; }
    public void setHeure(LocalTime heure) { this.heure = heure; }

    public Long getCoachId() { return coachId; }
    public void setCoachId(Long coachId) { this.coachId = coachId; }
}
