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
import java.util.ArrayList;
import java.util.Date;

public class Main extends Application {

    public static void main(String[] args) {
        CreneauController creneauController = new CreneauController();
        System.out.println("Launching");
        ICSParsing icsParsing = new ICSParsing();

        Calendar calendar = icsParsing.parse("src/main/resources/sacco_1.ics");
        creneauController.setCours(icsParsing.getAllCours(calendar));

        System.out.println("Cours : " + creneauController.getCours().size());
        // ArrayList<Creneau> coursAujourdhui = creneauController.getCoursByDay("2024-03-14");
        // creneauController.afficherEmploiDuTemps(coursAujourdhui);

        // ArrayList<Creneau> coursVernet = creneauController.getCoursByProf("VERNET");
        // creneauController.afficherEmploiDuTemps(coursVernet);

        ArrayList<Creneau> coursSalle = creneauController.getCoursBySalle("C 137");
        creneauController.afficherEmploiDuTemps(coursSalle);

        // launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Charge le fichier FXML de votre interface utilisateur (Login.fxml)
        Parent root = FXMLLoader.load(getClass().getResource("components/login/LoginInterface.fxml"));
        primaryStage.setTitle("P-ETD-H");

        primaryStage.setResizable(false);

        primaryStage.setScene(new Scene(root, 1000, 700));
        primaryStage.show();
        
    }

}
