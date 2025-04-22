package model;

public class Recycleur {
    private int id;
    private String nom;
    private String adresse;

    public Recycleur(int id, String nom, String adresse) {
        this.id = id;
        this.nom = nom;
        this.adresse = adresse;
    }

    public void recyclerDechets() {
        System.out.println("Le recycleur recycle les déchets.");
    }

    public void vendreProduits() {
        System.out.println("Le recycleur vend les produits recyclés.");
    }
}
