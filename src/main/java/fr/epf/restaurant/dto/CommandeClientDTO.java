// src/main/java/fr/epf/restaurant/dto/CommandeClientDTO.java
package fr.epf.restaurant.dto;

import java.time.LocalDateTime;

public class CommandeClientDTO {
    private Long id;
    private Long clientId;
    private LocalDateTime dateCommande;
    private String statut;

    public CommandeClientDTO(Long id, Long clientId, LocalDateTime dateCommande, String statut) {
        this.id = id;
        this.clientId = clientId;
        this.dateCommande = dateCommande;
        this.statut = statut;
    }

    // getters & setters
    public Long getId() {
        return id;
    }
    public Long getClientId() {
        return clientId;
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
    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }
    public void setDateCommande(LocalDateTime dateCommande) {
        this.dateCommande = dateCommande;
    }
    public void setStatut(String statut) {
        this.statut = statut;
    }
}