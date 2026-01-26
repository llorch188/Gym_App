package control;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ControlMain {

    private Button selectedRutina;
    private Scene scene;

    @FXML
    private VBox contenedorRutinas; // Debe existir en tu FXML

    @FXML
    private void initialize() {
        // Guardamos la escena cuando esté lista
        Platform.runLater(() -> scene = contenedorRutinas.getScene());
    }

    // ------------------ Temas ------------------
    @FXML
    private void onOrange() { changeTheme("/css/orange.css"); }
    @FXML
    private void onBlue() { changeTheme("/css/blue.css"); }
    @FXML
    private void onRed() { changeTheme("/css/red.css"); }

    private void changeTheme(String cssFile) {
        ThemeManager.getInstance().changeTheme(cssFile);
        ThemeManager.getInstance().applyTheme(scene);
    }

    // ------------------ Rutina ------------------
    @FXML
    private void onRutinaClicked(MouseEvent event) {
        Button button = (Button) event.getSource();
        selectedRutina = button;

        // Resaltar visualmente
        contenedorRutinas.getChildren().forEach(node -> node.setStyle("-fx-font-size: 31; -fx-font-weight: normal;"));
        button.setStyle("-fx-font-size: 31; -fx-font-weight: bold; -fx-background-color: lightblue;");

        if (event.getClickCount() == 2) {
            openRutina(button.getText(), button);
        }
    }

    public void openRutina(String rutinaName, Button sourceButton) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ViewRutina.fxml"));
            AnchorPane root = loader.load();

            Scene rutinaScene = new Scene(root);
            ThemeManager.getInstance().applyTheme(rutinaScene);

            Stage rutinaStage = new Stage();
            rutinaStage.setScene(rutinaScene);
            rutinaStage.setTitle(rutinaName);
            rutinaStage.show();

            // Cierra la ventana principal
            Stage mainStage = (Stage) sourceButton.getScene().getWindow();
            mainStage.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void addRutina() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Nueva rutina");
        dialog.setHeaderText(null);
        dialog.setContentText("Nombre de la nueva rutina:");

        dialog.showAndWait().ifPresent(name -> {
            if (!name.trim().isEmpty()) {
                Button newRutina = new Button(name);
                newRutina.setPrefWidth(289);
                newRutina.setPrefHeight(57);
                newRutina.setStyle("-fx-font-size: 31; -fx-font-weight: normal;");
                newRutina.setOnMouseClicked(this::onRutinaClicked);

                contenedorRutinas.getChildren().add(newRutina);
            }
        });
    }

    @FXML
    private void editRutina() {
        if (selectedRutina == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Editar rutina");
            alert.setHeaderText(null);
            alert.setContentText("Debes seleccionar una rutina primero.");
            alert.showAndWait();
            return;
        }

        TextInputDialog dialog = new TextInputDialog(selectedRutina.getText());
        dialog.setTitle("Renombrar rutina");
        dialog.setHeaderText(null);
        dialog.setContentText("Nuevo nombre de la rutina:");

        dialog.showAndWait().ifPresent(newName -> {
            if (!newName.trim().isEmpty()) {
                selectedRutina.setText(newName);
            }
        });
    }

    @FXML
    private void deleteRutina() {
        if (selectedRutina == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Eliminar rutina");
            alert.setHeaderText(null);
            alert.setContentText("Debes seleccionar una rutina primero.");
            alert.showAndWait();
            return;
        }

        contenedorRutinas.getChildren().remove(selectedRutina);
        selectedRutina = null;
    }
    
    public void setScene(Scene scene) {
        this.scene = scene;
    }

}
