package fr.epf.restaurant.model;

import java.math.BigDecimal;

public class LigneCommandeFournisseur {

    private Long id;
    private Ingredient ingredient;
    private Double quantiteCommandee;
    private BigDecimal prixUnitaire;

    public LigneCommandeFournisseur() {
    }

    public LigneCommandeFournisseur(Long id, Ingredient ingredient,
                                    Double quantiteCommandee, BigDecimal prixUnitaire) {
        this.id = id;
        this.ingredient = ingredient;
        this.quantiteCommandee = quantiteCommandee;
        this.prixUnitaire = prixUnitaire;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public Double getQuantiteCommandee() {
        return quantiteCommandee;
    }

    public void setQuantiteCommandee(Double quantiteCommandee) {
        this.quantiteCommandee = quantiteCommandee;
    }

    public BigDecimal getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(BigDecimal prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }
}