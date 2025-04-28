package test;

import model.*;

public class CommerceTest {

    public static void runTest() {
        System.out.println(" Test 1 : Réduction appliquée");
        testReductionSuffisante();

        System.out.println("\n Test 2 : Réduction refusée (pas assez de points)");
        testReductionInsuffisante();
    }

    public static void testReductionSuffisante() {
        // Création d'un ménage avec assez de points
        Menage menage = new Menage(1, "Famille Points", "10 rue Fidèle", "fidel@mail.com", "pass", 1234, 50, null);

        // Création d'une offre fidélité
        OffreFidelite offre = new OffreFidelite(1, "Réduction 5%", 20, "alimentaire");

        // Création du commerce
        Commerce commerce = new Commerce(1, "Super U");

        // Tentative d'appliquer la réduction
        boolean reductionAppliquee = commerce.offrirReduction(menage, offre);

        System.out.println("Réduction appliquée ? " + reductionAppliquee);
        System.out.println("Points restants : " + menage.getPointsFidelite());

        // Résultat attendu : réduction appliquée et points diminués
    }

    public static void testReductionInsuffisante() {
        // Création d'un ménage avec trop peu de points
        Menage menage = new Menage(2, "Famille Fauchée", "20 rue Pauvre", "pauvre@mail.com", "pass", 5678, 10, null);

        // Création d'une offre fidélité plus chère que les points
        OffreFidelite offre = new OffreFidelite(2, "Réduction 10%", 30, "électroménager");

        // Création du commerce
        Commerce commerce = new Commerce(2, "Carrefour");

        // Tentative d'appliquer la réduction
        boolean reductionAppliquee = commerce.offrirReduction(menage, offre);

        System.out.println("Réduction appliquée ? " + reductionAppliquee);
        System.out.println("Points restants : " + menage.getPointsFidelite());

        // Résultat attendu : réduction NON appliquée et points inchangés
    }
}
