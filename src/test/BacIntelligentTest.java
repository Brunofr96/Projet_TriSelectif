package test;

import model.*;
import java.util.*;

public class BacIntelligentTest {

	public static void runTests() {

        BacIntelligent bac = new BacIntelligent(1, TypePoubelle.JAUNE, 2.0, "Rue Verte");

        // Ajout du code d'accès autorisé
        bac.ajouterCodeAccesAutorise(1234);

        Menage m1 = new Menage("Famille Test", "12 rue A", "test@mail.com", "mdp", 1234, null);
        Menage m2 = new Menage("Famille Refusée", "12 rue B", "refus@mail.com", "mdp", 9999, null);

        List<Dechet> dechetsOk = List.of(
            new Dechet(TypeDechet.PLASTIQUE, 0.8),
            new Dechet(TypeDechet.CARTON, 0.7)
        );

        List<Dechet> dechetsPasOk = List.of(
            new Dechet(TypeDechet.VERRE, 0.5)
        );

        List<Dechet> dechetsPourRemplir = List.of(
            new Dechet(TypeDechet.METAL, 0.6),
            new Dechet(TypeDechet.PLASTIQUE, 0.9)
        );

        List<Dechet> dechetTropLourd = List.of(
            new Dechet(TypeDechet.PLASTIQUE, 1.0) // devrait dépasser la capacité max
        );

        System.out.println("\n TEST 1 : Dépôt valide autorisé");
        m1.deposerDechets(dechetsOk, bac);
        System.out.println(" Points après dépôt : " + m1.getPointsFidelite());

        System.out.println("\n TEST 2 : Dépôt avec déchet non conforme");
        m1.deposerDechets(dechetsPasOk, bac);
        System.out.println(" Points après pénalité : " + m1.getPointsFidelite());

        System.out.println("\n TEST 3 : Dépôt avec code refusé");
        m2.deposerDechets(dechetsOk, bac);

        System.out.println("\n TEST 4 : Dépôt partiel jusqu’à remplir le bac");
        m1.deposerDechets(dechetsPourRemplir, bac);
        System.out.println(" Bac plein ? " + bac.isEstPleine());

        System.out.println("\n TEST 5 : Tentative de dépôt quand le bac est plein");
        m1.deposerDechets(dechetTropLourd, bac);
        System.out.println(" Poids total final du bac : " + bac.getTotalPoids() + " kg");
    }

    public static void main(String[] args) {
        runTests();
    }
}
