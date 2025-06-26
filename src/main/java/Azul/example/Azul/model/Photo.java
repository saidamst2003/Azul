package Azul.example.Azul.model;

import jakarta.persistence.*;

@Entity
@Table(name = "photos")
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String urlImg;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "atelier_id")
    private Atelier atelier;

    // Constructeurs
    public Photo() {}

    public Photo(String urlImg) {
        this.urlImg = urlImg;
    }

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUrlImg() { return urlImg; }
    public void setUrlImg(String urlImg) { this.urlImg = urlImg; }

    public Atelier getAtelier() { return atelier; }
    public void setAtelier(Atelier atelier) { this.atelier = atelier; }

    // Méthodes métier
    public void associer() {
        // Logique d'association avec atelier
    }
}
