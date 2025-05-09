package model;

import java.util.*;

/**
 * Représente une poubelle intelligente avec contrôle d'accès et attribution de points.
 */
public class BacIntelligent {

    private int idBacIntelligent;
    private double capaciteMax;
    private String emplacement;
    private boolean estPleine;
    private double poidsActuel;


    private TypePoubelle typePoubelle;           // En BDD → Id_TypePoubelle (stocké en texte ou FK)
    private int idCentreDeTri;                   // Clé étrangère (reliée au Centre)

    private List<Dechet> dechets;
    private Set<Integer> codesAccesAutorises;

    public BacIntelligent(int idBacIntelligent, TypePoubelle typePoubelle, double capaciteMax, String emplacement) {
        this.idBacIntelligent = idBacIntelligent;
        this.typePoubelle = typePoubelle;
        this.capaciteMax = capaciteMax;
        this.emplacement = emplacement;
        this.estPleine = false;

        this.dechets = new ArrayList<>();
        this.codesAccesAutorises = new HashSet<>();
    }

    public void ajouterCodeAccesAutorise(int code) {
        codesAccesAutorises.add(code);
    }

    public boolean verifierAcces(int code) {
        return codesAccesAutorises.contains(code);
    }

    public int ajouterDechet(List<Dechet> nouveauxDechets, Menage menage) {
        int pointsTotal = 0;

        for (Dechet d : nouveauxDechets) {
            if (!dechetsConformes(d)) {
                System.out.println(" Déchet non conforme au type de bac !");
                pointsTotal -= 5;
                continue;
            }

            double poidsApresAjout = getTotalPoids() + d.getPoids();
            if (poidsApresAjout > capaciteMax) {
                System.out.println(" Bac plein : impossible d’ajouter ce déchet.");
                estPleine = true;
                notifierCentreDeTri();
                break;
            }

            dechets.add(d);
            pointsTotal += calculerPoints(d);
        }

        if (getTotalPoids() >= capaciteMax) {
            estPleine = true;
            notifierCentreDeTri();
        }

        return pointsTotal;
    }
    
    public double getPoidsActuel() {
        return poidsActuel;
    }

    public void setPoidsActuel(double poidsActuel) {
        this.poidsActuel = poidsActuel;
    }


    private boolean dechetsConformes(Dechet d) {
        switch (typePoubelle) {
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
        return (int) (d.getPoids() * 10);
    }

    public void notifierCentreDeTri() {
        System.out.println("Bac " + idBacIntelligent + " (" + typePoubelle + ") est plein à : " + emplacement);
    }

    public double getTotalPoids() {
        return dechets.stream().mapToDouble(Dechet::getPoids).sum();
    }

    // Getters & Setters

    public int getId() {
        return idBacIntelligent;
    }

    public double getCapaciteMax() {
        return capaciteMax;
    }

    public String getEmplacement() {
        return emplacement;
    }

    public boolean isEstPleine() {
        return estPleine;
    }

    public void setEstPleine(boolean estPleine) {
        this.estPleine = estPleine;
    }

    public TypePoubelle getType() {
        return typePoubelle;
    }

    public int getIdCentreDeTri() {
        return idCentreDeTri;
    }

    public void setIdCentreDeTri(int idCentreDeTri) {
        this.idCentreDeTri = idCentreDeTri;
    }

    public List<Dechet> getDechets() {
        return dechets;
    }

    public Set<Integer> getCodesAccesAutorises() {
        return codesAccesAutorises;
    }

    public int getIdTypePoubelle() {
        return typePoubelle.getId();
    }

    public String getTypePoubelleLabel() {
        return typePoubelle.toString();
    }

    @Override
    public String toString() {
        return typePoubelle + " - " + emplacement;
    }
}
