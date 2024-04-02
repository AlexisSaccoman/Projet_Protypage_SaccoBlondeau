package parsing.fonctParsing;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

public class Creneau {

    LocalTime heureDebut;
    LocalTime heureFin;
    LocalDate jourDebut;
    LocalDate jourFin;
    String salle;
    String description;

    public Creneau(LocalDateTime jourHeureDebut, LocalDateTime jourHeureFin, String salle, String description) {
        this.heureDebut = jourHeureDebut.toLocalTime();
        this.heureFin = jourHeureFin.toLocalTime();
        this.jourDebut = jourHeureDebut.toLocalDate();
        this.jourFin = jourHeureFin.toLocalDate();
        this.salle = salle;
        this.description = description;
    }

    public LocalTime getHeureDebut() {
        return heureDebut;
    }

    public void setHeureDebut(LocalTime heureDebut) {
        this.heureDebut = heureDebut;
    }

    public LocalTime getHeureFin() {
        return heureFin;
    }

    public void setHeureFin(LocalTime heureFin) {
        this.heureFin = heureFin;
    }

    public LocalDate getJourDebut() {
        return jourDebut;
    }

    public void setJourDebut(LocalDate jourDebut) {
        this.jourDebut = jourDebut;
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
        Label heureLabel = new Label(sdf.format(heureDebut) + " - " + sdf.format(heureFin));
        Label salleLabel = new Label(salle);
        Label descriptionLabel = new Label(description);
        vbox.setStyle("-fx-background-color: lightblue;");
        vbox.getChildren().addAll(heureLabel, salleLabel, descriptionLabel);
        return vbox;
    }
}
