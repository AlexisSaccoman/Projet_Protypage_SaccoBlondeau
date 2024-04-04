package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class MenuController{

    private String cssPath = "/component/";
    private String username = "";
    private LocalDate date = LocalDate.now();
    private String role = "";

    @FXML private AnchorPane bg_menu;

    @FXML
    void Deconnexion(ActionEvent event) {
        switch2Interface("/components/login/LoginInterface.fxml");
    }

    @FXML
    void toEdtFormation(ActionEvent event) {
        switch2Interface("/components/edtFormation/EdtFormationInterface.fxml");
    }

    @FXML
    void toEdtPerso(ActionEvent event) {
        goToAccueil();
    }

    @FXML
    void toEdtSalle(ActionEvent event) {
        switch2Interface("/components/edtFormation/EdtFormationInterface.fxml");
    }

    public void switch2Interface(String path) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) bg_menu.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private void goToAccueil() {
        String path = "";
        if(role.equals("etudiant")) {
            path = "/components/accueilEtudiant/AccueilEtudiantInterface.fxml";
        } else if(role.equals("enseignant")) {
            path = "/components/accueilEnseignant/AccueilEnseignantInterface.fxml";
        }
        try {
            // Chargement du fichier FXML de la nouvelle interface
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            Parent root = loader.load();

            // Obtention du contrôleur après le chargement du FXML
            Object controller = loader.getController();

            // Si le contrôleur est une instance de AccueilEtudiantController ou AccueilEnseignantController,
            // appelez la méthode initData() en fonction du type de personne (étudiant ou enseignant)
            if (controller instanceof AccueilEtudiantController) {
                ((AccueilEtudiantController) controller).setUsernameAndDate(username, date);
            } /*else if (controller instanceof AccueilEnseignantController) {
                ((AccueilEnseignantController) controller).initData(personne);
            }*/

            // Création d'une nouvelle scène avec la nouvelle interface
            Scene scene = new Scene(root);

            // Ajout de la feuille de style à la scène
            scene.getStylesheets().add(getClass().getResource(cssPath).toExternalForm());

            // Récupération de la scène actuelle
            Stage stage = (Stage) bg_menu.getScene().getWindow();

            // Mise à jour de la scène pour afficher la nouvelle interface
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void initData(String cssPath, String username, LocalDate date, String role){
        this.cssPath = cssPath;
        this.username = username;
        this.date = date;
        this.role = role;
    }

}
