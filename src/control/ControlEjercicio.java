package control;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import model.Ejercicio;

/**
 * Controlador de un ejercicio individual dentro de la rutina.
 * Se encarga de mostrar la imagen del ejercicio y gestionar
 * las acciones asociadas (like y eliminar).
 */
public class ControlEjercicio {

    private Ejercicio ejercicio;

    // Referencias a elementos del FXML
    public ImageView imageView;  // asegúrate de que tenga fx:id="imageView" en el FXML
    public AnchorPane rootPane;  // fx:id="rootPane"

    private Runnable onLike;
    private Runnable onDelete;

    /**
     * Establece el ejercicio asociado a este controlador.
     *
     * @param ejercicio ejercicio a mostrar
     */
    public void setEjercicio(Ejercicio ejercicio) {
        this.ejercicio = ejercicio;
    }

    /**
     * Asigna la imagen del ejercicio a partir de su ruta.
     *
     * @param ruta ruta de la imagen del ejercicio
     */
    public void setImage(String ruta) {
        imageView.setImage(new Image(ruta));
    }

    /**
     * Define la acción que se ejecutará al pulsar el botón de "like".
     *
     * @param action acción a ejecutar
     */
    public void setOnLike(Runnable action) {
        this.onLike = action;
    }

    /**
     * Define la acción que se ejecutará al pulsar el botón de eliminar.
     *
     * @param action acción a ejecutar
     */
    public void setOnDelete(Runnable action) {
        this.onDelete = action;
    }

    /**
     * Maneja la acción del botón de "like".
     * Ejecuta la acción asociada si existe.
     */
    public void handleLike() {
        if (onLike != null) {
            onLike.run();
        }
    }

    /**
     * Maneja la acción del botón de eliminar.
     * Ejecuta la acción asociada si existe.
     */
    public void handleDelete() {
        if (onDelete != null) {
            onDelete.run();
        }
    }
}
