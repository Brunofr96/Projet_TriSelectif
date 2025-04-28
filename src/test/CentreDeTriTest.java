package test;

import model.*;

import java.util.*;

public class CentreDeTriTest {

    public static void runTests() {

        CentreDeTri centre = new CentreDeTri("Centre Nord", "Zone 1");

        BacIntelligent bac1 = new BacIntelligent(1, TypePoubelle.BLEUE, 5.0, "Avenue 1");
        BacIntelligent bac2 = new BacIntelligent(2, TypePoubelle.VERTE, 5.0, "Avenue 2");

        centre.ajouterPoubelle(bac1);
        centre.ajouterPoubelle(bac2);

        // Ajout de quelques déchets simulés
        bac1.ajouterDechet(List.of(
            new Dechet(TypeDechet.PAPIER, 1.0),
            new Dechet(TypeDechet.PAPIER, 0.5)
        ), null);

        bac2.ajouterDechet(List.of(
            new Dechet(TypeDechet.VERRE, 1.0)
        ), null);

        // Générer statistiques
        System.out.println("\n Statistiques par type de bac :");
        Map<String, Double> stats = centre.genererStatistiques();
        for (String type : stats.keySet()) {
            System.out.println(type + " -> " + stats.get(type) + " kg");
        }
    }
}

