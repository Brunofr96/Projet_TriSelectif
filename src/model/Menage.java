
package model;
import model.BacIntelligent;
import model.Dechet;
import model.OperationDepot;
import model.OffreFidelite;


import java.util.ArrayList;
import java.util.List;

public class Menage {

    private int id;
    private String nom;
    private String adresse;
    private String adresseMail;
    private String motDePasse;
    private int codeAcces;
    private int pointsFidelite;
    private List<OperationDepot> historiqueDepots;

    // Constructeur par défaut
    public Menage() {
        this.historiqueDepots = new ArrayList<>();
    }

    // Constructeur avec tous les attributs
    public Menage(int id, String nom, String adresse, String adresseMail, String motDePasse, int codeAcces,
                  int pointsFidelite, List<OperationDepot> historiqueDepots) {
        this.id = id;
        this.nom = nom;
        this.adresse = adresse;
        this.adresseMail = adresseMail;
        this.motDePasse = motDePasse;
        this.codeAcces = codeAcces;
        this.pointsFidelite = pointsFidelite;
        this.historiqueDepots = (historiqueDepots != null) ? historiqueDepots : new ArrayList<>();
    }

    // Getters et Setters
    public String getNom() { return nom; }
    
    public int getId() {
        return id;
    }


    public void setNom(String nom) { this.nom = nom; }

    public String getAdresse() { return adresse; }

    public void setAdresse(String adresse) { this.adresse = adresse; }

    public String getAdresseMail() { return adresseMail; }

    public void setAdresseMail(String adresseMail) { this.adresseMail = adresseMail; }

    public String getMotDePasse() { return motDePasse; }

    public void setMotDePasse(String motDePasse) { this.motDePasse = motDePasse; }

    public int getCodeAcces() { return codeAcces; }

    public void setCodeAcces(int codeAcces) { this.codeAcces = codeAcces; }

    public int getPointsFidelite() { return pointsFidelite; }

    public void setPointsFidelite(int pointsFidelite) { this.pointsFidelite = pointsFidelite; }

    public void ajouterPoints(int points) {
        this.pointsFidelite += points;
    }

    public List<OperationDepot> getHistoriqueDepots() {
        return historiqueDepots;
    }

    public void setHistoriqueDepots(List<OperationDepot> historiqueDepots) {
        this.historiqueDepots = historiqueDepots;
    }

    public int consulterPoints() {
        return this.pointsFidelite;
    }

    public boolean convertirPointsEnReduction(OffreFidelite offre) {
        if (this.pointsFidelite >= offre.getCout()) {
            this.pointsFidelite -= offre.getCout();
            return true;
        }
        return false;
    }

    public void deposerDechets(List<Dechet> dechets, BacIntelligent bac) {
        if (bac.verifierAcces(this.codeAcces)) {
            int pointsGagnes = bac.ajouterDechet(dechets, this);
            OperationDepot depot = new OperationDepot(this.id, this, bac, dechets, pointsGagnes);
            historiqueDepots.add(depot);
            ajouterPoints(pointsGagnes);
        } else {
            System.out.println(" Accès refusé à la poubelle.");
        }
    }

    public List<OperationDepot> afficheHistorique() {
        return this.historiqueDepots;
    }

    public void enregistrer() {
        System.out.println("Ménage " + nom + " enregistré dans la base.");
    }

    @Override
    public String toString() {
        return "Menage [id=" + id + ", nom=" + nom + ", adresse=" + adresse + ", adresseMail=" + adresseMail
                + ", codeAcces=" + codeAcces + ", pointsFidelite=" + pointsFidelite
                + ", historiqueDepots=" + historiqueDepots + "]";
    }
}
