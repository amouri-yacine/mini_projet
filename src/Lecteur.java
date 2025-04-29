import app.models.Emprunt;
import app.models.Livre;
import app.models.RetardObserver;
import app.models.Utilisateur;

import java.util.ArrayList;
import java.util.List;

public class Lecteur implements Utilisateur, RetardObserver {
    private int id;
    private String nom;
    private String login;
    private String motDePasse;
    private List<Livre> livresEmpruntes;

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
        if (livre.isDisponible()) {
            livresEmpruntes.add(livre);
            livre.setDisponible(false);
            System.out.println(nom + " a emprunté le livre: " + livre.getTitre());
        } else {
            System.out.println("Le livre " + livre.getTitre() + " n'est pas disponible.");
        }
        return null;
    }
    @Override
    public void update(Emprunt e) {
        System.out.println("⚠️ Attention " + nom + "! Votre livre est en retard ou doit être retourné très bientôt !");
    }


    public List<Livre> getLivresEmpruntes() {
        return livresEmpruntes;
    }
}
