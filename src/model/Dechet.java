package model;

public class Dechet {

    private int id; // 🔑 ID auto-généré par la BDD
    private TypeDechet type;
    private double poids;

    // ✅ Constructeur pour l'insertion
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

    // ✅ Constructeur utilisé lors de la lecture en base
    public Dechet(int id, TypeDechet type, double poids) {
        this(type, poids);
        this.id = id;
    }

    // ✅ Getters et Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        return "Déchet [id=" + id + ", type=" + type + ", poids=" + poids + " kg]";
    }
}
