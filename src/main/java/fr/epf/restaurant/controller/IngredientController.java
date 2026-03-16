package fr.epf.restaurant.controller;

import fr.epf.restaurant.dto.IngredientDTO;
import fr.epf.restaurant.service.IngredientService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/ingredients")
public class IngredientsController {

    private final IngredientService service;

    public IngredientsController(IngredientService service) {
        this.service = service;
    }

    @GetMapping
    public List<IngredientDTO> getIngredients() {
        return service.getAllIngredients();
    }

    @GetMapping("/alertes")
    public List<IngredientDTO> getAlertes() {
        return service.getIngredientsEnRupture();
    }

    @GetMapping("/{id}/prix")
    public Double getPrix(@PathVariable Long id) {
        return service.getPrix(id);
    }

    @GetMapping("/{id}/recommandation")
    public String getRecommandation(@PathVariable Long id) {
        return service.getRecommandation(id);
    }
}