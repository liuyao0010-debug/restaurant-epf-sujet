package fr.epf.restaurant.model;

import java.math.BigDecimal;
import java.util.List;

public class Plat {

    private Long id;
    private String nom;
    private String description;
    private BigDecimal prix;
    private List<PlatIngredient> ingredients;

    public Plat() {
    }

    public Plat(Long id, String nom, String description, BigDecimal prix) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.prix = prix;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrix() {
        return prix;
    }

    public void setPrix(BigDecimal prix) {
        this.prix = prix;
    }

    public List<PlatIngredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<PlatIngredient> ingredients) {
        this.ingredients = ingredients;
    }
}