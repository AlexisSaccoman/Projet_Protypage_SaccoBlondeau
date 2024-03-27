package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

public class AccueilEtudiantController {

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

    public void initData(String info){
        System.out.println("Personne connectée --> " + info);
    }

}

