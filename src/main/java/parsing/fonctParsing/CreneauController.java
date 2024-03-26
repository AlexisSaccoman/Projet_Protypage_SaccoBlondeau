package parsing.fonctParsing;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (Creneau c : cours) {
            if (sdf.format(c.getHeureDebut()).equals(day)) {
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
    public void afficherEmploiDuTemps(ArrayList<Creneau> cours) {
        for (Creneau c : cours) {
            System.out.println("Cours : " + c.getDescription() + " de " + c.getHeureDebut() + " à " + c.getHeureFin() + " en salle " + c.getSalle());
        }
    }
}
