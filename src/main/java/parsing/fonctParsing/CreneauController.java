package parsing.fonctParsing;


import javafx.scene.layout.VBox;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
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
    public ArrayList<Creneau> getCoursByDay(LocalDate day) {
        ArrayList<Creneau> filteredCours = new ArrayList<>();
        for (Creneau c : cours) {
            if (c.getJour().equals(day)) {
                filteredCours.add(c);
            }
        }
        return filteredCours;
    }

    public void addCreneau(Creneau creneau) {
        cours.add(creneau);
    }
    public void addCreneaux(ArrayList<Creneau> creneaux) {
        for (Creneau c : creneaux) {
            cours.add(c);
        }
    }

    public boolean isCreneauUsed(LocalTime debut, LocalTime fin, LocalDate jour) {
        ArrayList<Creneau> filteredCours = new ArrayList<>(getCoursByDay(jour));

        for (Creneau c : filteredCours) {
            System.out.println("Debut : " + debut + " Fin : " + fin + " c.getHeureDebut() : " + c.getHeureDebut() + " c.getHeureFin() : " + c.getHeureFin());
            if ((debut.isAfter(c.getHeureDebut()) && debut.isBefore(c.getHeureFin())) ||
                    (fin.isAfter(c.getHeureDebut()) && fin.isBefore(c.getHeureFin())) ||
                    (debut.equals(c.getHeureDebut()) && fin.equals(c.getHeureFin())) ||
                    (debut.isBefore(c.getHeureDebut()) && fin.equals(c.getHeureFin()))) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<Creneau> getCoursByDayPeriod(LocalDate debut, LocalDate fin) {
        ArrayList<Creneau> filteredCours = new ArrayList<>();

        for (Creneau c : cours) {
            if ((c.getJour().isEqual(debut) || c.getJour().isAfter(debut)) && (c.getJour().isBefore(fin) || c.getJour().isEqual(fin))) {
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
            if (c.getSalle() != null) {
                if (c.getSalle().contains(salle)) {
                    filteredCours.add(c);
                }
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
            System.out.println("Cours : " + c.getDescription() + " le " + c.getJour() + " de " + c.getHeureDebut() + " à " + c.getHeureFin() + " en salle " + c.getSalle());
        }
    }
}
