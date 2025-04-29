package app.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Emprunt {
    private int id;
    private Lecteur lecteur;
    private Livre livre;
    private LocalDateTime dateEmprunt;
    public LocalDateTime dateRetour;
    private final List<RetardObserver> observers = new ArrayList<>();

    public Emprunt(int id, Lecteur lecteur, Livre livre, Date dateRetour) {
        this.id = id;
        this.livre = livre;
        this.dateEmprunt = LocalDateTime.now();
        this.dateRetour = dateEmprunt.plusSeconds(30);
    }

    public Livre getLivre() { return livre; }
    public Lecteur getLecteur() { return lecteur; }


    public void addObserver(RetardObserver observer) {
        observers.add(observer);
    }
    public void checkRetard() {
        if (LocalDateTime.now().isAfter(dateRetour)) {
            notifyRetard();
        }
    }
    private void notifyRetard() {
        for (RetardObserver observer : observers) {
            observer.update(this);
        }
    }
}
