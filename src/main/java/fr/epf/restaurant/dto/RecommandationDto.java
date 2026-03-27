// dto/RecommandationDto.java
package fr.epf.restaurant.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class RecommandationDto {

    private Long fournisseurId;
    private String fournisseurNom;
    private BigDecimal prixUnitaire;


    @JsonProperty("quantiteRecommandee")
    private Double quantiteRecommande;

    public RecommandationDto() {
    }

    public RecommandationDto(Long fournisseurId, String fournisseurNom,
                             BigDecimal prixUnitaire, Double quantiteRecommande) {
        this.fournisseurId = fournisseurId;
        this.fournisseurNom = fournisseurNom;
        this.prixUnitaire = prixUnitaire;
        this.quantiteRecommande = quantiteRecommande;
    }

    public Long getFournisseurId() {
        return fournisseurId;
    }

    public void setFournisseurId(Long fournisseurId) {
        this.fournisseurId = fournisseurId;
    }

    public String getFournisseurNom() {
        return fournisseurNom;
    }

    public void setFournisseurNom(String fournisseurNom) {
        this.fournisseurNom = fournisseurNom;
    }

    public BigDecimal getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(BigDecimal prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public Double getQuantiteRecommande() {
        return quantiteRecommande;
    }

    public void setQuantiteRecommande(Double quantiteRecommande) {
        this.quantiteRecommande = quantiteRecommande;
    }
}