package fr.epf.restaurant.controller;

import fr.epf.restaurant.dto.FournisseurDTO;
import fr.epf.restaurant.service.FournisseurService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/fournisseurs")
public class FournisseurController {

    private final FournisseurService service;

    public FournisseurController(FournisseurService service) {
        this.service = service;
    }

    @GetMapping
    public List<FournisseurDTO> getFournisseurs() {
        return service.getAllFournisseurs();
    }

    @GetMapping("/{id}/catalogue")
    public List<PlatDTO> getCatalogue(@PathVariable Long id) {
        return service.getCatalogue(id);
    }
}