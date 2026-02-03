package control;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import model.Dia;
import model.Ejercicio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controlador principal de la vista de rutina.
 * Gestiona los días de entrenamiento, los ejercicios asociados
 * a cada día y la visualización de estadísticas mediante gráficas.
 */
public class ControlRutina {

    @FXML
    private StackedBarChart<String, Number> graficaMusculos;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @FXML
    private TabPane tabPane;

    private ArrayList<Dia> dias = new ArrayList<>();
    private final Map<Dia, ListView<Ejercicio>> listViewPorDia = new HashMap<>();

    /**
     * Inicializa el controlador después de cargar el FXML.
     * Carga los días con ejercicios, muestra los tabs y
     * actualiza la gráfica según el día seleccionado.
     */
    @FXML
    private void initialize() {
        cargarDiasConEjercicios();
        mostrarDias();

        tabPane.getSelectionModel().selectedItemProperty().addListener((obs, oldTab, newTab) -> {
            if (newTab != null) {
                int indexDia = tabPane.getSelectionModel().getSelectedIndex();
                actualizarGrafica(dias.get(indexDia));
            }
        });

        if (!dias.isEmpty()) {
            actualizarGrafica(dias.get(0));
        }
    }

    /**
     * Carga los días de entrenamiento con ejercicios
     * predefinidos.
     */
    private void cargarDiasConEjercicios() {
        dias.clear();

        // DÍA 1: ESPALDA
        ArrayList<Ejercicio> espalda = new ArrayList<>();
        espalda.add(new Ejercicio(1,"Remo", "Espalda", "Lentas", 4, 8, 0,"/Imagenes/remoBarra.gif"));
        espalda.add(new Ejercicio(2,"Jalon al pecho", "Espalda", "Rir 1-2", 4, 10, 40,"/Imagenes/jalon.gif"));
        espalda.add(new Ejercicio(3,"Dominadas", "Espalda", "", 3, 12, 35,"/Imagenes/pullUps.gif"));
        dias.add(new Dia(1, "Espalda", espalda));

        // DÍA 2: PECHO
        ArrayList<Ejercicio> pecho = new ArrayList<>();
        pecho.add(new Ejercicio(5,"Press Banca", "Pecho", "", 4, 8, 50,"/Imagenes/pressBanca.gif"));
        pecho.add(new Ejercicio(6,"Press inclinado", "Pecho", "1 serie al fallo", 4, 10, 40));
        pecho.add(new Ejercicio(7,"Flexiones", "Pecho", "Control bajada", 3, 12, 12,"/Imagenes/flexiones.gif"));
        dias.add(new Dia(2, "Pecho", pecho));

        // DÍA 3: PIERNAS
        ArrayList<Ejercicio> piernas = new ArrayList<>();
        piernas.add(new Ejercicio(9, "Sentadilla", "Piernas","", 4, 8, 60));
        piernas.add(new Ejercicio(10, "Prensa", "Piernas","", 4, 12, 120));
        piernas.add(new Ejercicio(11, "Zancadas", "Piernas","", 3, 12, 20));
        piernas.add(new Ejercicio(12, "Curl femoral", "Piernas","", 3, 15, 35));
        dias.add(new Dia(3, "Piernas", piernas));

        // DÍA 4: CORE
        ArrayList<Ejercicio> core = new ArrayList<>();
        core.add(new Ejercicio(13, "Plancha", "Core","", 3, 45, 0));
        core.add(new Ejercicio(14, "Elevación de piernas","", "Core", 3, 12, 0));
        core.add(new Ejercicio(15, "Crunch máquina", "Core","", 3, 15, 20));
        core.add(new Ejercicio(16, "Russian twist", "Core","", 3, 20, 5));
        dias.add(new Dia(4, "Core", core));

        dias.add(new Dia(5, "Día 5", new ArrayList<>()));
        dias.add(new Dia(6, "Día 6", new ArrayList<>()));
    }

    /**
     * Devuelve la lista de días de la rutina.
     *
     * @return lista de días
     */
    public ArrayList<Dia> getDias() {
        return dias;
    }

    /**
     * Muestra los días de la rutina en el TabPane,
     * creando una pestaña y lista de ejercicios por cada día.
     */
    public void mostrarDias() {
        tabPane.getTabs().clear();
        listViewPorDia.clear();

        for (Dia dia : dias) {
            Tab tab = new Tab(dia.getNombre());
            ListView<Ejercicio> list = new ListView<>();
            list.getItems().addAll(dia.getEjercicios());

            list.setCellFactory(param -> new ListCell<>() {
                @Override
                protected void updateItem(Ejercicio e, boolean empty) {
                    super.updateItem(e, empty);
                    if (empty || e == null) {
                        setText(null);
                    } else {
                        setText(e.getNombre() + " | " + e.getSeries() + "x" + e.getReps() + " | " + e.getPeso() + " kg");

                        e.seriesProperty().addListener((obs, oldV, newV) ->
                                setText(e.getNombre() + " | " + newV + "x" + e.getReps() + " | " + e.getPeso() + " kg"));
                        e.repsProperty().addListener((obs, oldV, newV) ->
                                setText(e.getNombre() + " | " + e.getSeries() + "x" + newV + " | " + e.getPeso() + " kg"));
                        e.pesoProperty().addListener((obs, oldV, newV) ->
                                setText(e.getNombre() + " | " + e.getSeries() + "x" + e.getReps() + " | " + newV + " kg"));
                    }
                }
            });

            list.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2) {
                    Ejercicio seleccionado = list.getSelectionModel().getSelectedItem();
                    if (seleccionado != null) abrirEditorEjercicio(seleccionado);
                }
            });

            tab.setContent(list);
            tabPane.getTabs().add(tab);
            listViewPorDia.put(dia, list);
        }
    }

    /**
     * Abre la ventana de selección de músculos
     * para añadir un nuevo ejercicio.
     */
    @FXML
    private void onAddClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ViewMusculos.fxml"));
            Parent root = loader.load();

            ControlMusculos musculosController = loader.getController();
            musculosController.setControlRutina(this);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Seleccionar músculo");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Añade un ejercicio al día actualmente seleccionado.
     *
     * @param ejercicio ejercicio a añadir
     */
    public void addEjercicioARutina(Ejercicio ejercicio) {
        int indexDia = tabPane.getSelectionModel().getSelectedIndex();
        if (indexDia < 0 || indexDia >= dias.size()) return;

        Dia dia = dias.get(indexDia);
        dia.getEjercicios().add(ejercicio);

        ListView<Ejercicio> listView = listViewPorDia.get(dia);
        if (listView != null) listView.getItems().add(ejercicio);

        actualizarGrafica(dia);
    }

    /**
     * Actualiza la gráfica de músculos según
     * los ejercicios del día indicado.
     *
     * @param dia día seleccionado
     */
    private void actualizarGrafica(Dia dia) {
        graficaMusculos.getData().clear();
        Map<String, Integer> conteoMusculos = new HashMap<>();

        for (Ejercicio e : dia.getEjercicios()) {
            conteoMusculos.put(
                    e.getMusculo(),
                    conteoMusculos.getOrDefault(e.getMusculo(), 0) + 1
            );
        }

        for (Map.Entry<String, Integer> entry : conteoMusculos.entrySet()) {
            StackedBarChart.Series<String, Number> serie = new StackedBarChart.Series<>();
            serie.setName(entry.getKey());
            serie.getData().add(
                    new StackedBarChart.Data<>(dia.getNombre(), entry.getValue())
            );
            graficaMusculos.getData().add(serie);
        }
    }

    /**
     * Abre la ventana de edición del ejercicio seleccionado.
     */
    @FXML
    private void onEditClick() {
        try {
            Tab tab = tabPane.getSelectionModel().getSelectedItem();
            if (tab == null) { mostrarAlerta("Error", "No hay ningún día seleccionado."); return; }

            ListView<Ejercicio> list = (ListView<Ejercicio>) tab.getContent();
            Ejercicio ejercicioSeleccionado = list.getSelectionModel().getSelectedItem();
            if (ejercicioSeleccionado == null) { mostrarAlerta("Error", "No hay ningún ejercicio seleccionado."); return; }

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ViewEjercicio.fxml"));
            Parent root = loader.load();

            ControlEditarEjercicio controller = loader.getController();
            controller.setEjercicio(ejercicioSeleccionado);
            controller.setControlRutina(this);

            Stage stage = new Stage();
            stage.setTitle("Editar Ejercicio");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Muestra una alerta de error.
     *
     * @param titulo título de la alerta
     * @param mensaje mensaje a mostrar
     */
    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    /**
     * Refresca la visualización de un ejercicio editado
     * y actualiza la gráfica correspondiente.
     *
     * @param ejercicio ejercicio modificado
     */
    public void refreshEjercicio(Ejercicio ejercicio) {
        for (Dia dia : dias) {
            ListView<Ejercicio> listView = listViewPorDia.get(dia);
            if (listView != null && dia.getEjercicios().contains(ejercicio)) {
                listView.refresh();
                actualizarGrafica(dia);
                break;
            }
        }
    }

    /**
     * Elimina el ejercicio seleccionado del día actual.
     */
    @FXML
    private void onDeleteClick() {
        Tab tab = tabPane.getSelectionModel().getSelectedItem();
        if (tab == null) return;

        ListView<Ejercicio> list = (ListView<Ejercicio>) tab.getContent();
        Ejercicio ejercicioSeleccionado = list.getSelectionModel().getSelectedItem();
        if (ejercicioSeleccionado == null) {
            mostrarAlerta("Error", "No hay ningún ejercicio seleccionado.");
            return;
        }

        list.getItems().remove(ejercicioSeleccionado);
        int indexDia = tabPane.getSelectionModel().getSelectedIndex();
        if (indexDia >= 0 && indexDia < dias.size()) {
            dias.get(indexDia).getEjercicios().remove(ejercicioSeleccionado);
            actualizarGrafica(dias.get(indexDia));
        }
    }

    /**
     * Vuelve a la vista principal de la aplicación.
     */
    @FXML
    private void onAtrasClicked() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/mainView.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) tabPane.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Inicio");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Abre la ventana de edición para el ejercicio indicado.
     *
     * @param ejercicio ejercicio a editar
     */
    private void abrirEditorEjercicio(Ejercicio ejercicio) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/EditarEjercicio.fxml"));
            Parent root = loader.load();

            ControlEditarEjercicio controller = loader.getController();
            controller.setEjercicio(ejercicio);
            controller.setControlRutina(this);

            Stage stage = new Stage();
            stage.setTitle("Editar ejercicio");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
