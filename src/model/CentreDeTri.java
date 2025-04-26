package model;


import java.util.*;

/**
 * Classe représentant un centre de tri.
 */
public class CentreDeTri {

    private String nom;
    private String adresse;
    private List<BacIntelligent> poubelles;
    

    public CentreDeTri(String nom, String adresse) {
        this.nom = nom;
        this.adresse = adresse;
        this.poubelles = new ArrayList<>();
        
    }
    

    public void ajouterPoubelle(BacIntelligent p) {
        poubelles.add(p);
    }

    public void retirerPoubelle(BacIntelligent p) {
        poubelles.remove(p);
    }

    public void collecterDechets() {
        System.out.println("Collecte des déchets en cours...");
        // Logique à développer si besoin
    }

    public Map<String, Double> genererStatistiques() {
        Map<String, Double> stats = new HashMap<>();
        for (BacIntelligent bac : poubelles) {
            stats.put(bac.getType().toString(), bac.getTotalPoids());
        }
        return stats;
    }

    public void envoyerDechet(TypeDechet typeDechet) {
        System.out.println("Envoi du déchet de type " + typeDechet + " au conditionneur/recycleur...");
        // À compléter avec les conditionneurs/recycleurs associés
    }

    public void envoyerDechetsRecycleur() {
        System.out.println("Envoi global des déchets aux recycleurs...");
        // À compléter
    }

    public boolean verifierTri(List<Dechet> dechets, TypePoubelle typePoubelle) {
        for (Dechet d : dechets) {
            if (!typePoubelleAccepteDechet(typePoubelle, d.getType())) {
                return false;
            }
        }
        return true;
    }

    private boolean typePoubelleAccepteDechet(TypePoubelle typePoubelle, TypeDechet typeDechet) {
        switch (typePoubelle) {
            case VERTE:
                return typeDechet == TypeDechet.VERRE;
            case JAUNE:
                return typeDechet == TypeDechet.PLASTIQUE || typeDechet == TypeDechet.CARTON || typeDechet == TypeDechet.METAL;
            case BLEUE:
                return typeDechet == TypeDechet.PAPIER;
            case CLASSIQUE:
                return true; // accepte tout le reste
            default:
                return false;
        }
    }

    // Getters et setters
    public String getNom() {
        return nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public List<BacIntelligent> getPoubelles() {
        return poubelles;
    }
}
