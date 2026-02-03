package control;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Controlador de la vista de selección de músculos.
 * Permite elegir un grupo muscular y cargar la ventana
 * de ejercicios correspondientes.
 */
public class ControlMusculos {

    @FXML
    private AnchorPane rootPane;

    /**
     * Referencia al controlador de la rutina para poder
     * añadir ejercicios seleccionados.
     */
    private ControlRutina controlRutina;

    /**
     * Establece el controlador de la rutina asociado.
     *
     * @param controller controlador de la rutina
     */
    public void setControlRutina(ControlRutina controller) {
        this.controlRutina = controller;
    }

    /**
     * Maneja la acción del botón "Atrás".
     * Cierra la ventana actual.
     */
    @FXML
    private void onAtrasClicked() {
        ((Stage) rootPane.getScene().getWindow()).close();
    }

    /**
     * Maneja la selección de un músculo.
     * Carga la vista de ejercicios correspondiente al músculo
     * seleccionado y abre una nueva ventana.
     *
     * @param event evento de acción generado por el botón
     */
    @FXML
    private void onMusculoSeleccionado(ActionEvent event) {
        Button boton = (Button) event.getSource();
        String musculo = boton.getText();

        if (controlRutina == null) {
            System.out.println("ERROR: controlRutina es NULL");
            return;
        }

        try {
            // 🔹 Cargar FXML nuevo
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/view/ViewEjercicios.fxml")
            );
            Parent root = loader.load();

            // 🔹 Obtener controlador
            ControlEjercicios ejerciciosController = loader.getController();
            ejerciciosController.setControlRutina(controlRutina);
            ejerciciosController.cargarEjercicios(musculo);

            // 🔹 Crear nueva escena
            Scene scene = new Scene(root);

            // 🔹 Cargar CSS
            scene.getStylesheets().add(
                    getClass().getResource("/css/componente.css").toExternalForm()
            );

            // 🔹 Crear nuevo Stage
            Stage stage = new Stage();
            stage.setTitle("Ejercicios de " + musculo);
            stage.setScene(scene);
            stage.show();

            // 🔹 Cerrar ventana actual
            ((Stage) rootPane.getScene().getWindow()).close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
