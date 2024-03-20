package parsing;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.model.*;
import net.fortuna.ical4j.model.component.VEvent;
import parsing.fonctParsing.Creneau;


import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;


public class ICSParsing{

    public Calendar parse(String filePath) {
        System.out.println("#-------------------------------------------#");
        try{
            FileInputStream fin = new FileInputStream(filePath);
            CalendarBuilder builder = new CalendarBuilder();
            Calendar calendar = builder.build(fin);
            return calendar;
        }catch (Exception e){
            System.out.println("Erreur parsing edt : " + e.getMessage());
        }
        System.out.println("#-------------------------------------------#");
        return null;
    }

    public ArrayList<Creneau> getAllCours(Calendar calendar){

        ArrayList<Creneau> cours = new ArrayList<>();

        // Récupère tous les events de type VEVENT
        /*
        List<VEvent> events = calendar.getComponents("VEVENT");
        for (VEvent event : events) {
            if (event.getSummary().getValue().contains("VERNET")) { // Si SUMMARY contient VERNET
                System.out.println("Cours avec VERNET");
            }
        } */
        // Récupère tous les events de la semaine
        /*
        java.util.Calendar today = java.util.Calendar.getInstance();
        today.set(java.util.Calendar.HOUR_OF_DAY, 0);
        today.clear(java.util.Calendar.MINUTE);
        today.clear(java.util.Calendar.SECOND);

        // Récupère tous les events de la semaine
        java.util.Calendar nextDay = java.util.Calendar.getInstance();
        nextDay.set(java.util.Calendar.HOUR_OF_DAY, 0);
        nextDay.clear(java.util.Calendar.MINUTE);
        nextDay.clear(java.util.Calendar.SECOND);
        nextDay.add(java.util.Calendar.DAY_OF_MONTH, 7);

        System.out.println("Today: " + today.getTime());
        System.out.println("Next day: " + nextDay.getTime());

        Period period = new Period(new DateTime(today.getTime()), new DateTime(nextDay.getTime()));
        */

        List<VEvent> events = calendar.getComponents("VEVENT");
        for (VEvent event : events) {
            String location = event.getLocation() == null ? "null" : event.getLocation().getValue();
            String description = event.getDescription() == null ? "null" : event.getDescription().getValue();
            cours.add(new Creneau(event.getStartDate().getDate(), event.getEndDate().getDate(), location, description));
        }
        return cours;
    }
}
