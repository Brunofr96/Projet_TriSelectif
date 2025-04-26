package model;


/**
 * Représente une offre de fidélité qu’un ménage peut utiliser avec ses points.
 */
public class OffreFidelite {

    private int id;
    private String description;
    private int cout;
    private String type;

    public OffreFidelite(int id, String description, int cout, String type) {
        this.id = id;
        this.description = description;
        this.cout = cout;
        this.type = type;
    }

    public boolean verifPointAchat(Menage menage) {
        return menage.getPointsFidelite() >= cout;
    }

    public boolean appliquerOperation(Menage menage) {
        if (verifPointAchat(menage)) {
            menage.setPointsFidelite(menage.getPointsFidelite() - cout);
            return true;
        }
        return false;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public int getCout() {
        return cout;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Offre #" + id + " : " + description + " (" + cout + " pts)";
    }
}
