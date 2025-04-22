package model;

/**
 * Représente un déchet déposé par un ménage.
 */
public class Dechet {

    private TypeDechet type;
    private double poids;

    public Dechet(TypeDechet type, double poids) {
        this.type = type;
        this.poids = poids;
    }

    public TypeDechet getType() {
        return type;
    }

    public void setType(TypeDechet type) {
        this.type = type;
    }

    public double getPoids() {
        return poids;
    }

    public void setPoids(double poids) {
        this.poids = poids;
    }

    @Override
    public String toString() {
        return "Déchet [type=" + type + ", poids=" + poids + " kg]";
    }
}
