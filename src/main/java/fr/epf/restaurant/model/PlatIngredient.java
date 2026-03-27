package fr.epf.restaurant.model;

public class PlatIngredient {

    private Ingredient ingredient;
    private Double quantiteRequise;

    public PlatIngredient() {
    }

    public PlatIngredient(Ingredient ingredient, Double quantiteRequise) {
        this.ingredient = ingredient;
        this.quantiteRequise = quantiteRequise;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public Double getQuantiteRequise() {
        return quantiteRequise;
    }

    public void setQuantiteRequise(Double quantiteRequise) {
        this.quantiteRequise = quantiteRequise;
    }
}