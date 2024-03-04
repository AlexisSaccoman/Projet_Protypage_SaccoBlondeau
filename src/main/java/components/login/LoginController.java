package components.login;

import components.divers.Personne;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import parsing.ICSParsing;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class LoginController {

    @FXML
    private AnchorPane bg_login;

    @FXML
    private Pane bg_logpw;

    @FXML
    private TextField login;

    @FXML
    private TextField password;

    @FXML
    void connexion(ActionEvent event) {

        // on récupère les données des champs login et password

        Personne p = verify(login.getText(), password.getText());

        if(p != null){
            // Appelle la méthode de parsing après le lancement de l'interface utilisateur
            ICSParsing parseur = new ICSParsing();
            //afficherEmploiDuTemps(parseur.parse("parsing/data/sacco_1.ics"), p); //TODO
        }
    }

    // fonction qui vérifie si le login et le password sont corrects
    public Personne verify(String login, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader(""))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 3) {
                    String storedLogin = parts[0].trim();
                    String storedPassword = parts[1].trim();
                    String storedStatut = parts[2].trim();
                    if (login.equals(storedLogin) && password.equals(storedPassword)) {
                        return new Personne(storedStatut, storedLogin);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private void afficherEmploiDuTemps(List<Event> emploiDuTemps, Personne p) {
        try {
            // Chargement du fichier FXML de la vue suivante
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../accueilEnseignant/"));
            Parent root = loader.load();

            // EmploiDuTempsController controller = loader.getController();
            // controller.initialize(emploiDuTemps); // Passez les données de l'emploi du temps au contrôleur

            Scene scene = login.getScene();

            scene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
