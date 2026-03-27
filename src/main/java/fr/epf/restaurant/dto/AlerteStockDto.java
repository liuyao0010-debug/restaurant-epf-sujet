package fr.epf.restaurant.dto;

public class AlerteStockDto {

    private Long id;
    private String nom;
    private String unite;
    private Double stockActuel;
    private Double seuilAlerte;
    private Double quantiteACommander;

    public AlerteStockDto() {
    }

    public AlerteStockDto(Long id, String nom, String unite,
                          Double stockActuel, Double seuilAlerte, Double quantiteACommander) {
        this.id = id;
        this.nom = nom;
        this.unite = unite;
        this.stockActuel = stockActuel;
        this.seuilAlerte = seuilAlerte;
        this.quantiteACommander = quantiteACommander;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getUnite() {
        return unite;
    }

    public void setUnite(String unite) {
        this.unite = unite;
    }

    public Double getStockActuel() {
        return stockActuel;
    }

    public void setStockActuel(Double stockActuel) {
        this.stockActuel = stockActuel;
    }

    public Double getSeuilAlerte() {
        return seuilAlerte;
    }

    public void setSeuilAlerte(Double seuilAlerte) {
        this.seuilAlerte = seuilAlerte;
    }

    public Double getQuantiteACommander() {
        return quantiteACommander;
    }

    public void setQuantiteACommander(Double quantiteACommander) {
        this.quantiteACommander = quantiteACommander;
    }
}