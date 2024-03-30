package controllers;

import controllers.divers.DB;
import controllers.divers.Personne;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AccueilEtudiantController implements Initializable {

    @FXML
    private ChoiceBox<String> selectionGroupe;

    @FXML
    private ChoiceBox<String> selectionGroupe1;

    @FXML
    private ChoiceBox<?> selectionGroupe2;

    @FXML
    private ChoiceBox<?> selectionGroupe3;

    @FXML
    private ChoiceBox<?> selectionGroupe31;

    @FXML
    private ChoiceBox<?> selectionGroupe4;

    DB dbController = new DB();

    public void initData(){
        ArrayList<String> salles = readFile("src\\main\\java\\db\\salles.txt");
        for (String salle : salles) {
            selectionGroupe.getItems().add(salle);
        }
        selectionGroupe.setValue("Salle");

        ArrayList<String> groupes = readFile("src\\main\\java\\db\\groupes.txt");
        for(String groupe : groupes){
            selectionGroupe1.getItems().add(groupe);
        }
        selectionGroupe1.setValue("Groupe");
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
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initData();
    }
}

