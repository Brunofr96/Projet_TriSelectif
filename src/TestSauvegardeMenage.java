import dao.MenageDAO;
import model.Menage;
import java.util.ArrayList;

public class TestSauvegardeMenage {
    public static void main(String[] args) {
        // Créer un Menage fictif
        Menage menage = new Menage(
            1,                      // id
            "Famille Dupont",        // nom
            "12 rue Verte",          // adresse
            "dupont@mail.com",       // adresseMail
            "motdepasse123",         // motDePasse
            1234,                    // codeAcces
            50,                      // pointsFidelite
            new ArrayList<>()        // historique vide pour le moment
        );

        // Créer le DAO
        MenageDAO dao = new MenageDAO();
        
        // Appeler la méthode pour insérer en base
        dao.enregistrerMenage(menage);
    }
}
