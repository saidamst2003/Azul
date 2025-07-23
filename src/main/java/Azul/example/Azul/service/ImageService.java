package Azul.example.Azul.service;

import Azul.example.Azul.dto.ImageDto;
import Azul.example.Azul.model.Image;
import Azul.example.Azul.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ImageService {

    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private ImageRepository imageRepository;

    public ResponseEntity<Map<String, String>> uploadImage(ImageDto imageModel) {
        if (imageModel.getFile().isEmpty() || imageModel.getName().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        String url = cloudinaryService.uploadFile(imageModel.getFile(), "my-folder");

        if (url == null) return ResponseEntity.badRequest().build();

        Image image = new Image();
        image.setName(imageModel.getName());
        image.setUrl(url);
        imageRepository.save(image);

        return ResponseEntity.ok(Map.of("url", url));
    }
}
