package test;

import model.*;

public class DechetTest {

    // Test 1 : Création normale d'un déchet
    public void testCreationDechet() {
        Dechet d = new Dechet(TypeDechet.PLASTIQUE, 0.5);
        System.out.println("✅ Déchet créé : " + d.toString());
        System.out.println("Type attendu : PLASTIQUE, Type réel : " + d.getType());
        System.out.println("Poids attendu : 0.5 kg, Poids réel : " + d.getPoids());
    }

    // Test 2 : Modification normale d'un déchet
    public void testModificationDechet() {
        Dechet d = new Dechet(TypeDechet.METAL, 1.2);
        d.setType(TypeDechet.VERRE);
        d.setPoids(2.5);
        System.out.println("✅ Déchet modifié : " + d.toString());
        System.out.println("Nouveau type attendu : VERRE, Type réel : " + d.getType());
        System.out.println("Nouveau poids attendu : 2.5 kg, Poids réel : " + d.getPoids());
    }

    // Test 3 : Création avec poids négatif (erreur)
    public void testPoidsNegatifCreation() {
        try {
            Dechet d = new Dechet(TypeDechet.PLASTIQUE, -2.0);
            System.out.println("❌ Erreur : un déchet avec un poids négatif a été créé : " + d.toString());
        } catch (IllegalArgumentException e) {
            System.out.println("✅ Test réussi : impossible de créer un déchet avec un poids négatif !");
        }
    }

    // Test 4 : Création avec type null (erreur)
    public void testTypeNullCreation() {
        try {
            Dechet d = new Dechet(null, 2.0);
            System.out.println("❌ Erreur : un déchet avec un type null a été créé : " + d.toString());
        } catch (IllegalArgumentException e) {
            System.out.println("✅ Test réussi : impossible de créer un déchet avec un type null !");
        }
    }

    // Test 5 : Modifier poids en négatif après création (erreur)
    public void testPoidsNegatifSet() {
        try {
            Dechet d = new Dechet(TypeDechet.PAPIER, 1.0);
            d.setPoids(-5.0);
            System.out.println("❌ Erreur : setPoids a accepté un poids négatif !");
        } catch (IllegalArgumentException e) {
            System.out.println("✅ Test réussi : impossible de mettre un poids négatif après création !");
        }
    }

    // Test 6 : Modifier type en null après création (erreur)
    public void testTypeNullSet() {
        try {
            Dechet d = new Dechet(TypeDechet.VERRE, 1.5);
            d.setType(null);
            System.out.println("❌ Erreur : setType a accepté un type null !");
        } catch (IllegalArgumentException e) {
            System.out.println("✅ Test réussi : impossible de mettre un type null après création !");
        }
    }

    // Main pour tout lancer
    public static void main(String[] args) {
        DechetTest test = new DechetTest();

        System.out.println("=== Test 1 : Création Déchet ===");
        test.testCreationDechet();

        System.out.println("\n=== Test 2 : Modification Déchet ===");
        test.testModificationDechet();

        System.out.println("\n=== Test 3 : Création avec poids négatif ===");
        test.testPoidsNegatifCreation();

        System.out.println("\n=== Test 4 : Création avec type null ===");
        test.testTypeNullCreation();

        System.out.println("\n=== Test 5 : Modification poids négatif après création ===");
        test.testPoidsNegatifSet();

        System.out.println("\n=== Test 6 : Modification type null après création ===");
        test.testTypeNullSet();
    }
}
