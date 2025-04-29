package app.models;

public interface Livre {
    int getId();
    String getTitre();
    String getAuteur();
    boolean isDisponible();
    void setDisponible(boolean disponible);
}
