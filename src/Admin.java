import app.models.Bibliotheque;
import app.models.Emprunt;
import app.models.Livre;
import app.models.Utilisateur;

import java.util.ArrayList;
import java.util.List;

public class Admin implements Utilisateur {
    private int id;
    private String nom;
    private String login;
    private String motDePasse;
    private List<Lecteur> lecteurs;

    public Admin(int id, String nom, String login, String motDePasse) {
        this.id = id;
        this.nom = nom;
        this.login = login;
        this.motDePasse = motDePasse;
        this.lecteurs = new ArrayList<>();
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
        System.out.println("Les admins n'empruntent généralement pas de livres.");
        return null;
    }

    // Admin-specific methods to manage Lecteurs
    public void ajouterLecteur(Lecteur lecteur) {
        lecteurs.add(lecteur);
        System.out.println("Lecteur ajouté: " + lecteur.getNom());
    }

    public void supprimerLecteur(Lecteur lecteur) {
        lecteurs.remove(lecteur);
        System.out.println("Lecteur supprimé: " + lecteur.getNom());
    }

    public List<Lecteur> getLecteurs() {
        return lecteurs;
    }

    // Admin-specific methods to manage Books
    public void ajouterLivre(Livre livre) {
        Bibliotheque.getInstance().ajouterLivre(livre);
        System.out.println("app.models.Livre ajouté: " + livre.getTitre());
    }

    public void supprimerLivre(Livre livre) {
        Bibliotheque.getInstance().supprimerLivre(livre);
        System.out.println("app.models.Livre supprimé: " + livre.getTitre());
    }
}
