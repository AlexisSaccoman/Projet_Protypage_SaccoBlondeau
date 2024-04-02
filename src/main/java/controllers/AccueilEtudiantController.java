package controllers;

import controllers.divers.Personne;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import controllers.divers.DB;
import controllers.divers.Personne;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import net.fortuna.ical4j.model.Calendar;
import parsing.ICSParsing;
import parsing.fonctParsing.Creneau;
import parsing.fonctParsing.CreneauController;

import java.io.*;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class AccueilEtudiantController implements Initializable {

    DB dbController = new DB();

    @FXML
    private GridPane grid_edt;

    @FXML
    private Label nomPrenom;

    @FXML
    private ChoiceBox<String> selectionGroupe;

    @FXML
    private ChoiceBox<String> selectionGroupe1;

    @FXML
    private ChoiceBox<String> selectionGroupe2;

    @FXML
    private ChoiceBox<String> selectionGroupe3;

    @FXML
    private ChoiceBox<?> selectionGroupe31;

    @FXML
    private ChoiceBox<?> selectionGroupe4;
    @FXML
    private Label today_date;

    @FXML
    private ToggleButton toggle_light;

    public void setUsernameAndDate(String username, LocalDate date) {
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
        String filePath = "src\\main\\java\\db\\users.txt";

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

    public ArrayList<String> readFile(String filePath) {
        ArrayList<String> lines = new ArrayList<>();
        try {
            File file = new File(filePath);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            while (line != null) {
                lines.add(line);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    public void setButtonData() {
        ArrayList<String> salles = readFile("src\\main\\java\\db\\salles.txt");
        for (String salle : salles) {
            selectionGroupe.getItems().add(salle);
        }
        selectionGroupe.setValue("Salle");

        ArrayList<String> typeDeCours = readFile("src\\main\\java\\db\\typeDeCours.txt");
        for (String type : typeDeCours) {
            selectionGroupe1.getItems().add(type);
        }
        selectionGroupe1.setValue("Type de cours");

        ArrayList<String> matieres = readFile("src\\main\\java\\db\\matieres.txt");
        for (String matiere : matieres) {
            selectionGroupe2.getItems().add(matiere);
        }
        selectionGroupe2.setValue("Matieres");

        ArrayList<String> groupes = readFile("src\\main\\java\\db\\groupes.txt");
        for (String groupe : groupes) {
            selectionGroupe3.getItems().add(groupe);
        }
        selectionGroupe3.setValue("Groupe");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setButtonData();

        ICSParsing icsParsing = new ICSParsing();
        CreneauController creneauController = new CreneauController();
        Calendar calendar = icsParsing.parse("src/main/resources/sacco_1.ics");
        creneauController.setCours(icsParsing.getAllCours(calendar));
        ArrayList<Creneau> cours = creneauController.getCours();
        cours = creneauController.getCoursByDay("2024-03-14");

        Map<String, Integer> heureIndexMap = new HashMap<>();
        heureIndexMap.put("08:30", 1);
        heureIndexMap.put("10:00", 5);
        heureIndexMap.put("11:30", 8);
        heureIndexMap.put("13:00", 11);
        heureIndexMap.put("14:30", 14);
        heureIndexMap.put("16:00", 17);
        heureIndexMap.put("17:30", 20);

        for (Creneau c : cours) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            System.out.println(c.getHeureDebut().format(formatter));
//            Integer indexHeureDebut = heureIndexMap.get(sdf.format(c.getHeureDebut()));
//            Integer indexHeureFin = heureIndexMap.get(sdf.format(c.getHeureFin()));
//            if (indexHeureDebut == null || indexHeureFin == null) {
//                continue;
//            }
//            System.out.println(indexHeureDebut + " " + indexHeureFin);
//            grid_edt.add(c.getVbox(), 0, indexHeureDebut, 1, (indexHeureFin - indexHeureDebut) - 1);
        }
    }
}
