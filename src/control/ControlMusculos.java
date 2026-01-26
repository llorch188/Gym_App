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

public class ControlMusculos {

    @FXML
    private AnchorPane rootPane;

    // Referencia a ControlRutina para añadir ejercicios
    private ControlRutina controlRutina;

    public void setControlRutina(ControlRutina controller) {
        this.controlRutina = controller;
    }

    @FXML
    private void onAtrasClicked() {
        ((Stage) rootPane.getScene().getWindow()).close();
    }

    @FXML
    private void onMusculoSeleccionado(ActionEvent event) {
        Button boton = (Button) event.getSource();
        String musculo = boton.getText();

        if (controlRutina == null) {
            System.out.println("ERROR: controlRutina es NULL");
            return;
        }

        try {
            // 🔹 Cargar FXML NUEVO (SIEMPRE)
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/view/ViewEjercicios.fxml")
            );
            Parent root = loader.load();

            // 🔹 Controlador
            ControlEjercicios ejerciciosController = loader.getController();
            ejerciciosController.setControlRutina(controlRutina);
            ejerciciosController.cargarEjercicios(musculo);

            // 🔹 Crear Scene NUEVA
            Scene scene = new Scene(root);

            // 🔹 Cargar CSS
            scene.getStylesheets().add(
                    getClass().getResource("/css/componente.css").toExternalForm()
            );

            // 🔹 Crear Stage NUEVO
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
