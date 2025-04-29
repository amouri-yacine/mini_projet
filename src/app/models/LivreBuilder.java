package app.models;

public class LivreBuilder {
    // Attributes for building the book
    private int id;
    private String titre;
    private String auteur;
    private boolean disponible = true; // By default, a book is available

    // Builder methods
    public LivreBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public LivreBuilder setTitre(String titre) {
        this.titre = titre;
        return this;
    }

    public LivreBuilder setAuteur(String auteur) {
        this.auteur = auteur;
        return this;
    }

    public LivreBuilder setDisponible(boolean disponible) {
        this.disponible = disponible;
        return this;
    }

    // Build method: creates an instance of app.models.Livre
    public Livre build() {
        return new LivreImpl(id, titre, auteur, disponible);
    }

    // Private class implementing app.models.Livre
    private static class LivreImpl implements Livre {
        private int id;
        private String titre;
        private String auteur;
        private boolean disponible;

        public LivreImpl(int id, String titre, String auteur, boolean disponible) {
            this.id = id;
            this.titre = titre;
            this.auteur = auteur;
            this.disponible = disponible;
        }

        @Override
        public int getId() {
            return id;
        }

        @Override
        public String getTitre() {
            return titre;
        }

        @Override
        public String getAuteur() {
            return auteur;
        }

        @Override
        public boolean isDisponible() {
            return disponible;
        }

        @Override
        public void setDisponible(boolean disponible) {
            this.disponible = disponible;
        }
    }
}
