package Azul.example.Azul.repository;

import Azul.example.Azul.model.Atelier;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface AtelierRepository extends JpaRepository<Atelier, Long> {

    // Méthodes de recherche personnalisées
    List<Atelier> findByNomContainingIgnoreCase(String nom);

    List<Atelier> findByDate(Date date);

    @EntityGraph(attributePaths = {"photos"})
    List<Atelier> findAll();

    @Query("SELECT a FROM Atelier a LEFT JOIN FETCH a.photos LEFT JOIN FETCH a.coach")
    List<Atelier> findAllWithCoachDetails();

    @Query("SELECT a FROM Atelier a LEFT JOIN FETCH a.coach")
    List<Atelier> findAllWithCoachOnly();

    @Query("SELECT a FROM Atelier a WHERE a.date >= :dateDebut AND a.date <= :dateFin")
    List<Atelier> findByDateBetween(@Param("dateDebut") Date dateDebut, @Param("dateFin") Date dateFin);

    @Query("SELECT a FROM Atelier a LEFT JOIN FETCH a.photos WHERE a.coach.id = :coachId")
    List<Atelier> findByCoachId(@Param("coachId") Long coachId);
}
