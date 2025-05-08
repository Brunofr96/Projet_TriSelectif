package model;

public enum TypeDechet {
    PLASTIQUE(1),
    VERRE(2),
    CARTON(3),
    METAL(4),
    PAPIER(5);

    private final int id;

    TypeDechet(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    // 🔁 Utilisé dans DAO pour retrouver l'enum depuis la base
    public static TypeDechet fromId(int id) {
        for (TypeDechet t : values()) {
            if (t.id == id) return t;
        }
        throw new IllegalArgumentException("Aucun type trouvé pour l'id : " + id);
    }

    @Override
    public String toString() {
        return name().charAt(0) + name().substring(1).toLowerCase(); // Ex: Plastique
    }
}
