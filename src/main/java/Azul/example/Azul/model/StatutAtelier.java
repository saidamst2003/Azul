package Azul.example.Azul.model;
public enum StatutAtelier {


    PLANIFIE("Planifié"),
    OUVERT("Ouvert aux réservations"),
    EN_COURS("En cours"),
    TERMINE("Terminé"),
    ANNULE("Annulé");

    private final String libelle;

    StatutAtelier(String libelle) {
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }

    /**
     * Vérifie si l'atelier est disponible pour les réservations
     * @return true si l'atelier est ouvert aux réservations
     */
    public boolean estDisponible() {
        return this == OUVERT;
    }



    public boolean estTermine() {
        return this == TERMINE;
    }



    public static StatutAtelier fromLibelle(String libelle) {
        for (StatutAtelier statut : values()) {
            if (statut.getLibelle().equalsIgnoreCase(libelle)) {
                return statut;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return this.libelle;
    }
}
