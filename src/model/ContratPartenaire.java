// Classe : ContratPartenaire.java
package model;

import java.util.*;

import java.util.Date;
import java.util.List;

public class ContratPartenaire {
    private int id;
    private Date dateDebut;
    private Date dateFin;
    private List<String> categoriesProduits;
    private int pointsNecessaires;
    private boolean renouvelable;
    private int idCommerce;
    private int idCentreDeTri;

    public ContratPartenaire(int id, Date dateDebut, Date dateFin, List<String> categoriesProduits,
                             int pointsNecessaires, boolean renouvelable, int idCommerce, int idCentreDeTri) {
        this.id = id;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.categoriesProduits = categoriesProduits;
        this.pointsNecessaires = pointsNecessaires;
        this.renouvelable = renouvelable;
        this.idCommerce = idCommerce;
        this.idCentreDeTri = idCentreDeTri;
    }

    public boolean estValide(Date date) {
        return date != null && !date.before(dateDebut) && !date.after(dateFin);
    }

    public int getId() { return id; }
    public Date getDateDebut() { return dateDebut; }
    public Date getDateFin() { return dateFin; }
    public List<String> getCategoriesProduits() { return categoriesProduits; }
    public int getPointsNecessaires() { return pointsNecessaires; }
    public boolean isRenouvelable() { return renouvelable; }
    public int getIdCommerce() { return idCommerce; }
    public int getIdCentreDeTri() { return idCentreDeTri; }

    @Override
    public String toString() {
        return "Contrat #" + id + " [" + dateDebut + " à " + dateFin + "] - Points: " + pointsNecessaires;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public void setCategoriesProduits(List<String> categoriesProduits) {
        this.categoriesProduits = categoriesProduits;
    }

    public void setPointsNecessaires(int pointsNecessaires) {
        this.pointsNecessaires = pointsNecessaires;
    }

    public void setRenouvelable(boolean renouvelable) {
        this.renouvelable = renouvelable;
    }

    public void setIdCommerce(int idCommerce) {
        this.idCommerce = idCommerce;
    }

    public void setIdCentreDeTri(int idCentreDeTri) {
        this.idCentreDeTri = idCentreDeTri;
    }

    

}
