package fr.epf.restaurant.model;

import java.math.BigDecimal;

public class FournisseurIngredient {

    private Fournisseur fournisseur;
    private Ingredient ingredient;
    private BigDecimal prixUnitaire;

    public FournisseurIngredient() {
    }

    public FournisseurIngredient(Fournisseur fournisseur, Ingredient ingredient, BigDecimal prixUnitaire) {
        this.fournisseur = fournisseur;
        this.ingredient = ingredient;
        this.prixUnitaire = prixUnitaire;
    }

    public Fournisseur getFournisseur() {
        return fournisseur;
    }

    public void setFournisseur(Fournisseur fournisseur) {
        this.fournisseur = fournisseur;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public BigDecimal getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(BigDecimal prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }
}