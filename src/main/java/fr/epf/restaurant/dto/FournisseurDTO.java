// src/main/java/fr/epf/restaurant/dto/FournisseurDTO.java
package fr.epf.restaurant.dto;

public class FournisseurDTO {
    private Long id;
    private String nom;
    private String contact;
    private String email;

    public FournisseurDTO(Long id, String nom, String contact, String email) {
        this.id = id;
        this.nom = nom;
        this.contact = contact;
        this.email = email;
    }

    // getters & setters
    public Long getId() {
        return id;
    }
    public String getNom() {
        return nom;
    }
    public String getContact() {
        return contact;
    }
    public String getEmail() {
        return email;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public void setContact(String contact) {
        this.contact = contact;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}