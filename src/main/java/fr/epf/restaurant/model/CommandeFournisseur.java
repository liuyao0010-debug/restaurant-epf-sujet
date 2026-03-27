package fr.epf.restaurant.model;

import java.time.LocalDateTime;
import java.util.List;

public class CommandeFournisseur {

    private Long id;
    private Fournisseur fournisseur;
    private LocalDateTime dateCommande;
    private StatutCommandeFournisseur statut;
    private List<LigneCommandeFournisseur> lignes;

    public enum StatutCommandeFournisseur {
        EN_ATTENTE, ENVOYEE, RECUE, ANNULEE
    }

    public CommandeFournisseur() {
    }

    public CommandeFournisseur(Long id, Fournisseur fournisseur, LocalDateTime dateCommande,
                               StatutCommandeFournisseur statut, List<LigneCommandeFournisseur> lignes) {
        this.id = id;
        this.fournisseur = fournisseur;
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

    public Fournisseur getFournisseur() {
        return fournisseur;
    }

    public void setFournisseur(Fournisseur fournisseur) {
        this.fournisseur = fournisseur;
    }

    public LocalDateTime getDateCommande() {
        return dateCommande;
    }

    public void setDateCommande(LocalDateTime dateCommande) {
        this.dateCommande = dateCommande;
    }

    public StatutCommandeFournisseur getStatut() {
        return statut;
    }

    public void setStatut(StatutCommandeFournisseur statut) {
        this.statut = statut;
    }

    public List<LigneCommandeFournisseur> getLignes() {
        return lignes;
    }

    public void setLignes(List<LigneCommandeFournisseur> lignes) {
        this.lignes = lignes;
    }
}