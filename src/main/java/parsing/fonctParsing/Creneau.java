package parsing.fonctParsing;

import java.util.ArrayList;
import java.util.Date;

public class Creneau {

    Date heureDeb;
    Date heureFin;
    String salle;
    String description;

    public Creneau(Date heureDeb, Date heureFin, String salle, String description) {
        this.heureDeb = heureDeb;
        this.heureFin = heureFin;
        this.salle = salle;
        this.description = description;
    }

    public Date getHeureDeb() {
        return heureDeb;
    }

    public void setHeureDeb(Date heureDeb) {
        this.heureDeb = heureDeb;
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
