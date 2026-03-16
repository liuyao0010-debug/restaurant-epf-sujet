// src/main/java/fr/epf/restaurant/dto/CommandeFournisseurDTO.java
package fr.epf.restaurant.dto;

import java.time.LocalDateTime;

public class CommandeFournisseurDTO {
    private Long id;
    private Long fournisseurId;
    private LocalDateTime dateCommande;
    private String statut;

    public CommandeFournisseurDTO(Long id, Long fournisseurId, LocalDateTime dateCommande, String statut) {
        this.id = id;
        this.fournisseurId = fournisseurId;
        this.dateCommande = dateCommande;
        this.statut = statut;
    }

    // getters & setters
    public Long getId() {
        return id;
    }
    public Long getFournisseurId() {
        return fournisseurId;
    }
    public LocalDateTime getDateCommande() {
        return dateCommande;
    }
    public String getStatut() {
        return statut;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public void setFournisseurId(Long fournisseurId) {
        this.fournisseurId = fournisseurId;
    }
    public void setDateCommande(LocalDateTime dateCommande) {
        this.dateCommande = dateCommande;
    }
    public void setStatut(String statut) {
        this.statut = statut;
    }
}