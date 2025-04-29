package test;
import dao.MenageDAO;
import model.Menage;
import java.util.ArrayList;

public class TestSauvegardeMenage {
    public static void main(String[] args) {
        // Créer un Menage fictif
        Menage menage = new Menage(
            "Famille Athi",        // nom
            "12 rue Konoha",          // adresse
            "naruto@gmail.com",       // adresseMail
            "hinata123",         // motDePasse
            2808,                    // codeAcces
            new ArrayList<>()        // historique vide pour le moment
        );

        // Créer le DAO
        MenageDAO dao = new MenageDAO();
        
        // Appeler la méthode pour insérer en base
        dao.enregistrerMenage(menage);
    }
}
