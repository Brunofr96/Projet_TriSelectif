package main;


import test.*;




public class MainTest {

    public static void main(String[] args) {
        System.out.println("\n=============================");
        System.out.println("DÉMARRAGE DES TESTS");
        System.out.println("=============================\n");

        try {
        	MenageTest.runTests();
            BacIntelligentTest.runTests();
            DechetTest.runTests();
            CentreDeTriTest.runTests();
            OffreFideliteTest.runTests();
            CommerceTest.runTest();
            // appelle toutes les autres classes de test ici
        } catch (Exception e) {
            System.out.println("❌ Erreur pendant les tests : " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("\n=============================");
        System.out.println("TOUS LES TESTS TERMINÉS");
        System.out.println("=============================\n");
    }
}
