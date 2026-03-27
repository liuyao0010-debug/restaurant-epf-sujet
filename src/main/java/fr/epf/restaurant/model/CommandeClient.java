package fr.epf.restaurant.model;

import java.time.LocalDateTime;
import java.util.List;

public class CommandeClient {

    private Long id;
    private Client client;
    private LocalDateTime dateCommande;
    private StatutCommandeClient statut;
    private List<LigneCommandeClient> lignes;

    public enum StatutCommandeClient {
        EN_ATTENTE, EN_PREPARATION, SERVIE, ANNULEE
    }

    public CommandeClient() {
    }

    public CommandeClient(Long id, Client client, LocalDateTime dateCommande,
                          StatutCommandeClient statut, List<LigneCommandeClient> lignes) {
        this.id = id;
        this.client = client;
        this.dateCommande = dateCommande;
        this.statut = statut;
        this.lignes = lignes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public LocalDateTime getDateCommande() {
        return dateCommande;
    }

    public void setDateCommande(LocalDateTime dateCommande) {
        this.dateCommande = dateCommande;
    }

    public StatutCommandeClient getStatut() {
        return statut;
    }

    public void setStatut(StatutCommandeClient statut) {
        this.statut = statut;
    }

    public List<LigneCommandeClient> getLignes() {
        return lignes;
    }

    public void setLignes(List<LigneCommandeClient> lignes) {
        this.lignes = lignes;
    }
}