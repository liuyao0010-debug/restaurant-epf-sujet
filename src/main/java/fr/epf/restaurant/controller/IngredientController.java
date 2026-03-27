package fr.epf.restaurant.controller;

import fr.epf.restaurant.dao.FournisseurDao;
import fr.epf.restaurant.dao.IngredientDao;
import fr.epf.restaurant.dto.AlerteStockDto;
import fr.epf.restaurant.dto.PrixFournisseurDto;
import fr.epf.restaurant.dto.RecommandationDto;
import fr.epf.restaurant.exception.ResourceNotFoundException;
import fr.epf.restaurant.model.FournisseurIngredient;
import fr.epf.restaurant.model.Ingredient;
import fr.epf.restaurant.service.StockService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/ingredients")
public class IngredientController {

    private final IngredientDao ingredientDao;
    private final FournisseurDao fournisseurDao;
    private final StockService stockService;

    public IngredientController(IngredientDao ingredientDao,
                                FournisseurDao fournisseurDao,
                                StockService stockService) {
        this.ingredientDao = ingredientDao;
        this.fournisseurDao = fournisseurDao;
        this.stockService = stockService;
    }

    @GetMapping
    public List<Ingredient> getAllIngredients() {
        return ingredientDao.findAll();
    }

    @GetMapping("/alertes")
    public List<AlerteStockDto> getIngredientsEnAlerte() {
        return stockService.getIngredientsEnAlerte();
    }

    @GetMapping("/{id}")
    public Ingredient getIngredientById(@PathVariable Long id) {
        Ingredient ingredient = ingredientDao.findById(id);
        if (ingredient == null) {
            throw new ResourceNotFoundException("Ingrédient non trouvé avec l'id: " + id);
        }
        return ingredient;
    }

    @GetMapping("/{id}/recommandation")
    public RecommandationDto getRecommandation(@PathVariable Long id) {
        Ingredient ingredient = ingredientDao.findById(id);
        if (ingredient == null) {
            throw new ResourceNotFoundException("Ingrédient non trouvé avec l'id: " + id);
        }

        List<FournisseurIngredient> fournisseurs = fournisseurDao.findCheapestSuppliersForIngredient(id);
        if (fournisseurs.isEmpty()) {
            throw new ResourceNotFoundException("Aucun fournisseur trouvé pour cet ingrédient");
        }

        FournisseurIngredient meilleurFournisseur = fournisseurs.get(0);

        Double quantiteRecommande;
        if (ingredient.getStockActuel() < ingredient.getSeuilAlerte()) {
            quantiteRecommande = 2 * (ingredient.getSeuilAlerte() - ingredient.getStockActuel());
        } else {
            quantiteRecommande = ingredient.getSeuilAlerte();
        }

        RecommandationDto dto = new RecommandationDto();
        dto.setFournisseurId(meilleurFournisseur.getFournisseur().getId());
        dto.setFournisseurNom(meilleurFournisseur.getFournisseur().getNom());
        dto.setPrixUnitaire(meilleurFournisseur.getPrixUnitaire());
        dto.setQuantiteRecommande(quantiteRecommande);

        return dto;
    }

    @GetMapping("/{id}/prix")
    public List<PrixFournisseurDto> getPrixFournisseurs(@PathVariable Long id) {
        Ingredient ingredient = ingredientDao.findById(id);
        if (ingredient == null) {
            throw new ResourceNotFoundException("Ingrédient non trouvé avec l'id: " + id);
        }

        List<FournisseurIngredient> fournisseurs = fournisseurDao.findCheapestSuppliersForIngredient(id);
        return fournisseurs.stream()
                .map(fi -> {
                    PrixFournisseurDto dto = new PrixFournisseurDto();
                    dto.setFournisseurId(fi.getFournisseur().getId());
                    dto.setFournisseurNom(fi.getFournisseur().getNom());
                    dto.setPrixUnitaire(fi.getPrixUnitaire());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}