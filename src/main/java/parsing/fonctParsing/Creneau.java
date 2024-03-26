package parsing.fonctParsing;

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
}
