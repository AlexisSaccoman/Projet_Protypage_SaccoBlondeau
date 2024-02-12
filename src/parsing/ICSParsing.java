package parsing;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.model.Calendar;
import parsing.fonctParsing.creneau;

import java.io.FileInputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;


public class ICSParsing{

    ArrayList<ArrayList<creneau>> semaine = new ArrayList<ArrayList<creneau>>();

    public void parse(String filePath) {
        System.out.println("#-------------------------------------------#");

        try{
            FileInputStream fin = new FileInputStream(filePath);
            CalendarBuilder builder = new CalendarBuilder();
            Calendar calendar = builder.build(fin);
            System.out.println(calendar);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        System.out.println("#-------------------------------------------#");

    }
}
