package model;

import java.util.*;

/**
 * Représente un commerce partenaire du centre de tri.
 */
public class Commerce {

    private int id;
    private String nom;
    private List<ContratPartenaire> contrats;

    public Commerce(int id, String nom) {
        this.id = id;
        this.nom = nom;
        this.contrats = new ArrayList<>();
    }

    public void ajouterContrat(ContratPartenaire contrat) {
        contrats.add(contrat);
    }

    /**
     * Vérifie si un ménage peut obtenir une réduction via une offre et retire les points si c’est possible.
     */
    public boolean offrirReduction(Menage m, OffreFidelite offre) {
        if (m.getPointsFidelite() >= offre.getCout()) {
            m.setPointsFidelite(m.getPointsFidelite() - offre.getCout());
            System.out.println("✅ Réduction appliquée à " + m.getNom());
            return true;
        } else {
            System.out.println("❌ Pas assez de points pour " + m.getNom());
            return false;
        }
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public List<ContratPartenaire> getContrats() {
        return contrats;
    }

    @Override
    public String toString() {
        return "Commerce #" + id + " : " + nom;
    }
}
