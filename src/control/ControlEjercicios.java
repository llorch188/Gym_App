package control;

import java.util.List;
import java.util.Map;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.Node;
import javafx.stage.Stage;
import model.Ejercicio;

public class ControlEjercicios {

    @FXML
    private GridPane gridEjercicios;

    private ControlRutina controlRutina; // 🔹 Referencia a ControlRutina

    public void setControlRutina(ControlRutina cr) {
        this.controlRutina = cr;
    }

    private final Map<String, List<EjercicioData>> ejerciciosPorMusculo = Map.of(
            "Espalda", List.of(
                    new EjercicioData("Dominadas", "/Imagenes/pullUps.gif"),
                    new EjercicioData("Remo con barra", "/Imagenes/remoBarra.gif"),
                    new EjercicioData("Jalón al pecho", "/Imagenes/jalon.gif"),
                    new EjercicioData("Remo mancuerna", "/Imagenes/remoMancuerna.gif")
            ),
            "Pectoral", List.of(
                    new EjercicioData("Press banca", "/Imagenes/pressBanca.gif"),
                    new EjercicioData("Flexiones", "/Imagenes/flexiones.gif")
            )
    );

    public void cargarEjercicios(String musculo) {

        gridEjercicios.getChildren().clear();
        List<EjercicioData> lista = ejerciciosPorMusculo.get(musculo);
        if (lista == null) return;

        int row = 0, col = 0;

        for (EjercicioData data : lista) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ViewEjercicioPlantilla.fxml"));
                BorderPane ejercicioPane = loader.load();

                ImagenConBotonesController controller = loader.getController();

                controller.setNombre(data.nombre);  // Nombre visible en la UI
                controller.setImage(data.rutaImagen);

                // 🔹 BOTÓN LIKE: mover el panel al inicio y reorganizar
                controller.setOnLike(() -> {
                    gridEjercicios.getChildren().remove(ejercicioPane);
                    gridEjercicios.getChildren().add(0, ejercicioPane);
                    reorganizarGrid();
                });

                // 🔹 BOTÓN AÑADIR: añadir a la rutina y cerrar ventana
                controller.setOnAdd(() -> {
                    if (controlRutina != null) {
                        // Crear un Ejercicio nuevo con ID único usando el constructor actualizado
                        Ejercicio nuevoEjercicio = new Ejercicio(
                                generarIdUnico(),   // idEjercio
                                data.nombre,        // nombre del ejercicio
                                musculo,            // músculo trabajado
                                "Nota corta",       // nota breve o comentario
                                3,                  // series por defecto
                                10,                 // reps por defecto
                                0                   // peso por defecto
                        );

                        controlRutina.addEjercicioARutina(nuevoEjercicio);
                    }

                    // Cerrar ventana
                    Stage stage = (Stage) gridEjercicios.getScene().getWindow();
                    stage.close();
                });

                gridEjercicios.add(ejercicioPane, col, row);
                col++;
                if (col > 1) { col = 0; row++; }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Reordenar elementos del GridPane correctamente.
     */
    private void reorganizarGrid() {
        List<Node> nodes = gridEjercicios.getChildren().stream().toList();
        gridEjercicios.getChildren().clear();

        int col = 0, row = 0;
        for (Node n : nodes) {
            gridEjercicios.add(n, col, row);
            col++;
            if (col > 1) { col = 0; row++; }
        }
    }

    /**
     * Genera un ID único temporal para un nuevo ejercicio.
     * ⚠️ Esto se puede mejorar usando un contador global o un generador UUID si quieres persistencia.
     */
    private int generarIdUnico() {
        // Suma total de ejercicios actuales para generar ID
        int total = 0;
        if (controlRutina != null) {
            for (var dia : controlRutina.getDias()) {
                total += dia.getEjercicios().size();
            }
        }
        return total + 1;
    }

    private static class EjercicioData {
        String nombre;
        String rutaImagen;

        EjercicioData(String n, String r) {
            nombre = n;
            rutaImagen = r;
        }
    }
}
