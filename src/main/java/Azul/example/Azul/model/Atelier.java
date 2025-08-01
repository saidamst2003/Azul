package Azul.example.Azul.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.sql.Date;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ateliers")
public class Atelier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false)
    private Date date;

    @Column(nullable = false)
    private LocalTime heure;

    @Column(nullable = false)
    private String categorie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coach_id")
    @JsonBackReference("coach-ateliers")
    private Coach coach;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    @JsonBackReference("admin-ateliers")
    private Admin admin;

    @OneToMany(mappedBy = "atelier", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Reservation> reservations = new ArrayList<>();

    @OneToMany(mappedBy = "atelier", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference("atelier-photos")
    private List<Image> photos = new ArrayList<>();

    @OneToOne(mappedBy = "atelier", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference("atelier-menucafe")
    private MenuCafe menuCafe;

    // CONSTRUCTEURS
    public Atelier() {}

    public Atelier(String nom, String description, Date date, LocalTime heure, String categorie) {
        this.nom = nom;
        this.description = description;
        this.date = date;
        this.heure = heure;
        this.categorie = categorie;
    }

    // GETTERS ET SETTERS
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }

    public LocalTime getHeure() { return heure; }
    public void setHeure(LocalTime heure) { this.heure = heure; }

    public String getCategorie() { return categorie; }
    public void setCategorie(String categorie) { this.categorie = categorie; }

    public Coach getCoach() { return coach; }
    public void setCoach(Coach coach) { this.coach = coach; }

    public Admin getAdmin() { return admin; }
    public void setAdmin(Admin admin) { this.admin = admin; }

    public List<Reservation> getReservations() { return reservations; }
    public void setReservations(List<Reservation> reservations) { this.reservations = reservations; }

    public List<Image> getPhotos() { return photos; }
    public void setPhotos(List<Image> photos) {
        this.photos = photos;
    }

    public MenuCafe getMenuCafe() { return menuCafe; }
    public void setMenuCafe(MenuCafe menuCafe) { this.menuCafe = menuCafe; }
}
