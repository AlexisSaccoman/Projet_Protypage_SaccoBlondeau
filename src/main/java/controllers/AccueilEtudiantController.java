package controllers;

import controllers.divers.Personne;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import controllers.divers.DB;
import controllers.divers.Personne;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import net.fortuna.ical4j.model.Calendar;
import parsing.ICSParsing;
import parsing.fonctParsing.Creneau;
import parsing.fonctParsing.CreneauController;

import java.io.*;
import java.lang.reflect.Array;
import java.sql.SQLOutput;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

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
    private ChoiceBox<String> selectionGroupe31;

    @FXML
    private ChoiceBox<String> selectionGroupe4;

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
    private ChoiceBox<String> ajouterEvenement_couleur;

    @FXML
    private ChoiceBox<String> ajouterEvenement_heureDebut;

    @FXML
    private ChoiceBox<String> ajouterEvenement_heureFin;

    @FXML
    private ChoiceBox<String> ajouterEvenement_salle;

    @FXML
    private TextField ajouterEvenement_intitule;

    @FXML
    private Button resetFiltersButton;

    @FXML
    private Label titreDimanche;

    @FXML
    private Label titreJeudi;

    @FXML
    private Label titreLundi;

    @FXML
    private Label titreMardi;

    @FXML
    private Label titreMercredi;

    @FXML
    private Label titreSamedi;

    @FXML
    private Label titreVendredi;

    @FXML
    private VBox horairesContainer;

    @FXML
    private ChoiceBox<String> filtreFormation;

    private CreneauController creneauController = new CreneauController();

    private ArrayList<Creneau> allCours = new ArrayList<Creneau>();

    private String icsPath;

    private String interfaceDisplayed; // perso / salle / formation


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
        if(selectionGroupe != null && ajouterEvenement_salle != null) {
            ArrayList<String> salles = readFile("src\\main\\java\\db\\salles.txt");
            for (String salle : salles) {

                selectionGroupe.getItems().add(salle);
                ajouterEvenement_salle.getItems().add(salle);
            }
            selectionGroupe.setValue("Salle");
            ajouterEvenement_salle.setValue("Salle");
        }

        if(selectionGroupe1 != null){
            ArrayList<String> typeDeCours = readFile("src\\main\\java\\db\\typeDeCours.txt");
            for (String type : typeDeCours) {
                selectionGroupe1.getItems().add(type);
            }
            selectionGroupe1.setValue("Type de cours");
        }

        if(selectionGroupe2 != null){
            ArrayList<String> matieres = readFile("src\\main\\java\\db\\matieres.txt");
            for (String matiere : matieres) {
                selectionGroupe2.getItems().add(matiere);
            }
            selectionGroupe2.setValue("Matieres");
        }

        if(selectionGroupe3 != null){
            ArrayList<String> groupes = readFile("src\\main\\java\\db\\groupes.txt");
            for (String groupe : groupes) {
                selectionGroupe3.getItems().add(groupe);
            }
            selectionGroupe3.setValue("Groupe");
        }

        // ajout des affichages sur le bouton de sélection des affichages (par défaut : week)
        if(selectionGroupe4 != null){
            selectionGroupe4.getItems().add("Week");
            selectionGroupe4.getItems().add("Day");
            selectionGroupe4.getItems().add("Month");
            selectionGroupe4.setValue("Week");
        }
    }

    public void updateDateLabel() {
        ArrayList<Label> dateLabels = new ArrayList<>(Arrays.asList(labelLundi, labelMardi, labelMercredi, labelJeudi, labelVendredi, labelSamedi, labelDimanche));
        int dayIncrement = 0;
        if (this.modeAffichage.equals("week")) {
            for (Label dateLabel : dateLabels) {
                LocalDate currentMonday = LocalDate.now().with(DayOfWeek.MONDAY).plusWeeks(weekFromNow);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                dateLabel.setText(currentMonday.plusDays(dayIncrement).format(formatter));
                dayIncrement++;
            }
        } else if (this.modeAffichage.equals("day")) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE", Locale.FRENCH); // Pour traduire le jour d'aujourd'hui en Français
            titreJeudi.setText(formatter.format(LocalDate.now().plusDays(weekFromNow)).toUpperCase());

            formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // Afficher jour en dd/MM/yyyy
            labelJeudi.setText(formatter.format(LocalDate.now().plusDays(weekFromNow))); // Rajoute un jour et non une semaine
        }
    }

    @FXML
    public void nextWeek() {
        weekFromNow++;
        if (modeAffichage.equals("month")) {
            drawGridOnEdtMonth(creneauController);
        } else {
            drawnEdtOnGrid(creneauController);
        }
        updateDateLabel();
    }

    @FXML
    public void previousWeek() {
        weekFromNow--;
        if (modeAffichage.equals("month")) {
            drawGridOnEdtMonth(creneauController);
        } else {
            drawnEdtOnGrid(creneauController);
        }
        updateDateLabel();
    }

    public void filerBy() {
        // Salle
        if (selectionGroupe != null) {
            selectionGroupe.setOnAction(event -> {
                creneauController.setCours(allCours);
                creneauController.setCours(creneauController.getCoursBySalle(selectionGroupe.getValue()));
                if (modeAffichage.equals("month")) {
                    drawGridOnEdtMonth(creneauController);
                } else {
                    drawnEdtOnGrid(creneauController);
                }
            });
        }

        // Type de cours
        if (selectionGroupe1 != null) {
            selectionGroupe1.setOnAction(event -> {
                creneauController.setCours(allCours);
                creneauController.setCours(creneauController.getCoursByAnything(selectionGroupe1.getValue()));
                if (modeAffichage.equals("month")) {
                    drawGridOnEdtMonth(creneauController);
                } else {
                    drawnEdtOnGrid(creneauController);
                }
            });
        }

        // Matieres
        if (selectionGroupe2 != null) {
            selectionGroupe2.setOnAction(event -> {
                creneauController.setCours(allCours);
                creneauController.setCours(creneauController.getCoursByAnything(selectionGroupe2.getValue()));
                if (modeAffichage.equals("month")) {
                    drawGridOnEdtMonth(creneauController);
                } else {
                    drawnEdtOnGrid(creneauController);
                }
            });
        }

        // Groupe
        if (selectionGroupe3 != null) {
            selectionGroupe3.setOnAction(event -> {
                creneauController.setCours(allCours);
                creneauController.setCours(creneauController.getCoursByAnything(selectionGroupe3.getValue()));
                if (modeAffichage.equals("month")) {
                    drawGridOnEdtMonth(creneauController);
                } else {
                    drawnEdtOnGrid(creneauController);
                }
            });
        }

        //mode d'affichage
        if (selectionGroupe4 != null) {
            selectionGroupe4.setOnAction(event -> {
                changeDisplayMode();
            });
        }
    }

    public void resetFilters() {
        if(selectionGroupe != null){
            selectionGroupe.setValue("Salle");
        }

        if (selectionGroupe1 != null) {
            selectionGroupe1.setValue("Type de cours");
        }

        if (selectionGroupe2 != null) {
            selectionGroupe2.setValue("Matieres");
        }

        if (selectionGroupe3 != null) {
            selectionGroupe3.setValue("Groupe");
        }

        creneauController.setCours(allCours);

        if (modeAffichage.equals("month")) {
            drawGridOnEdtMonth(creneauController);
        } else {
            drawnEdtOnGrid(creneauController);
        }
    }

    public void drawnEdtOnGrid(CreneauController creneauController) {
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

        grid_edt.getChildren().clear(); // On efface tout ce qu'il y avait dans la grid
        ArrayList<Creneau> cours = new ArrayList<>();

        // Récupère le Lundi par rapport au jour actuel, et on ajoute a quel semaine on est (0 = semaine actuelle, 1 semaine suivant etc...)
        if (modeAffichage.equals("week")) {
            LocalDate currentMonday = LocalDate.now().with(DayOfWeek.MONDAY).plusWeeks(weekFromNow);
            cours = creneauController.getCoursByDayPeriod(currentMonday, currentMonday.plusDays(5)); // On regarde 5 jours dans le futur
        } else if (modeAffichage.equals("day")) {
            LocalDate currentDay = LocalDate.now().plusDays(weekFromNow);
            cours = creneauController.getCoursByDay(currentDay); // On regarde 5 jours dans le futur
        }

        creneauController.afficherEmploiDuTemps(cours); // TODO remove

        for (Creneau c : cours) {
            // Récupère les index (verticaux) dans la hmap en fonction des horaires + index du jour (horizontal)
            Integer indexHeureDebut = heureIndexMap.get(c.getHeureDebut().toString());
            Integer indexHeureFin = heureIndexMap.get(c.getHeureFin().toString());
            Integer indexJour = c.getJour().getDayOfWeek().getValue();

            if (indexHeureDebut == null || indexHeureFin == null) {
                continue;
            } else if (indexHeureDebut == 0) { // Si évènement dure toute la journée, index de fin = fin de la grille
                indexHeureFin = 24;
            }

            if (modeAffichage.equals("week")) {
                grid_edt.add(c.getVbox(), indexJour - 1, indexHeureDebut, 1, (indexHeureFin - indexHeureDebut));
            } else if (modeAffichage.equals("day")) {
                grid_edt.add(c.getVbox(), 0, indexHeureDebut, 7, (indexHeureFin - indexHeureDebut));
            }
        }
    }

    public void drawGridOnEdtMonth(CreneauController creneauController) {
        grid_edt.getChildren().clear(); // On efface tout ce qu'il y avait dans la grid

        LocalDate firstDayOfMonth = LocalDate.now().withDayOfMonth(1).plusMonths(weekFromNow);
        YearMonth currentYearMonth = YearMonth.from(LocalDate.now().plusMonths(weekFromNow));
        int daysInCurrentMonth = currentYearMonth.lengthOfMonth(); // Récupère combien de jours dans ce mois-ci

        // Initialiser la HashMap pour stocker le nombre de cours par jour
        HashMap<LocalDate, Integer> coursParJour = new HashMap<>();

        // Récupérer tous les cours du mois
        ArrayList<Creneau> cours = creneauController.getCoursByDayPeriod(firstDayOfMonth, firstDayOfMonth.plusDays(daysInCurrentMonth - 1));

        // Compter le nombre de cours pour chaque jour et stocker dans la HashMap
        for (Creneau c : cours) {
            LocalDate date = c.getJour();
            coursParJour.put(date, coursParJour.getOrDefault(date, 0) + 1);
        }

        Map<Integer, Integer> departHBox = new HashMap<>();
        departHBox.put(0, 0);
        departHBox.put(1, 4);
        departHBox.put(2, 8);
        departHBox.put(3, 12);
        departHBox.put(4, 16);
        departHBox.put(5, 20);

        // Afficher le nombre de cours pour chaque jour
        for (Map.Entry<LocalDate, Integer> entry : coursParJour.entrySet()) {
            LocalDate date = entry.getKey();
            int numberOfCourses = entry.getValue();

            // TODO Changer le fond pour que ça soit dynamique
            VBox vbox = new VBox();
            Label label = new Label(numberOfCourses + " cours");
            Label dateLabel = new Label(date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            vbox.setBackground(new javafx.scene.layout.Background(new javafx.scene.layout.BackgroundFill(Color.LIGHTBLUE, null, null)));
            vbox.getChildren().addAll(label, dateLabel);

            int departHBoxValue = departHBox.getOrDefault(date.getDayOfMonth()/7, 0);
            grid_edt.add(vbox, date.getDayOfWeek().getValue() - 1, departHBoxValue, 1, 4);
        }
    }

    public void hideDaysExceptJeudi() {
        titreLundi.getStyleClass().add("hidden");
        labelLundi.getStyleClass().add("hidden");
        titreMardi.getStyleClass().add("hidden");
        labelMardi.getStyleClass().add("hidden");
        titreMercredi.getStyleClass().add("hidden");
        labelMercredi.getStyleClass().add("hidden");
        titreVendredi.getStyleClass().add("hidden");
        labelVendredi.getStyleClass().add("hidden");
        titreSamedi.getStyleClass().add("hidden");
        labelSamedi.getStyleClass().add("hidden");
        titreDimanche.getStyleClass().add("hidden");
        labelDimanche.getStyleClass().add("hidden");
    }

    public void hideDates() {
        labelLundi.getStyleClass().add("hidden");
        labelMardi.getStyleClass().add("hidden");
        labelMercredi.getStyleClass().add("hidden");
        labelJeudi.getStyleClass().add("hidden");
        labelVendredi.getStyleClass().add("hidden");
        labelSamedi.getStyleClass().add("hidden");
        labelDimanche.getStyleClass().add("hidden");

    }

    public void displayDates() {
        labelLundi.getStyleClass().remove("hidden");
        labelMardi.getStyleClass().remove("hidden");
        labelMercredi.getStyleClass().remove("hidden");
        labelJeudi.getStyleClass().remove("hidden");
        labelVendredi.getStyleClass().remove("hidden");
        labelSamedi.getStyleClass().remove("hidden");
        labelDimanche.getStyleClass().remove("hidden");
    }

    public void displayDays() {
        titreLundi.getStyleClass().remove("hidden");
        labelLundi.getStyleClass().remove("hidden");
        titreMardi.getStyleClass().remove("hidden");
        labelMardi.getStyleClass().remove("hidden");
        titreMercredi.getStyleClass().remove("hidden");
        labelMercredi.getStyleClass().remove("hidden");
        titreJeudi.getStyleClass().remove("hidden");
        labelJeudi.getStyleClass().remove("hidden");
        titreVendredi.getStyleClass().remove("hidden");
        labelVendredi.getStyleClass().remove("hidden");
        titreSamedi.getStyleClass().remove("hidden");
        labelSamedi.getStyleClass().remove("hidden");
        titreDimanche.getStyleClass().remove("hidden");
        labelDimanche.getStyleClass().remove("hidden");
    }

    public void displayHoraire() {
        horairesContainer.getStyleClass().remove("hidden");
    }

    public void hideHoraire() {
        horairesContainer.getStyleClass().add("hidden");
    }

    public void ifDisplayMode() { // permet de changer les affichages (day/week/month/)
        System.out.println("Mode d'affichage : " + selectionGroupe4.getValue());
        if (selectionGroupe4.getValue() == "Week") {
            this.modeAffichage = "week";
            displayDays();
            displayDates();
            displayHoraire();
            titreJeudi.setText("JEUDI");
            updateDateLabel();

            drawnEdtOnGrid(creneauController);

        } else if (selectionGroupe4.getValue() == "Day") {
            this.modeAffichage = "day";
            displayDates();
            displayHoraire();
            hideDaysExceptJeudi();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE", Locale.FRENCH); // Pour traduire le jour d'aujourd'hui en Français
            titreJeudi.setText(formatter.format(LocalDate.now()).toUpperCase());

            formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // Afficher jour en dd/MM/yyyy
            labelJeudi.setText(formatter.format(LocalDate.now().plusDays(weekFromNow))); // Rajoute un jour et non une semaine

            drawnEdtOnGrid(creneauController);

        } else if (selectionGroupe4.getValue() == "Month") {
            this.modeAffichage = "month";
            titreJeudi.setText("JEUDI");
            displayDays();
            hideDates();
            hideHoraire();
            drawGridOnEdtMonth(creneauController);
        }
    }

    public void changeDisplayMode() {
        ifDisplayMode();
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
            creneauController.addCreneau(new Creneau(heureDebut, heureFin, ajouterEvenement_date.getValue(), ajouterEvenement_salle.getValue(), ajouterEvenement_intitule.getText(), ajouterEvenement_couleur.getValue()));
            drawnEdtOnGrid(creneauController);
        }
    }

    // gestionnaire des raccourcis clavier
    public void handleKey(KeyEvent event) {
        System.out.println("Touche pressée : " + event.getCode());
        switch (event.getCode()) {
            case RIGHT:
                nextWeek();
                break;
            case LEFT:
                previousWeek();
                break;
            case R:
                resetFilters();
                break;
            case W:
                selectionGroupe4.setValue("Week");
                changeDisplayMode();
                break;
            case D:
                selectionGroupe4.setValue("Day");
                changeDisplayMode();
                break;
            case M:
                selectionGroupe4.setValue("Month");
                changeDisplayMode();
                break;
            default:
                break;
        }
    }

    public void setUntraversable(Scene scene) {
        for (Node node : scene.getRoot().getChildrenUnmodifiable()) {
            if (node instanceof Button) {
                Button button = (Button) node;
                button.setFocusTraversable(false);
            }
        }
    }

    @FXML
    public void openMenu(ActionEvent event) { // ouvrir le menu

        DB db = new DB();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/components/menu/MenuInterface.fxml"));
            Parent root = loader.load();
            MenuController controller = loader.getController();
            controller.initData("/components/accueilEtudiant/" + db.getCssPath("etudiant", nomPrenom.getText()) + ".css", nomPrenom.getText(), LocalDate.now(), "etudiant");
            Scene scene = new Scene(root);
            Stage stage = (Stage) grid_edt.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public String parserQui(String username, String interf){

        String path = "";

        if(interf.equals("perso")){
            path = username;
        }else if (interf.equals("salle")){
            path = "Stat 7 = Info - C 128";
        }else if (interf.equals("formation")){
            path = "formation";
        }

        icsPath = "src/main/java/db/ics/"+path+".ics";
        System.out.println(icsPath);

        return path;
    }

    public void initData(String username, LocalDate date, String interf) {
        setUsernameAndDate(username, date);
        interfaceDisplayed = interf;
        initParsing(parserQui(username, interf));
    }

    public void initParsing(String pathToParse){
        if(pathToParse.equals("formation")){
            return;
        }
        ICSParsing icsParsing = new ICSParsing();
        Calendar calendar = icsParsing.parse("src/main/java/db/ics/"+pathToParse+".ics");
        allCours = icsParsing.getAllCours(calendar);
        creneauController.setCours(allCours);
        drawnEdtOnGrid(creneauController);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        setButtonData();
        updateDateLabel();
        filerBy();

        Platform.runLater(() -> {
            // Récupérer la scène et ajouter le gestionnaire d'événements clavier
            Scene scene = grid_edt.getScene();
            setUntraversable(scene);
            scene.setOnKeyPressed(this::handleKey);
        });
    }
}
