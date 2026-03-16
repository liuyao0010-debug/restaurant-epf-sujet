package fr.epf.restaurant.dto;

import java.util.List;

public class PlatDTO {
    private Long id;
    private String nom;
    private String description;
    private Double prix;
    private List<IngredientDTO> ingredients;

    public PlatDTO(Long id, String nom, String description, Double prix, List<IngredientDTO> ingredients) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.prix = prix;
        this.ingredients = ingredients;
    }

    public PlatDTO(Long id, String nom, String description, Double prix) {
        this(id, nom, description, prix, null);
    }

    public Long getId() { return id; }
    public String getNom() { return nom; }
    public String getDescription() { return description; }
    public Double getPrix() { return prix; }
    public List<IngredientDTO> getIngredients() { return ingredients; }
    public void setIngredients(List<IngredientDTO> ingredients) { this.ingredients = ingredients; }
}
