package model;

public class CategorieProduit {

    private int id;
    private String libelle;
    private int idContrat;

    public CategorieProduit(int id, String libelle, int idContrat) {
        this.id = id;
        this.libelle = libelle;
        this.idContrat = idContrat;
    }

    // Constructeur sans id (pour insertion)
    public CategorieProduit(String libelle, int idContrat) {
        this.libelle = libelle;
        this.idContrat = idContrat;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public int getIdContrat() {
        return idContrat;
    }

    public void setIdContrat(int idContrat) {
        this.idContrat = idContrat;
    }

    @Override
    public String toString() {
        return libelle;
    }
}
