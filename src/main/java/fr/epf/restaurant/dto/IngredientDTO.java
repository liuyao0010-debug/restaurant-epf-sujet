// src/main/java/fr/epf/restaurant/dto/IngredientDTO.java
package fr.epf.restaurant.dto;

public class IngredientDTO {
    private Long id;
    private String nom;
    private String unite;
    private Double stockActuel;
    private Double seuilAlerte;

    public IngredientDTO(Long id, String nom, String unite, Double stockActuel, Double seuilAlerte) {
        this.id = id;
        this.nom = nom;
        this.unite = unite;
        this.stockActuel = stockActuel;
        this.seuilAlerte = seuilAlerte;
    }

    // getters & setters
    public Long getId() {
        return id;
    }
    public String getNom() {
        return nom;
    }
    public String getUnite() {
        return unite;
    }
    public Double getStockActuel() {
        return stockActuel;
    }
    public Double getSeuilAlerte() {
        return seuilAlerte;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public void setUnite(String unite) {
        this.unite = unite;
    }
    public void setStockActuel(Double stockActuel) {
        this.stockActuel = stockActuel;
    }
    public void setSeuilAlerte(Double seuilAlerte) {
        this.seuilAlerte = seuilAlerte;
    }
}