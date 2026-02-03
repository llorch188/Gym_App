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

/**
 * Controlador encargado de mostrar y gestionar la selección
 * de ejercicios disponibles según el músculo trabajado.
 * Permite marcar ejercicios como favoritos y añadirlos
 * a una rutina.
 */
public class ControlEjercicios {

    @FXML
    private GridPane gridEjercicios;

    /**
     * Referencia al controlador de la rutina para poder
     * añadir ejercicios seleccionados.
     */
    private ControlRutina controlRutina; // 🔹 Referencia a ControlRutina

    /**
     * Establece el controlador de la rutina asociado.
     *
     * @param cr controlador de la rutina
     */
    public void setControlRutina(ControlRutina cr) {
        this.controlRutina = cr;
    }

    /**
     * Mapa que relaciona cada músculo con la lista
     * de ejercicios disponibles para ese grupo muscular.
     */
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

    /**
     * Carga en el GridPane los ejercicios correspondientes
     * al músculo seleccionado.
     *
     * @param musculo nombre del músculo a mostrar
     */
    public void cargarEjercicios(String musculo) {

        gridEjercicios.getChildren().clear();
        List<EjercicioData> lista = ejerciciosPorMusculo.get(musculo);
        if (lista == null) return;

        int row = 0, col = 0;

        for (EjercicioData data : lista) {
            try {
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("/view/ViewEjercicioPlantilla.fxml")
                );
                BorderPane ejercicioPane = loader.load();

                ImagenConBotonesController controller = loader.getController();

                controller.setNombre(data.nombre);  // Nombre visible en la UI
                controller.setImage(data.rutaImagen);

                /**
                 * Acción del botón "like":
                 * mueve el ejercicio al inicio del grid
                 * y reorganiza los elementos.
                 */
                controller.setOnLike(() -> {
                    gridEjercicios.getChildren().remove(ejercicioPane);
                    gridEjercicios.getChildren().add(0, ejercicioPane);
                    reorganizarGrid();
                });

                /**
                 * Acción del botón "añadir":
                 * crea un nuevo ejercicio, lo añade a la rutina
                 * y cierra la ventana.
                 */
                controller.setOnAdd(() -> {
                    if (controlRutina != null) {
                        // Crear un Ejercicio nuevo con ID único
                        Ejercicio nuevoEjercicio = new Ejercicio(
                                generarIdUnico(),   // idEjercicio
                                data.nombre,        // nombre del ejercicio
                                musculo,            // músculo trabajado
                                "Nota corta",       // nota breve
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
                if (col > 1) {
                    col = 0;
                    row++;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Reorganiza los elementos del GridPane para mantener
     * un orden correcto tras mover ejercicios.
     */
    private void reorganizarGrid() {
        List<Node> nodes = gridEjercicios.getChildren().stream().toList();
        gridEjercicios.getChildren().clear();

        int col = 0, row = 0;
        for (Node n : nodes) {
            gridEjercicios.add(n, col, row);
            col++;
            if (col > 1) {
                col = 0;
                row++;
            }
        }
    }

    /**
     * Genera un identificador único temporal para un nuevo ejercicio.
     * ⚠️ Este método puede mejorarse usando un generador UUID
     * o persistencia de datos.
     *
     * @return ID único del ejercicio
     */
    private int generarIdUnico() {
        int total = 0;
        if (controlRutina != null) {
            for (var dia : controlRutina.getDias()) {
                total += dia.getEjercicios().size();
            }
        }
        return total + 1;
    }

    /**
     * Clase auxiliar que representa los datos básicos
     * de un ejercicio (nombre e imagen).
     */
    private static class EjercicioData {

        String nombre;
        String rutaImagen;

        /**
         * Crea un nuevo conjunto de datos de ejercicio.
         *
         * @param n nombre del ejercicio
         * @param r ruta de la imagen del ejercicio
         */
        EjercicioData(String n, String r) {
            nombre = n;
            rutaImagen = r;
        }
    }
}
