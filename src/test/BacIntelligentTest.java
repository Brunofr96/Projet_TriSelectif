package test;
import model.*;
import java.util.*;

public class BacIntelligentTest {

    public static void runTests() {

        BacIntelligent bac = new BacIntelligent(1, TypePoubelle.JAUNE, 2.0, "Rue Verte");

        // Ajout du code d'accès autorisé
        bac.ajouterCodeAccesAutorise(1234);

        Menage m1 = new Menage(1, "Famille Test", "12 rue A", "test@mail.com", "mdp", 1234, 0, null);
        Menage m2 = new Menage(2, "Famille Refusée", "12 rue B", "refus@mail.com", "mdp", 9999, 0, null);

        List<Dechet> dechetsOk = List.of(
            new Dechet(TypeDechet.PLASTIQUE, 0.8),
            new Dechet(TypeDechet.CARTON, 0.7)
        );

        List<Dechet> dechetsPasOk = List.of(
            new Dechet(TypeDechet.VERRE, 0.5)
        );

        System.out.println("\n TEST 1 : Dépôt valide autorisé");
        m1.deposerDechets(dechetsOk, bac);
        System.out.println("Points : " + m1.getPointsFidelite());

        System.out.println("\n TEST 2 : Dépôt avec déchet non conforme");
        m1.deposerDechets(dechetsPasOk, bac);

        System.out.println("\n TEST 3 : Dépôt avec code refusé");
        m2.deposerDechets(dechetsOk, bac);

        System.out.println("\n TEST 4 : Bac plein");
        m1.deposerDechets(List.of(new Dechet(TypeDechet.PLASTIQUE, 1.0)), bac); // dépasse capacité
    }
}

