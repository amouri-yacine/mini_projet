package app.models;

public class UIState {
    private static boolean lecteurDashboardOpen = false;

    public static boolean isLecteurDashboardOpen() {
        return lecteurDashboardOpen;
    }

    public static void setLecteurDashboardOpen(boolean open) {
        lecteurDashboardOpen = open;
    }
}

