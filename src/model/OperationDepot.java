package model;
import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class OperationDepot {

	private int id;
    private LocalDateTime dateDepot;
    private double quantite;
    private int pointsGagnes;

    private Menage menage;
    private BacIntelligent bac;
    private List<Dechet> dechets;

    public OperationDepot(int id, Menage menage, BacIntelligent bac, List<Dechet> dechets, int pointsGagnes) {
        this.id = id;
        this.dateDepot = LocalDateTime.now();
        this.menage = menage;
        this.bac = bac;
        this.dechets = dechets;
        this.quantite = calculerPoidsTotal(dechets);
        this.pointsGagnes = pointsGagnes;
    }

    private double calculerPoidsTotal(List<Dechet> dechets) {
        double total = 0;
        for (Dechet d : dechets) {
            total += d.getPoids();
        }
        return total;
    }

    public int getPointsGagnes() {
        return pointsGagnes;
    }

    public double getQuantite() {
        return quantite;
    }

    public void setQuantite(double quantite) {
		this.quantite = quantite;
	}

	public LocalDateTime getDateDepot() {
        return dateDepot;
    }

    public BacIntelligent getBac() {
        return bac;
    }

    public Menage getMenage() {
        return menage;
    }

    public List<Dechet> getDechets() {
        return dechets;
    }

    @Override
    public String toString() {
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return "Opération #" + id + " - " + dateDepot + "\n"
                + "Ménage : " + menage.getNom() + " - Bac : " + bac.getType() + "\n"
                + "Poids : " + quantite + " kg | Points : " + pointsGagnes + "\n";
    }
    
	public String getDateDepotFormatee() {
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
	    return this.dateDepot.format(formatter);
	}
	
	public void setDateDepot(LocalDateTime dateDepot) {
	    this.dateDepot = dateDepot;
	}

    
    public void enregistrerDepot(Menage m, BacIntelligent b) {
	    System.out.println("--> Dépôt enregistré :");
	    System.out.println("Ménage : " + m.getNom());
	    System.out.println("Bac : " + b.getType());
	    System.out.println("Poids : " + this.quantite + " kg");
	    System.out.println("Points gagnés : " + this.pointsGagnes);
    }
    public String getNomBac() {
        if (bac != null && bac.getType() != null) {
            return bac.getType().toString();
        }
        return "Inconnu";
    }

    public String getTypeDechet() {
        if (dechets != null && !dechets.isEmpty() && dechets.get(0).getType() != null) {
            return dechets.get(0).getType().toString();
        }
        return "Inconnu";
    }


}