package model;

import java.util.*;

/**
 * Représente une poubelle intelligente avec contrôle d'accès et attribution de points.
 */
public class BacIntelligent {

    private int id;
    private TypePoubelle type;
    private double capaciteMax;
    private String emplacement;
    private boolean estPleine;

    private List<Dechet> dechets;
    private Set<Integer> codesAccesAutorises;

    public BacIntelligent(int id, TypePoubelle type, double capaciteMax, String emplacement) {
        this.id = id;
        this.type = type;
        this.capaciteMax = capaciteMax;
        this.emplacement = emplacement;
        this.dechets = new ArrayList<>();
        this.codesAccesAutorises = new HashSet<>();
        this.estPleine = false;
    }

    // Ajoute un code d’accès autorisé
    public void ajouterCodeAccesAutorise(int code) {
        codesAccesAutorises.add(code);
    }

    // Vérifie si un code est autorisé à accéder à la poubelle
    public boolean verifierAcces(int code) {
        return codesAccesAutorises.contains(code);
    }

    // Ajoute des déchets à la poubelle et retourne les points gagnés
    public int ajouterDechet(List<Dechet> nouveauxDechets, Menage menage) {
        int pointsTotal = 0;

        for (Dechet d : nouveauxDechets) {
            if (dechetsConformes(d)) {
                dechets.add(d);
                pointsTotal += calculerPoints(d);
            } else {
                System.out.println("❌ Déchet non conforme au type de bac !");
                pointsTotal -= 5; // pénalité
            }
        }

        if (getTotalPoids() >= capaciteMax) {
            estPleine = true;
            notifierCentreDeTri();
        }

        return pointsTotal;
    }

    // Vérifie si un déchet est conforme à la catégorie de la poubelle
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

    // Calcule les points de fidélité en fonction du poids
    public int calculerPoints(Dechet d) {
        return (int) (d.getPoids() * 10);
    }

    // Notifie le centre de tri que le bac est plein
    public void notifierCentreDeTri() {
        System.out.println("⚠️ Bac " + id + " (" + type + ") est plein à l'emplacement : " + emplacement);
    }

    // Calcule le poids total des déchets dans le bac
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

    public Set<Integer> getCodesAccesAutorises() {
        return codesAccesAutorises;
    }
}
