package fr.epf.restaurant.controller;

import fr.epf.restaurant.dto.PlatDTO;
import fr.epf.restaurant.service.PlatService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/plats")
public class PlatController {

    private final PlatService service;

    public PlatController(PlatService service) {
        this.service = service;
    }

    // GET /api/plats
    @GetMapping
    public List<PlatDTO> getPlats() {
        return service.getAllPlats();
    }

    // GET /api/plats/{id}
    @GetMapping("/{id}")
    public PlatDTO getPlat(@PathVariable Long id) {
        return service.getPlatById(id);
    }
}