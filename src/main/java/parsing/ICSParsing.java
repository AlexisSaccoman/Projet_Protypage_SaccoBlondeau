package parsing;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.filter.predicate.PeriodRule;
import net.fortuna.ical4j.model.*;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.Summary;
import parsing.fonctParsing.creneau;


import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;


public class ICSParsing{

    ArrayList<ArrayList<creneau>> semaine = new ArrayList<ArrayList<creneau>>();

    public Calendar parse(String filePath) {
        System.out.println("#-------------------------------------------#");
        try{
            FileInputStream fin = new FileInputStream(filePath);
            CalendarBuilder builder = new CalendarBuilder();
            Calendar calendar = builder.build(fin);
            return calendar;

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        System.out.println("#-------------------------------------------#");
        return null;
    }

    public void filter(Calendar calendar){
        // Récupère tous les events de type VEVENT
        List<VEvent> events = calendar.getComponents("VEVENT");
        for (VEvent event : events) {
            if (event.getSummary().getValue().contains("VERNET")) { // Si SUMMARY contient VERNET
                System.out.println("Cours avec VERNET");
            }
        }
    }
}
