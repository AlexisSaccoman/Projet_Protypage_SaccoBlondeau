import net.fortuna.ical4j.model.Calendar;
import parsing.ICSParsing;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import parsing.fonctParsing.CreneauManager;

import java.io.IOException;
import java.io.InputStream;

public class Main extends Application {

    public static void main(String[] args) {
        CreneauManager creneauManager = new CreneauManager();
        System.out.println("Launching");
        ICSParsing icsParsing = new ICSParsing();
        Calendar calendar = icsParsing.parse("src/main/resources/sacco_1.ics");
        creneauManager.setCours(icsParsing.getAllCours(calendar));
        System.out.println("Cours : " + creneauManager.getCours().size());
        //launch(args);
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
