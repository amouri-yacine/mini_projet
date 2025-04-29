// Lecteur.java
package app.models;

import javafx.application.Platform;
import javafx.scene.control.Alert;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Lecteur implements Utilisateur, RetardObserver {
    private int id;
    private String nom;
    private String login;
    private String motDePasse;
    private List<Livre> livresEmpruntes;
    public List<Emprunt> emprunts = new ArrayList<>();

    public Lecteur(int id, String nom, String login, String motDePasse) {
        this.id = id;
        this.nom = nom;
        this.login = login;
        this.motDePasse = motDePasse;
        this.livresEmpruntes = new ArrayList<>();
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getNom() {
        return nom;
    }

    @Override
    public String getLogin() {
        return login;
    }

    @Override
    public String getMotDePasse() {
        return motDePasse;
    }

    @Override
    public Emprunt emprunterLivre(Livre livre) {
        if (livre != null && livre.isDisponible()) {
            livre.setDisponible(false);
            Date dateRetour = new Date(System.currentTimeMillis() + (7L * 24 * 60 * 60 * 1000)); // 7 days later
            Emprunt emprunt = new Emprunt(generateId(), this, livre, dateRetour);
            emprunts.add(emprunt);
            emprunt.addObserver(this);
            System.out.println("Livre emprunté: " + livre.getTitre());
            return emprunt;
        } else {
            System.out.println("Ce livre n'est pas disponible.");
            return null;
        }

    }

    private int generateId() {
        return emprunts.size() + 1; // Simplified ID generation
    }
    @Override
    public void update(Emprunt e) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Livre en retard");
            alert.setHeaderText("⚠️ Attention " + nom + " !");
            alert.setContentText("Votre livre \"" + e.getLivre().getTitre() + "\" est en retard ou doit être retourné très bientôt.");
            alert.showAndWait();
        });
    }


    public List<Livre> getLivresEmpruntes() {
        return livresEmpruntes;
    }
}

