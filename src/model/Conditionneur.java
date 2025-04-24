package model;
public class Conditionneur {
    private int id;
    private String nom;
    private String adresse;

    public Conditionneur(int id, String nom, String adresse) {
        this.id = id;
        this.nom = nom;
        this.adresse = adresse;
    }

    public void preparerDechets() {
        System.out.println("Le conditionneur prépare les déchets.");
    }

    public void envoyerDechetsRecycleur() {
        System.out.println("Envoi des déchets au recycleur.");
    }
}
