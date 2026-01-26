package control;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

import java.io.InputStream;

public class ImagenConBotonesController {

    @FXML private ImageView imageView;
    @FXML private Label nombreEjercicio;
    @FXML private Button likeButton;
    @FXML private Button addButton;   // ← CORREGIDO

    private Runnable onLike;
    private Runnable onAdd;

    @FXML
    public void initialize() {
        addButton.setText("➕ Añadir");

        likeButton.setOnAction(e -> {
            if (onLike != null) onLike.run();
        });

        addButton.setOnAction(e -> {
            if (onAdd != null) onAdd.run();
        });
    }

    public void setImage(String ruta) {
        try (InputStream is = getClass().getResourceAsStream(ruta)) {
            if (is == null) {
                System.err.println("No se encontró la imagen: " + ruta);
                return;
            }
            imageView.setImage(new Image(is));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setNombre(String nombre) {
        nombreEjercicio.setText(nombre);
    }

    public String getNombre() {
        return nombreEjercicio.getText();
    }

    public void setOnLike(Runnable action) {
        this.onLike = action;
    }

    public void setOnAdd(Runnable action) {
        this.onAdd = action;
    }
}
