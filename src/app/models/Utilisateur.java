package app.models;

public interface Utilisateur {
    int getId();
    String getNom();
    String getLogin();
    String getMotDePasse();
    Emprunt emprunterLivre(Livre livre);
}
