package parsing.fonctParsing;


import javafx.scene.layout.VBox;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

import javafx.scene.control.Label;

public class CreneauController {

    ArrayList<Creneau> cours = new ArrayList<>();

    public ArrayList<Creneau> getCours() {
        return cours;
    }

    public void setCours(ArrayList<Creneau> cours) {
        this.cours = cours;
    }

    // day format : yyyy-MM-dd
    public ArrayList<Creneau> getCoursByDay(String day) {
        ArrayList<Creneau> filteredCours = new ArrayList<>();
        LocalDate date = LocalDate.of(2024, 3, 14);
        for (Creneau c : cours) {
            if (c.getHeureDebut().equals(date)) {
                filteredCours.add(c);
            }
        }
        return filteredCours;
    }

    public ArrayList<Creneau> getCoursByPeriod(LocalDate debut, LocalDate fin) {
        ArrayList<Creneau> filteredCours = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (Creneau c : cours) {
            if (c.getJourDebut().isAfter(debut)) {
                filteredCours.add(c);
            }
        }
        return filteredCours;
    }

    public ArrayList<Creneau> getCoursByProf(String prof) {
        ArrayList<Creneau> filteredCours = new ArrayList<>();
        for (Creneau c : cours) {
            if (c.getDescription().contains(prof)) {
                filteredCours.add(c);
            }
        }
        return filteredCours;
    }

    public ArrayList<Creneau> getCoursBySalle(String salle) {
        ArrayList<Creneau> filteredCours = new ArrayList<>();
        for (Creneau c : cours) {
            if (c.getSalle().contains(salle)) {
                filteredCours.add(c);
            }
        }
        return filteredCours;
    }

    public ArrayList<Creneau> getCoursByAnything(String type) {
        ArrayList<Creneau> filteredCours = new ArrayList<>();
        for (Creneau c : cours) {
            if (c.getDescription().contains(type)) {
                filteredCours.add(c);
            }
        }
        return filteredCours;
    }
    public void afficherEmploiDuTemps(ArrayList<Creneau> cours) {
        for (Creneau c : cours) {
            System.out.println("Cours : " + c.getDescription() + " de " + c.getHeureDebut() + " à " + c.getHeureFin() + " en salle " + c.getSalle());
        }
    }

    public ArrayList<VBox> afficherEmploiDuTempsVBox(ArrayList<Creneau> cours) {
        ArrayList<VBox> vboxes = new ArrayList<>();
        VBox vbox = new VBox();
        for (Creneau c : cours) {
            Label label = new Label(c.getDescription());
            vbox.getChildren().add(label);
            vboxes.add(vbox);
        }

        return vboxes;
    }
}
