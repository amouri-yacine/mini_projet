package app.controllers;

import app.models.Admin;
import app.models.Lecteur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private TextField usernameField; // <-- Link to the username TextField from FXML

    @FXML
    private PasswordField passwordField; // <-- Link to the password PasswordField from FXML

    @FXML
    private void handleLogin(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if ("admin".equals(username) && "admin".equals(password)) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/views/AdminDashboard.fxml"));
                Parent root = loader.load();

                Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Lecteur matchingLecteur = null;
            for (Lecteur lecteur : Admin.getLecteurs()) {  // ✅ use Admin.getLecteurs()
                if (lecteur.getLogin().equals(username) && lecteur.getMotDePasse().equals(password)) {
                    matchingLecteur = lecteur;
                    break;
                }
            }

            if (matchingLecteur != null) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/views/LecteurDashboard.fxml"));
                    Parent root = loader.load();
                    LecteurDashboardController controller = loader.getController();
                    controller.setLecteur(matchingLecteur); // ✅ pass the logged-in user

                    Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                showAlert("Login Failed", "Invalid credentials.");
            }
        }
    }



    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
