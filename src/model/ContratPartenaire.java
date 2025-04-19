package model;
import java.util.*;

/**
 * 
 */
public class ContratPartenaire {

    /**
     * Default constructor
     */
    public ContratPartenaire() {
    }

    /**
     * 
     */
    private int id;

    /**
     * 
     */
    private Date dateDebut;

    /**
     * 
     */
    private Date datefin;

    /**
     * 
     */
    private List categoriesProduits;

    /**
     * 
     */
    private int pointsNecessaires;

    /**
     * 
     */
    private boolean renouvelable;

    /**
     * @param date 
     * @return
     */
    public boolean estValide(Date date) {
        // TODO implement here
        return false;
    }

}