package model;

public enum TypePoubelle {
    VERTE(1),
    JAUNE(2),
    BLEUE(3),
    CLASSIQUE(4);

    private final int id;

    TypePoubelle(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    // 🔁 Conversion depuis un id en base de données
    public static TypePoubelle fromId(int id) {
        for (TypePoubelle type : TypePoubelle.values()) {
            if (type.getId() == id) return type;
        }
        throw new IllegalArgumentException("Aucun type de poubelle pour l'id : " + id);
    }

    @Override
    public String toString() {
        // Permet d'afficher "Verte", "Jaune", etc. dans les ComboBox
        return name().charAt(0) + name().substring(1).toLowerCase();
    }
}
