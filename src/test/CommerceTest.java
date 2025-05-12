package test;

import model.*;

public class CommerceTest {

    public static void runTest() {
        System.out.println("=== TEST OFFRES DE RÉDUCTION ===");

        System.out.println("\n▶ Test 1 : Réduction appliquée (points suffisants)");
        testReductionSuffisante();

        System.out.println("\n▶ Test 2 : Réduction refusée (points insuffisants)");
        testReductionInsuffisante();
    }

    private static void testReductionSuffisante() {
        Menage menage = new Menage("Famille Points", "10 rue Fidèle", "fidel@mail.com", "pass", 1234, null);
        menage.setPointsFidelite(50); // Donne 50 points

        OffreFidelite offre = new OffreFidelite(1, "Réduction 5%", 20, "alimentaire", 1);
        Commerce commerce = new Commerce(1, "Super U");

        boolean resultat = commerce.offrirReduction(menage, offre);

        System.out.println("Réduction appliquée ? " + resultat);
        System.out.println("Points restants : " + menage.getPointsFidelite());

        // ✅ Assertion attendue
        assert resultat : "La réduction aurait dû être appliquée";
        assert menage.getPointsFidelite() == 30 : "Il devrait rester 30 points";
    }

    private static void testReductionInsuffisante() {
        Menage menage = new Menage("Famille Fauchée", "20 rue Pauvre", "pauvre@mail.com", "pass", 5678, null);
        menage.setPointsFidelite(10); // Donne 10 points

        OffreFidelite offre = new OffreFidelite(2, "Réduction 10%", 30, "électroménager", 2);
        Commerce commerce = new Commerce(2, "Carrefour");

        boolean resultat = commerce.offrirReduction(menage, offre);

        System.out.println("Réduction appliquée ? " + resultat);
        System.out.println("Points restants : " + menage.getPointsFidelite());

        // ❌ Pas assez de points donc pas de réduction
        assert !resultat : "La réduction n'aurait pas dû être appliquée";
        assert menage.getPointsFidelite() == 10 : "Les points ne doivent pas changer";
    }
}
