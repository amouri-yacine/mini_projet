package app.controllers;

import app.models.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class LecteurDashboardController implements RetardObserver {
    private Lecteur lecteur; // ✅ Store current user

    public void setLecteur(Lecteur lecteur) {
        this.lecteur = lecteur;
        postLecteurInit();  // 👈 safe to call now that lecteur is set
    }

    @FXML
    private ListView<Livre> availableBooksList;

    @FXML
    public void initialize() {
        // Load books from Bibliotheque
        availableBooksList.getItems().addAll(Bibliotheque.getInstance().getLivres());

        // Customize how each item looks
        availableBooksList.setCellFactory(listView -> new ListCell<>() {
            private final Label bookLabel = new Label();
            private final Button loanButton = new Button("+");
            private final HBox hBox = new HBox(10, bookLabel, loanButton);

            {
                loanButton.setOnAction(event -> loanBook(getItem()));
            }

            @Override
            protected void updateItem(Livre livre, boolean empty) {
                super.updateItem(livre, empty);
                if (empty || livre == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    bookLabel.setText(livre.getTitre() + " by " + livre.getAuteur());
                    setGraphic(hBox);
                }
            }
        });
    }
    public void postLecteurInit() {
        if (lecteur == null) return;
        for (Emprunt e : lecteur.emprunts) {
            if (LocalDateTime.now().isAfter(e.dateRetour)) {
                update(e); // trigger alert immediately
            }
        }
    }


    @Override
    public void update(Emprunt emprunt) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Overdue Book");
            alert.setHeaderText("Loan overdue!");
            alert.setContentText("The loan for \"" + emprunt.getLivre().getTitre() + "\" is overdue.");
            alert.showAndWait();
        });
    }

    private void loanBook(Livre livre) {
        if (livre != null && livre.isDisponible()) {
            Emprunt emprunt = lecteur.emprunterLivre(livre);
            livre.setDisponible(false);



            emprunt.addObserver(new RetardObserver() {
                @Override
                public void update(Emprunt e) {
                    if (UIState.isLecteurDashboardOpen()) {
                        update(e); // show alert
                    } else {
                        System.out.println("Overdue book: " + e.getLivre().getTitre());
                    }
                }
            });

            ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
            scheduler.schedule(emprunt::checkRetard, 30, TimeUnit.SECONDS);
        }
    }
    @FXML
    private void handleLogout(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/resources/views/LoginView.fxml"));
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
