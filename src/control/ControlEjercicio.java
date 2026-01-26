package control;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import model.Ejercicio;

public class ControlEjercicio {

    private Ejercicio ejercicio;

    // Referencias a elementos del FXML
    public ImageView imageView;  // asegúrate de que tenga fx:id="imageView" en el FXML
    public AnchorPane rootPane;  // fx:id="rootPane"

    private Runnable onLike;
    private Runnable onDelete;

    public void setEjercicio(Ejercicio ejercicio) {
        this.ejercicio = ejercicio;
    }

    public void setImage(String ruta) {
        imageView.setImage(new Image(ruta));
    }

    public void setOnLike(Runnable action) {
        this.onLike = action;
    }

    public void setOnDelete(Runnable action) {
        this.onDelete = action;
    }

    // Métodos que se vinculen a botones en FXML
    public void handleLike() {
        if (onLike != null) onLike.run();
    }

    public void handleDelete() {
        if (onDelete != null) onDelete.run();
    }
}
