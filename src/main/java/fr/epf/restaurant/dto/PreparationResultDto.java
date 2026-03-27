package fr.epf.restaurant.dto;

import fr.epf.restaurant.model.CommandeClient;
import java.util.List;

public class PreparationResultDto {

    private CommandeClient commande;
    private List<String> alertes;

    public PreparationResultDto() {
    }

    public PreparationResultDto(CommandeClient commande, List<String> alertes) {
        this.commande = commande;
        this.alertes = alertes;
    }

    public CommandeClient getCommande() {
        return commande;
    }

    public void setCommande(CommandeClient commande) {
        this.commande = commande;
    }

    public List<String> getAlertes() {
        return alertes;
    }

    public void setAlertes(List<String> alertes) {
        this.alertes = alertes;
    }
}