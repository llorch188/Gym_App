package control;

import javafx.scene.Scene;

public class ThemeManager {

    // Instancia única del ThemeManager (patrón Singleton)
    private static ThemeManager instance;

    // Ruta del tema actual (tema por defecto)
    private String currentTheme = "/css/orange.css";

    // Constructor privado para evitar instanciación externa
    private ThemeManager() {}

    // Devuelve la instancia única del ThemeManager
    public static ThemeManager getInstance() {
        if (instance == null) {
            instance = new ThemeManager();
        }
        return instance;
    }

    // Aplica el tema actual a la escena recibida
    public void applyTheme(Scene scene) {
        // Elimina los estilos anteriores
        scene.getStylesheets().clear();

        // Añade el CSS del tema actual
        scene.getStylesheets().add(
                getClass().getResource(currentTheme).toExternalForm()
        );
    }

    // Cambia la ruta del tema actual
    public void changeTheme(String themePath) {
        currentTheme = themePath;
    }

    // Devuelve la ruta del tema actual
    public String getCurrentTheme() {
        return currentTheme;
    }
}
