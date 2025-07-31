package Azul.example.Azul.controller;

package Azul.example.Azul.controller;

import Azul.example.Azul.model.Coach;
import Azul.example.Azul.service.CoachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/coaches")
@CrossOrigin(origins = "http://localhost:4200")
public class CoachController {

    @Autowired
    private CoachService coachService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT')")
    public ResponseEntity<List<Coach>> getAllCoaches() {
        List<Coach> coaches = coachService.getAllCoaches();
        return ResponseEntity.ok(coaches);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT')")
    public ResponseEntity<Coach> getCoachById(@PathVariable Long id) {
        Coach coach = coachService.getCoachById(id);
        if (coach != null) {
            return ResponseEntity.ok(coach);
        }
        return ResponseEntity.notFound().build();
    }
}
