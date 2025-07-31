package Azul.example.Azul.service;

import Azul.example.Azul.dto.ImageDto;
import Azul.example.Azul.model.Image;
import Azul.example.Azul.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;

@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    public ResponseEntity<Map<String, String>> uploadImage(ImageDto imageModel) {
        MultipartFile file = imageModel.getFile();
        if (file.isEmpty() || imageModel.getName().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        // Save file locally
        String uploadsDir = System.getProperty("user.dir") + java.io.File.separator + "uploads" + java.io.File.separator;
        String extension = "";
        String originalName = file.getOriginalFilename();
        if (originalName != null && originalName.contains(".")) {
            extension = originalName.substring(originalName.lastIndexOf('.'));
        }
        String uniqueName = UUID.randomUUID() + extension;
        Path filePath = Paths.get(uploadsDir + uniqueName);
        try {
            Files.createDirectories(filePath.getParent());
            file.transferTo(filePath.toFile());
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }

        String url = "/uploads/" + uniqueName;

        Image image = new Image();
        image.setName(imageModel.getName());
        image.setUrl(url);
        imageRepository.save(image);

        return ResponseEntity.ok(Map.of("url", url));
    }
}
