package Azul.example.Azul.repository;

import Azul.example.Azul.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ImageRepository extends JpaRepository<Image, UUID> {
}
