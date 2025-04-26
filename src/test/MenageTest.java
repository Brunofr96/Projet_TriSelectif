package test;

import model.*;
import java.util.*;

public class MenageTest {

    public void testCreationMenage() {
        Menage m = new Menage(1, "Famille Martin", "12 rue des Lilas", "martin@mail.com", "azerty", 1234, 0, null);
        System.out.println("✅ Test création Ménage : " + m.getNom());
    }

    public void testDepotAvecPoints() {
        // Création du ménage
        Menage m = new Menage(2, "Famille Durand", "8 rue Bleue", "durand@mail.com", "1234", 5678, 0, null);

        // Création de la poubelle intelligente
        BacIntelligent bac = new BacIntelligent(101, TypePoubelle.JAUNE, 50.0, "Rue de la Paix");
        bac.ajouterCodeAccesAutorise(5678);  // autoriser le code du ménage

        // Création des déchets
        List<Dechet> dechets = new ArrayList<>();
        dechets.add(new Dechet(TypeDechet.PLASTIQUE, 0.5));
        dechets.add(new Dechet(TypeDechet.PLASTIQUE, 0.8));

        // Dépôt
        m.deposerDechets(dechets, bac);

        // Affichage des points
        System.out.println("Points fidélité : " + m.getPointsFidelite());

        // Historique
        for (OperationDepot d : m.getHistoriqueDepots()) {
            System.out.println(d.toString());
        }
    }

    public void testDepotAccesRefuse() {
        Menage m = new Menage(3, "Famille Refusée", "Rue Barrée", "refus@mail.com", "pass", 9999,0, null);
        BacIntelligent bac = new BacIntelligent(102, TypePoubelle.VERTE, 30.0, "Place Rouge");

        // Aucun code autorisé ajouté → accès refusé attendu
        List<Dechet> dechets = List.of(new Dechet(TypeDechet.VERRE, 1.0));
        m.deposerDechets(dechets, bac);
    }

    public static void runTests() {
        MenageTest test = new MenageTest();

        System.out.println("=== Test 1 : Création ===");
        test.testCreationMenage();

        System.out.println("\n=== Test 2 : Dépôt autorisé ===");
        test.testDepotAvecPoints();

        System.out.println("\n=== Test 3 : Accès refusé ===");
        test.testDepotAccesRefuse();
    }
}
