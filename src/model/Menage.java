package model;
import java.util.*;

/**
 * 
 */
public class Menage {

    /**
     * Default constructor
     */
    public Menage() {
    }

    /**
     * 
     */
    private int id;

    /**
     * 
     */
    private String nom;

    /**
     * 
     */
    private String adresse;

    /**
     * 
     */
    private int pointsFidelite;

    /**
     * 
     */
    private List historiqueDepots;

    /**
     * 
     */
    public String adresseMail;

    /**
     * 
     */
    public int motdepasse;

    /**
     * 
     */
    public int CodeAcess;

    /**
     * 
     */
    public String adresseMail;

    /**
     * 
     */
    public String motDePasse;

    /**
     * 
     */
    public void deposerDechets() {
        // TODO implement here
    }

    /**
     * 
     */
    public void consulterPoints() {
        // TODO implement here
    }

    /**
     * 
     */
    public void convertirPointsEnReduction() {
        // TODO implement here
    }

    /**
     * @return
     */
    public List<OperationDepot> afficheHistorique() {
        // TODO implement here
        return null;
    }

    /**
     * @param dechets 
     * @param poubelle 
     * @return
     */
    public void deposerDechets(List<Dechet> dechets, BacIntelligente poubelle) {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public void enregistrer() {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public int consulterPoints() {
        // TODO implement here
        return 0;
    }

    /**
     * @param offre 
     * @return
     */
    public boolean convertirPointsEnReduction(offreFidelite offre) {
        // TODO implement here
        return false;
    }

    /**
     * 
     */
    public void convertirPoints() {
        // TODO implement here
    }

}