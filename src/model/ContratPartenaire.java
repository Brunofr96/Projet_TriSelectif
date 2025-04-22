package model;

import java.util.*;

/**
 * Représente un contrat entre un commerce et le centre de tri.
 */
public class ContratPartenaire {

    private int id;
    private Date dateDebut;
    private Date dateFin;
    private List<String> categoriesProduits;
    private int pointsNecessaires;
    private boolean renouvelable;

    public ContratPartenaire(int id, Date dateDebut, Date dateFin, List<String> categoriesProduits,
                             int pointsNecessaires, boolean renouvelable) {
        this.id = id;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.categoriesProduits = categoriesProduits;
        this.pointsNecessaires = pointsNecessaires;
        this.renouvelable = renouvelable;
    }

    public boolean estValide(Date date) {
        return date != null && !date.before(dateDebut) && !date.after(dateFin);
    }

    // Getters & Setters
    public int getId() {
        return id;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public List<String> getCategoriesProduits() {
        return categoriesProduits;
    }

    public int getPointsNecessaires() {
        return pointsNecessaires;
    }

    public boolean isRenouvelable() {
        return renouvelable;
    }

    @Override
    public String toString() {
        return "Contrat #" + id + " [" + dateDebut + " à " + dateFin + "] - Points: " + pointsNecessaires;
    }
}
