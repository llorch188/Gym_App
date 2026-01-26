package control;

import javafx.scene.Scene;

public class ThemeManager {

    private static ThemeManager instance;

    private String currentTheme = "/css/orange.css"; // tema por defecto

    private ThemeManager() {}

    public static ThemeManager getInstance() {
        if (instance == null) {
            instance = new ThemeManager();
        }
        return instance;
    }

    public void applyTheme(Scene scene) {
        scene.getStylesheets().clear();
        scene.getStylesheets().add(getClass().getResource(currentTheme).toExternalForm());
    }

    public void changeTheme(String themePath) {
        currentTheme = themePath;
    }

    public String getCurrentTheme() {
        return currentTheme;
    }
}
