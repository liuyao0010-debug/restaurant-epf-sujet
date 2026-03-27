package fr.epf.restaurant.service;

import fr.epf.restaurant.dao.IngredientDao;
import fr.epf.restaurant.dto.AlerteStockDto;
import fr.epf.restaurant.exception.StockInsuffisantException;
import fr.epf.restaurant.model.Ingredient;
import fr.epf.restaurant.model.Plat;
import fr.epf.restaurant.model.PlatIngredient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StockService {

    private final IngredientDao ingredientDao;

    public StockService(IngredientDao ingredientDao) {
        this.ingredientDao = ingredientDao;
    }

    public void verifierStockSuffisant(Map<Long, Double> quantitesRequisesParIngredient) {
        List<String> alertes = new ArrayList<>();
        for (Map.Entry<Long, Double> entry : quantitesRequisesParIngredient.entrySet()) {
            Long ingredientId = entry.getKey();
            Double quantiteRequise = entry.getValue();
            Double stockActuel = ingredientDao.getStock(ingredientId);
            if (stockActuel < quantiteRequise) {
                Ingredient ingredient = ingredientDao.findById(ingredientId);
                alertes.add(String.format("Stock insuffisant pour %s : besoin de %.2f %s, disponible %.2f %s",
                        ingredient.getNom(), quantiteRequise, ingredient.getUnite(),
                        stockActuel, ingredient.getUnite()));
            }
        }
        if (!alertes.isEmpty()) {
            throw new StockInsuffisantException(String.join("; ", alertes));
        }
    }

    public Map<Long, Double> calculerQuantitesRequises(List<Plat> plats, List<Integer> quantites) {
        Map<Long, Double> quantitesParIngredient = new HashMap<>();
        for (int i = 0; i < plats.size(); i++) {
            Plat plat = plats.get(i);
            Integer quantitePlat = quantites.get(i);
            for (PlatIngredient platIngredient : plat.getIngredients()) {
                Long ingredientId = platIngredient.getIngredient().getId();
                Double quantiteRequise = platIngredient.getQuantiteRequise() * quantitePlat;
                quantitesParIngredient.merge(ingredientId, quantiteRequise, Double::sum);
            }
        }
        return quantitesParIngredient;
    }

    @Transactional
    public void consommerStock(Map<Long, Double> quantitesParIngredient) {
        for (Map.Entry<Long, Double> entry : quantitesParIngredient.entrySet()) {
            ingredientDao.decrementStock(entry.getKey(), entry.getValue());
        }
    }

    @Transactional
    public void restituerStock(Map<Long, Double> quantitesParIngredient) {
        for (Map.Entry<Long, Double> entry : quantitesParIngredient.entrySet()) {
            ingredientDao.incrementStock(entry.getKey(), entry.getValue());
        }
    }

    public List<AlerteStockDto> getIngredientsEnAlerte() {
        List<Ingredient> ingredients = ingredientDao.findUnderAlertThreshold();
        List<AlerteStockDto> alertes = new ArrayList<>();
        for (Ingredient ingredient : ingredients) {
            double quantiteACommander = 2 * (ingredient.getSeuilAlerte() - ingredient.getStockActuel());
            alertes.add(new AlerteStockDto(
                    ingredient.getId(),
                    ingredient.getNom(),
                    ingredient.getUnite(),
                    ingredient.getStockActuel(),
                    ingredient.getSeuilAlerte(),
                    quantiteACommander
            ));
        }
        return alertes;
    }

    public boolean estEnAlerte(Long ingredientId) {
        Ingredient ingredient = ingredientDao.findById(ingredientId);
        if (ingredient == null) {
            return false;
        }
        return ingredient.getStockActuel() < ingredient.getSeuilAlerte();
    }

    public List<String> getAlertesApresConsommation(Map<Long, Double> quantitesConsommees) {
        List<String> alertes = new ArrayList<>();
        for (Long ingredientId : quantitesConsommees.keySet()) {
            if (estEnAlerte(ingredientId)) {
                Ingredient ingredient = ingredientDao.findById(ingredientId);
                String alerte = String.format(
                        "Attention : %s en dessous du seuil d'alerte (stock: %.2f %s, seuil: %.2f %s)",
                        ingredient.getNom(),
                        ingredient.getStockActuel(),
                        ingredient.getUnite(),
                        ingredient.getSeuilAlerte(),
                        ingredient.getUnite()
                );
                alertes.add(alerte);
            }
        }
        return alertes;
    }

    public Map<Long, Double> calculerQuantitesConsommeesParCommande(List<Plat> plats, List<Integer> quantites) {
        return calculerQuantitesRequises(plats, quantites);
    }
}