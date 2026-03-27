package fr.epf.restaurant.model;

public class LigneCommandeClient {

    private Long id;
    private Plat plat;
    private Integer quantite;

    public LigneCommandeClient() {
    }

    public LigneCommandeClient(Long id, Plat plat, Integer quantite) {
        this.id = id;
        this.plat = plat;
        this.quantite = quantite;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Plat getPlat() {
        return plat;
    }

    public void setPlat(Plat plat) {
        this.plat = plat;
    }

    public Integer getQuantite() {
        return quantite;
    }

    public void setQuantite(Integer quantite) {
        this.quantite = quantite;
    }
}