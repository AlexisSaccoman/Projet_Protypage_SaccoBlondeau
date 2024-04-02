package parsing;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.model.*;
import net.fortuna.ical4j.model.component.VEvent;
import parsing.fonctParsing.Creneau;


import java.io.FileInputStream;
import java.time.LocalDateTime;
import java.time.ZoneId;
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

        List<VEvent> events = calendar.getComponents("VEVENT");
        for (VEvent event : events) {
            String location = event.getLocation() == null ? null : event.getLocation().getValue();
            String description = event.getDescription() == null ? null : event.getDescription().getValue();
            if (description == null) { // Si la description est null, on essaie de résupérer le summary (pour les jours fériés par exemple, "Férié" est écrit dans summary)
                description = event.getSummary() == null ? null : event.getSummary().getValue();
            }
            cours.add(new Creneau(event.getStartDate().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(), event.getEndDate().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(), location, description));
        }
        return cours;
    }
}
