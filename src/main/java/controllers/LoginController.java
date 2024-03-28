package controllers;

import controllers.divers.DB;
import controllers.divers.Personne;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import parsing.ICSParsing;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.SocketOption;
import java.sql.SQLOutput;
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

    DB db = new DB();

    @FXML
    void connexion(ActionEvent event) {

        System.out.println("Connexion");

        System.out.println("Login : " + login.getText());
        System.out.println("Password : " + password.getText());

        // on récupère les données des champs login et password
        Personne p = db.verify(login.getText(), password.getText());

        if(p != null){
            // Appelle la méthode de parsing après le lancement de l'interface utilisateur
            ICSParsing parseur = new ICSParsing();
            //afficherEmploiDuTemps(parseur.parse("parsing/data/sacco_1.ics"), p); //TODO

            System.out.println("Statut : " + p.statut);

            if(p.statut.equals("etudiant")){ // on va vers l'interface correspondate
                switchToInterface2("/components/accueilEtudiant/AccueilEtudiantInterface.fxml");
                System.out.println("Etudiant");
            }else if(p.statut.equals("enseignant")){
                switchToInterface2("/components/accueilEnseignant/AccueilEnseignantInterface.fxml");
                System.out.println("Enseignant");
            }
        }
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

    private void switchToInterface2(String fxmlPath) {
        try {
            // Chargement du fichier FXML de la nouvelle interface
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));

            Parent root = loader.load();

            // Création d'une nouvelle scène avec la nouvelle interface
            Scene scene = new Scene(root);

            // Récupération de la scène actuelle
            Stage stage = (Stage) bg_login.getScene().getWindow();

            // Mise à jour de la scène pour afficher la nouvelle interface
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
