package app.controllers;

import app.models.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.*;
import java.util.Optional;

public class AdminDashboardController {


    @FXML
    private ListView<String> bookListView;

    @FXML
    private ListView<String> lecteurListView;
    private int lecteurIdCounter;
    private int livreIdCounter;
    private ListView<String> livreListView = new ListView<>();

    @FXML
    public void initialize() {
        // Initialize with some dummy data (later we connect real app.models.Bibliotheque)
        bookListView.getItems().addAll(Bibliotheque.getInstance().getLivreNames());
        for (Lecteur lecteur : Admin.getLecteurs()) {
            lecteurListView.getItems().add(lecteur.getNom());
        }
    }

    @FXML
    private void handleAddLivre() {
        // Create a custom dialog
        Dialog<Livre> dialog = new Dialog<>();
        dialog.setTitle("Add New Livre");
        dialog.setHeaderText("Enter Book Information:");

        // Set the button types
        ButtonType addButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

        // Create fields
        TextField titleField = new TextField();
        titleField.setPromptText("Title");
        TextField authorField = new TextField();
        authorField.setPromptText("Author");
        CheckBox availableCheckBox = new CheckBox("Available");
        availableCheckBox.setSelected(true); // by default true

        VBox content = new VBox(10);
        content.getChildren().addAll(titleField, authorField, availableCheckBox);
        dialog.getDialogPane().setContent(content);

        // Convert the result to a Livre when Add button is clicked
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButtonType) {
                return new LivreBuilder()
                        .setId(livreIdCounter++) // Assuming you have a livreIdCounter like lecteurIdCounter
                        .setTitre(titleField.getText())
                        .setAuteur(authorField.getText())
                        .setDisponible(availableCheckBox.isSelected())
                        .build();
            }
            return null;
        });

        Optional<Livre> result = dialog.showAndWait();

        result.ifPresent(livre -> {
            Admin.ajouterLivre(livre);
            bookListView.getItems().add(livre.getTitre()); // Add title to the list
        });
    }


    @FXML
    private void handleRemoveBook() {
        String selected = bookListView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            bookListView.getItems().remove(selected);
        }
    }

    @FXML
    private void handleAddLecteur() {
        // Create a custom dialog
        Dialog<Lecteur> dialog = new Dialog<>();
        dialog.setTitle("Add New Lecteur");
        dialog.setHeaderText("Enter Lecteur Information:");

        // Set the button types
        ButtonType addButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

        // Create fields
        TextField nameField = new TextField();
        nameField.setPromptText("Name");
        TextField loginField = new TextField();
        loginField.setPromptText("Login");
        TextField passwordField = new TextField();
        passwordField.setPromptText("Password");

        VBox content = new VBox(10);
        content.getChildren().addAll(nameField, loginField, passwordField);
        dialog.getDialogPane().setContent(content);

        // Convert the result to a Lecteur when the Add button is clicked
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButtonType) {
                return new Lecteur(lecteurIdCounter++,
                        nameField.getText(),
                        loginField.getText(),
                        passwordField.getText()
                );
            }
            return null;
        });

        Optional<Lecteur> result = dialog.showAndWait();

        result.ifPresent(lecteur -> {
            Admin.ajouterLecteur(lecteur);
            lecteurListView.getItems().add(lecteur.getNom());
        });
    }


    @FXML
    private void handleRemoveLecteur() {
        String selected = lecteurListView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            lecteurListView.getItems().remove(selected);
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
