package model;

public class Dechet {

    private TypeDechet type;
    private double poids;

    public Dechet(TypeDechet type, double poids) {
        if (type == null) {
            throw new IllegalArgumentException("Le type de déchet ne peut pas être null.");
        }
        if (poids < 0) {
            throw new IllegalArgumentException("Le poids du déchet ne peut pas être négatif.");
        }
        this.type = type;
        this.poids = poids;
    }

    public TypeDechet getType() {
        return type;
    }

    public void setType(TypeDechet type) {
        if (type == null) {
            throw new IllegalArgumentException("Le type de déchet ne peut pas être null.");
        }
        this.type = type;
    }

    public double getPoids() {
        return poids;
    }

    public void setPoids(double poids) {
        if (poids < 0) {
            throw new IllegalArgumentException("Le poids du déchet ne peut pas être négatif.");
        }
        this.poids = poids;
    }

    @Override
    public String toString() {
        return "Déchet [type=" + type + ", poids=" + poids + " kg]";
    }
}
	