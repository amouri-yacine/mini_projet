package app.models;

import java.util.ArrayList;
import java.util.List;

public class Bibliotheque {

    // Static instance for Singleton
    private static Bibliotheque instance;

    // Attributes
    private static List<Livre> livres;


    // Private constructor to prevent instantiation
    private Bibliotheque() {
        livres = new ArrayList<>();
        livres.add(new LivreBuilder()
                .setId(1)
                .setTitre("Le Petit Prince")
                .setAuteur("Antoine de Saint-Exupéry")
                .setDisponible(true)
                .build());

        livres.add(new LivreBuilder()
                .setId(2)
                .setTitre("1984")
                .setAuteur("George Orwell")
                .setDisponible(true)
                .build());

        livres.add(new LivreBuilder()
                .setId(3)
                .setTitre("Le Comte de Monte-Cristo")
                .setAuteur("Alexandre Dumas")
                .setDisponible(true)
                .build());
    }

    // Public method to provide access to the instance
    public static Bibliotheque getInstance() {
        if (instance == null) {
            instance = new Bibliotheque();
        }
        return instance;
    }

    // Methods to manage books
    public void ajouterLivre(Livre livre) {
        livres.add(livre);
    }

    public void supprimerLivre(Livre livre) {
        livres.remove(livre);
    }

    public Livre rechercherLivre(String titre) {
        for (Livre livre : livres) {
            if (livre.getTitre().equalsIgnoreCase(titre)) {
                return livre;
            }
        }
        return null;
    }
    public List<String> getLivreNames() {
        List<String> names = new ArrayList<>();
        for (Livre livre : livres) {
            names.add(livre.getTitre());
        }
        return names;
    }


    public static List<Livre> getLivres() {
        return livres;
    }

}
