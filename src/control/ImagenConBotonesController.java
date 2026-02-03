package control;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

import java.io.InputStream;

public class ImagenConBotonesController {

    // Imagen del ejercicio (GIF o PNG)
    @FXML private ImageView imageView;

    // Nombre del ejercicio
    @FXML private Label nombreEjercicio;

    // Botón de “like”
    @FXML private Button likeButton;

    // Botón para añadir el ejercicio a la rutina
    @FXML private Button addButton;

    // Acción que se ejecuta al pulsar Like
    private Runnable onLike;

    // Acción que se ejecuta al pulsar Añadir
    private Runnable onAdd;

    @FXML
    public void initialize() {

        // Texto del botón Añadir
        addButton.setText("➕ Añadir");

        // Ejecuta la acción de Like si existe
        likeButton.setOnAction(e -> {
            if (onLike != null) onLike.run();
        });

        // Ejecuta la acción de Añadir si existe
        addButton.setOnAction(e -> {
            if (onAdd != null) onAdd.run();
        });
    }

    // Carga la imagen desde recursos usando la ruta
    public void setImage(String ruta) {
        try (InputStream is = getClass().getResourceAsStream(ruta)) {

            // Si la imagen no existe, se avisa por consola
            if (is == null) {
                System.err.println("No se encontró la imagen: " + ruta);
                return;
            }

            // Asigna la imagen al ImageView
            imageView.setImage(new Image(is));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Establece el nombre del ejercicio
    public void setNombre(String nombre) {
        nombreEjercicio.setText(nombre);
    }

    // Devuelve el nombre del ejercicio
    public String getNombre() {
        return nombreEjercicio.getText();
    }

    // Define la acción del botón Like
    public void setOnLike(Runnable action) {
        this.onLike = action;
    }

    // Define la acción del botón Añadir
    public void setOnAdd(Runnable action) {
        this.onAdd = action;
    }
}
