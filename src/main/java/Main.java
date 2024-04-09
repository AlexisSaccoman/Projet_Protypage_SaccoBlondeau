import net.fortuna.ical4j.model.Calendar;
import parsing.ICSParsing;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import parsing.fonctParsing.Creneau;
import parsing.fonctParsing.CreneauController;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class Main extends Application {

    public static void main(String[] args) {
        CreneauController creneauController = new CreneauController();
        System.out.println("Launching");
        ICSParsing icsParsing = new ICSParsing();
        System.setProperty("ical4j.unfolding.relaxed", "true");

        Calendar calendar = icsParsing.parse("src/main/java/db/ics/blondeau.ics");
        creneauController.setCours(icsParsing.getAllCours(calendar));

        System.out.println("Cours : " + creneauController.getCours().size());
        creneauController.afficherEmploiDuTemps(creneauController.getCours());

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("components/login/LoginInterface.fxml"));
        primaryStage.setTitle("P-ETD-H");

        primaryStage.setResizable(false);

        primaryStage.setScene(new Scene(root, 1070, 800));
        primaryStage.show();
        
    }

}
