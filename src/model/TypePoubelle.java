package model;
/**
 * 
 */
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
}
