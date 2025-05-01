package test;

import dao.BacIntelligentDAO;
import model.BacIntelligent;
import model.TypePoubelle;

public class BacIntelligentDAOTest {

    public static void main(String[] args) {
        // Création d'un bac intelligent à tester
        BacIntelligent bac = new BacIntelligent(
            105,                      // Id_BacIntelligent
            TypePoubelle.JAUNE,       // Type (sera inséré en tant qu'entier via getId())
            60.0,                     // capacitéMax
            "Zone industrielle"       // emplacement
        );

        // Spécifie l'ID du centre de tri lié (doit exister dans la BDD)
        bac.setIdCentreDeTri(1); // ⚠️ Assure-toi que CentreDeTri avec id = 1 existe bien

        // DAO
        BacIntelligentDAO dao = new BacIntelligentDAO();
        dao.enregistrerBac(bac);
    }
}
