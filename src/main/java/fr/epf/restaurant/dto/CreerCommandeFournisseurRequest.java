package fr.epf.restaurant.dto;

import java.util.List;

public class CreerCommandeFournisseurRequest {

    private Long fournisseurId;
    private List<LigneCommande> lignes;

    public CreerCommandeFournisseurRequest() {
    }

    public CreerCommandeFournisseurRequest(Long fournisseurId, List<LigneCommande> lignes) {
        this.fournisseurId = fournisseurId;
        this.lignes = lignes;
    }

    public Long getFournisseurId() {
        return fournisseurId;
    }

    public void setFournisseurId(Long fournisseurId) {
        this.fournisseurId = fournisseurId;
    }

    public List<LigneCommande> getLignes() {
        return lignes;
    }

    public void setLignes(List<LigneCommande> lignes) {
        this.lignes = lignes;
    }

    public static class LigneCommande {
        private Long ingredientId;
        private Double quantite;
        private Double prixUnitaire;

        public LigneCommande() {
        }

        public LigneCommande(Long ingredientId, Double quantite, Double prixUnitaire) {
            this.ingredientId = ingredientId;
            this.quantite = quantite;
            this.prixUnitaire = prixUnitaire;
        }

        public Long getIngredientId() {
            return ingredientId;
        }

        public void setIngredientId(Long ingredientId) {
            this.ingredientId = ingredientId;
        }

        public Double getQuantite() {
            return quantite;
        }

        public void setQuantite(Double quantite) {
            this.quantite = quantite;
        }

        public Double getPrixUnitaire() {
            return prixUnitaire;
        }

        public void setPrixUnitaire(Double prixUnitaire) {
            this.prixUnitaire = prixUnitaire;
        }
    }
}