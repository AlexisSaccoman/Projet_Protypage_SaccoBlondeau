package controllers;

import controllers.divers.Personne;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;

import java.io.*;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;

public class AccueilEtudiantController {

    @FXML
    private GridPane grid_edt;

    @FXML
    private Label nomPrenom;

    @FXML
    private ChoiceBox<?> selectionGroupe;

    @FXML
    private ChoiceBox<?> selectionGroupe1;

    @FXML
    private ChoiceBox<?> selectionGroupe2;

    @FXML
    private ChoiceBox<?> selectionGroupe3;

    @FXML
    private ChoiceBox<?> selectionGroupe31;

    @FXML
    private ChoiceBox<?> selectionGroupe4;

    @FXML
    private Label today_date;

    @FXML
    private ToggleButton toggle_light;

    public void initData(String username, LocalDate date) {
        // Utiliser les données passées pour initialiser votre interface utilisateur
        nomPrenom.setText(username);
        today_date.setText(date.toString());
    }

    @FXML
    void toggle_light_action(ActionEvent event) { // Changer le thème de l'interface utilisateur en live (+ appel de la fonction qui le change dans "users.txt")
        Scene scene = toggle_light.getScene();

        if (toggle_light.isSelected()) {
            scene.getStylesheets().clear();
            scene.getStylesheets().add(getClass().getResource("/components/accueilEtudiant/AccueilEtudiantDarkCSS.css").toExternalForm());
            changeThemeUsers("AccueilEtudiantDarkCSS");
        } else {
            scene.getStylesheets().clear();
            scene.getStylesheets().add(getClass().getResource("/components/accueilEtudiant/AccueilEtudiantCSS.css").toExternalForm());
            changeThemeUsers("AccueilEtudiantCSS");
        }
    }


    public void changeThemeUsers(String theme) {
        // Chemin vers le fichier
        String filePath = "C:\\Users\\Alexis\\Documents\\files\\Cours\\master\\Semestre 8\\UE Prototypage\\Projet_Protypage_SaccoBlondeau\\src\\main\\java\\db\\users.txt";

        // Créer une liste pour stocker les lignes du fichier
        ArrayList<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            // Lire chaque ligne du fichier et l'ajouter à la liste
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return; // Quitter la méthode en cas d'erreur de lecture du fichier
        }

        // Modifier la ligne correspondant à l'utilisateur connecté
        for (int i = 0; i < lines.size(); i++) {
            String[] columns = lines.get(i).split(";");
            if (columns.length >= 1 && columns[1].equals(nomPrenom.getText())) {
                columns[3] = theme;
                // Reconstruire la ligne avec les modifications
                lines.set(i, String.join(";", columns));
                break; // Sortir de la boucle une fois la ligne modifiée
            }
        }

        // Réécrire tout le contenu du fichier avec les modifications
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
