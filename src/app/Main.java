package app;

import app.models.Admin;
import app.models.Lecteur;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Admin.ajouterLecteur(new Lecteur(1,"yes","ab","ab"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/views/LoginView.fxml"));
        Scene scene = new Scene(loader.load());
        primaryStage.setScene(scene);
        primaryStage.setTitle("app.models.Bibliotheque App");
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
