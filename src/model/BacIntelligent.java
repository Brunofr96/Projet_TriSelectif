package model;

import java.util.*;

/**
 * Représente une poubelle intelligente.
 */
public class BacIntelligent {

    private int id;
    private TypePoubelle type;
    private double capaciteMax;
    private String emplacement;
    private List<Dechet> dechets;
    private boolean estPleine;

    public BacIntelligent(int id, TypePoubelle type, double capaciteMax, String emplacement) {
        this.id = id;
        this.type = type;
        this.capaciteMax = capaciteMax;
        this.emplacement = emplacement;
        this.dechets = new ArrayList<>();
        this.estPleine = false;
    }

    public boolean verifierAcces(int code) {
        // Ici on pourrait vérifier un accès par badge, etc.
        return true;
    }

    public int ajouterDechet(List<Dechet> nouveauxDechets, Menage m) {
        int pointsTotal = 0;
        for (Dechet d : nouveauxDechets) {
            if (dechetsConformes(d)) {
                dechets.add(d);
                pointsTotal += calculerPoints(d);
            } else {
                pointsTotal -= 5; // pénalité arbitraire
            }
        }
        if (getTotalPoids() >= capaciteMax) {
            estPleine = true;
            notifierCentreDeTri();
        }
        return pointsTotal;
    }

    private boolean dechetsConformes(Dechet d) {
        switch (type) {
            case VERTE:
                return d.getType() == TypeDechet.VERRE;
            case JAUNE:
                return d.getType() == TypeDechet.PLASTIQUE || d.getType() == TypeDechet.CARTON || d.getType() == TypeDechet.METAL;
            case BLEUE:
                return d.getType() == TypeDechet.PAPIER;
            case CLASSIQUE:
                return true;
            default:
                return false;
        }
    }

    public int calculerPoints(Dechet d) {
        // On peut attribuer des points par kg, ici simple exemple
        return (int) (d.getPoids() * 10);
    }

    public void notifierCentreDeTri() {
        System.out.println("⚠️ Bac " + id + " est plein à l'emplacement " + emplacement);
    }

    public double getTotalPoids() {
        return dechets.stream().mapToDouble(Dechet::getPoids).sum();
    }

    // Getters
    public int getId() {
        return id;
    }

    public TypePoubelle getType() {
        return type;
    }

    public String getEmplacement() {
        return emplacement;
    }

    public boolean isEstPleine() {
        return estPleine;
    }

    public List<Dechet> getDechets() {
        return dechets;
    }
}
