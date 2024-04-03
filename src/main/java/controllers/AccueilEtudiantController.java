package controllers;

import controllers.divers.Personne;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import controllers.divers.DB;
import controllers.divers.Personne;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
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
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class AccueilEtudiantController implements Initializable {

    private int weekFromNow = 0;

    private String modeAffichage = "week"; // soit "week" soit "day" soit "month"

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

    @FXML
    private Label labelLundi;

    @FXML
    private Label labelMardi;

    @FXML
    private Label labelMercredi;

    @FXML
    private Label labelJeudi;

    @FXML
    private Label labelVendredi;

    @FXML
    private Label labelSamedi;

    @FXML
    private Label labelDimanche;

    @FXML
    private Label edt_affichage_date;

    @FXML
    private DatePicker ajouterEvenement_date;

    @FXML
    private ChoiceBox<String> ajouterEvenement_heureDebut;

    @FXML
    private ChoiceBox<String> ajouterEvenement_heureFin;

    @FXML
    private ChoiceBox<String> ajouterEvenement_salle;

    @FXML
    private TextField ajouterEvenement_intitule;

    private CreneauController creneauController = new CreneauController();

    private CreneauController creneauControllerFiltered = new CreneauController();

    private ArrayList<Creneau> allCours = new ArrayList<Creneau>();


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
            ajouterEvenement_salle.getItems().add(salle);
        }
        selectionGroupe.setValue("Salle");
        ajouterEvenement_salle.setValue("Salle");

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

    public void updateDateAffichage() {
        if (this.modeAffichage.equals("week")) {
            this.edt_affichage_date.setText(labelLundi.getText().split("/")[0] + "-" + labelLundi.getText().split("/")[1] + "-" + labelLundi.getText().split("/")[2]);
        }
    }

    public void updateDateLabel() {
        ArrayList<Label> dateLabels = new ArrayList<>(Arrays.asList(labelLundi, labelMardi, labelMercredi, labelJeudi, labelVendredi, labelSamedi, labelDimanche));
        int dayIncrement = 0;
        for (Label dateLabel : dateLabels) {
            LocalDate currentMonday = LocalDate.now().with(DayOfWeek.MONDAY).plusWeeks(weekFromNow);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            dateLabel.setText(currentMonday.plusDays(dayIncrement).format(formatter));
            dayIncrement++;
        }
        updateDateAffichage();
    }

    @FXML
    public void nextWeek() {
        weekFromNow++;
        drawnEdtOnGrid(creneauController);
        updateDateLabel();
    }

    @FXML
    public void previousWeek() {
        weekFromNow--;
        drawnEdtOnGrid(creneauController);
        updateDateLabel();
    }

    public void filerBy() {
        // TODO MAIS TROP DUR
//        final int[] currentFiltreAppliques = {0};
//        final int[] previousFiltreAppliques = {0};
//        selectionGroupe.setOnAction(event -> {
//            currentFiltreAppliques[0] += 1;
//            if (currentFiltreAppliques[0] == 0) {
//                creneauController.setCours(allCours);
//                creneauControllerFiltered.setCours(creneauController.getCoursBySalle(selectionGroupe.getValue()));
//                drawnEdtOnGrid(creneauControllerFiltered);
//                currentFiltreAppliques[0] = 1;
//            } else {
//                if(currentFiltreAppliques[0] == previousFiltreAppliques[0]) {
//                    creneauControllerFiltered.setCours(creneauController.getCoursBySalle(selectionGroupe.getValue()));
//                    drawnEdtOnGrid(creneauControllerFiltered);
//                } else {
//                   creneauControllerFiltered.setCours(creneauControllerFiltered.getCoursBySalle(selectionGroupe.getValue()));
//                    drawnEdtOnGrid(creneauControllerFiltered);
//                }
//            }
//            previousFiltreAppliques[0] = currentFiltreAppliques[0];;
//        });
        // Salle
        selectionGroupe.setOnAction(event -> {
            creneauController.setCours(allCours);
            creneauController.setCours(creneauController.getCoursBySalle(selectionGroupe.getValue()));
            drawnEdtOnGrid(creneauController);
        });
        // Type de cours
        selectionGroupe1.setOnAction(event -> {
            creneauController.setCours(allCours);
            creneauController.setCours(creneauController.getCoursByAnything(selectionGroupe1.getValue()));
            drawnEdtOnGrid(creneauController);
        });
        // Matieres
        selectionGroupe2.setOnAction(event -> {
            creneauController.setCours(allCours);
            creneauController.setCours(creneauController.getCoursByAnything(selectionGroupe2.getValue()));
            drawnEdtOnGrid(creneauController);
        });
        // Groupe
        selectionGroupe3.setOnAction(event -> {
            creneauController.setCours(allCours);
            creneauController.setCours(creneauController.getCoursByAnything(selectionGroupe3.getValue()));
            drawnEdtOnGrid(creneauController);
        });
    }

    public void resetFilters() {
        selectionGroupe.setValue("Salle");
        selectionGroupe1.setValue("Type de cours");
        selectionGroupe2.setValue("Matieres");
        selectionGroupe3.setValue("Groupe");
        creneauController.setCours(allCours);
        drawnEdtOnGrid(creneauController);
    }

    public void drawnEdtOnGrid(CreneauController creneauController) {
        grid_edt.getChildren().clear(); // On efface tout ce qu'il y avait dans la grid

        // Récupère le Lundi par rapport au jour actuel, et on ajoute a quel semaine on est (0 = semaine actuelle, 1 semaine suivant etc...)
        LocalDate currentMonday = LocalDate.now().with(DayOfWeek.MONDAY).plusWeeks(weekFromNow);
        ArrayList<Creneau> cours = creneauController.getCoursByDayPeriod(currentMonday, currentMonday.plusDays(5)); // On regarde 5 jours dans le futur
        creneauController.afficherEmploiDuTemps(cours);

        Map<String, Integer> heureIndexMap = new HashMap<>();
        heureIndexMap.put("02:00", 0); // Pour les évènements de toute une journée, heure début et de fin = 02:00
        heureIndexMap.put("08:30", 1);
        heureIndexMap.put("09:00", 2);
        heureIndexMap.put("09:30", 3);
        heureIndexMap.put("10:00", 4);
        heureIndexMap.put("10:30", 5);
        heureIndexMap.put("11:00", 6);
        heureIndexMap.put("11:30", 7);
        heureIndexMap.put("12:00", 8);
        heureIndexMap.put("12:30", 9);
        heureIndexMap.put("13:00", 10);
        heureIndexMap.put("13:30", 11);
        heureIndexMap.put("14:00", 12);
        heureIndexMap.put("14:30", 13);
        heureIndexMap.put("15:00", 14);
        heureIndexMap.put("15:30", 15);
        heureIndexMap.put("16:00", 16);
        heureIndexMap.put("16:30", 17);
        heureIndexMap.put("17:00", 18);
        heureIndexMap.put("17:30", 19);
        heureIndexMap.put("18:00", 20);
        heureIndexMap.put("18:30", 21);
        heureIndexMap.put("19:00", 22);

        for (Creneau c : cours) {
            Integer indexHeureDebut = heureIndexMap.get(c.getHeureDebut().toString());
            Integer indexHeureFin = heureIndexMap.get(c.getHeureFin().toString());
            Integer indexJour = c.getJour().getDayOfWeek().getValue();
            System.out.println(c.getJour());
            if (indexHeureDebut == null || indexHeureFin == null) {
                continue;
            } else if (indexHeureDebut == 0) { // Si évènement dure toute la journée, index de fin = fin de la grille
                indexHeureFin = 24;
            }

            grid_edt.add(c.getVbox(), indexJour - 1, indexHeureDebut, 1, (indexHeureFin - indexHeureDebut));
        }
    }

    public void ifDisplayMode() { // permet de changer les affichages (day/week/month/)
        // TODO
    }

    public void ajouterEvenement() {
        if (ajouterEvenement_date.getValue() == null || ajouterEvenement_heureDebut.getValue() == null || ajouterEvenement_heureFin.getValue() == null || ajouterEvenement_salle.getValue() == null || ajouterEvenement_intitule.getText().equals("")) {
            System.out.println("Veuillez remplir tous les champs");
            return;
        }

        LocalTime heureDebut = LocalTime.parse(ajouterEvenement_heureDebut.getValue().toString());
        LocalTime heureFin = LocalTime.parse(ajouterEvenement_heureFin.getValue().toString());

        if (heureDebut.isAfter(heureFin)) {
            System.out.println("L'heure de début doit être avant l'heure de fin");
            return;
        }
        if (creneauController.isCreneauUsed(heureDebut, heureFin, ajouterEvenement_date.getValue())) {
            System.out.println("Créneau déjà utilisé");
        } else {
            creneauController.addCreneau(new Creneau(heureDebut, heureFin, ajouterEvenement_date.getValue(), ajouterEvenement_salle.getValue(), ajouterEvenement_intitule.getText()));
            drawnEdtOnGrid(creneauController);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ICSParsing icsParsing = new ICSParsing();
        Calendar calendar = icsParsing.parse("src/main/resources/sacco_1.ics");
        allCours = icsParsing.getAllCours(calendar);
        creneauController.setCours(allCours);


        setButtonData();
        drawnEdtOnGrid(creneauController);
        updateDateLabel();
        filerBy();
    }
}
