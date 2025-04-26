package test;

import model.*;

public class OffreFideliteTest {

    public static void runTests() {

        Menage m = new Menage(1, "Famille Points", "10 rue Fidèle", "fidel@mail.com", "pass", 1234, 20, null);

        OffreFidelite offre1 = new OffreFidelite(1, "Réduction 5%", 15, "alimentaire");
        OffreFidelite offre2 = new OffreFidelite(2, "Réduction 10%", 30, "hygiène");

        System.out.println("\n✅ Test 1 : Points suffisants");
        boolean result1 = m.convertirPointsEnReduction(offre1);
        System.out.println("Réduction appliquée ? " + result1 + " | Points restants : " + m.getPointsFidelite());

        
        System.out.println("\n❌ Test 2 : Points insuffisants");
        boolean result2 = m.convertirPointsEnReduction(offre2);
        System.out.println("Réduction appliquée ? " + result2 + " | Points restants : " + m.getPointsFidelite());
    }
}
