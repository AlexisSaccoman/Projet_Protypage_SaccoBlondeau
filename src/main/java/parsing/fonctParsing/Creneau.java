package parsing.fonctParsing;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Creneau {

    Date heureDebut;
    Date heureFin;
    String salle;
    String description;

    public Creneau(Date heureDebut, Date heureFin, String salle, String description) {
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
        this.salle = salle;
        this.description = description;
    }

    public Date getHeureDebut() {
        return heureDebut;
    }

    public void setHeureDebut(Date heureDebut) {
        this.heureDebut = heureDebut;
    }

    public Date getHeureFin() {
        return heureFin;
    }

    public void setHeureFin(Date heureFin) {
        this.heureFin = heureFin;
    }

    public String getSalle() {
        return salle;
    }

    public void setSalle(String salle) {
        this.salle = salle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public VBox getVbox() {
        VBox vbox = new VBox();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Label heureDebutLabel = new Label(sdf.format(heureDebut));
        Label heureFinLabel = new Label(sdf.format(heureFin));
        Label salleLabel = new Label(salle);
        Label descriptionLabel = new Label(description);
        vbox.setStyle("-fx-background-color: lightblue;");
        vbox.getChildren().addAll(heureDebutLabel, heureFinLabel, salleLabel, descriptionLabel);
        return vbox;
    }
}
