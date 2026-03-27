package fr.epf.restaurant.dto;

import java.util.List;

public class CreerCommandeClientRequest {

    private Long clientId;
    private List<LigneCommande> lignes;

    public CreerCommandeClientRequest() {
    }

    public CreerCommandeClientRequest(Long clientId, List<LigneCommande> lignes) {
        this.clientId = clientId;
        this.lignes = lignes;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public List<LigneCommande> getLignes() {
        return lignes;
    }

    public void setLignes(List<LigneCommande> lignes) {
        this.lignes = lignes;
    }

    public static class LigneCommande {
        private Long platId;
        private Integer quantite;

        public LigneCommande() {
        }

        public LigneCommande(Long platId, Integer quantite) {
            this.platId = platId;
            this.quantite = quantite;
        }

        public Long getPlatId() {
            return platId;
        }

        public void setPlatId(Long platId) {
            this.platId = platId;
        }

        public Integer getQuantite() {
            return quantite;
        }

        public void setQuantite(Integer quantite) {
            this.quantite = quantite;
        }
    }
}