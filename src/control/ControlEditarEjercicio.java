package control;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
import model.Ejercicio;

/**
 * Controlador de la ventana de edición de un ejercicio.
 * Permite modificar las series, peso, repeticiones y notas
 * de un ejercicio existente.
 */
public class ControlEditarEjercicio {

    @FXML private ImageView imageView;
    @FXML private Text txtNombre;
    @FXML private Spinner<Integer> spinnerSeries;
    @FXML private TableView<PesoRepsRow> tablaPesoReps;
    @FXML private TableColumn<PesoRepsRow, Double> colPeso;
    @FXML private TableColumn<PesoRepsRow, Integer> colReps;
    @FXML private TextArea txtNotas;
    @FXML private Button saveButton;

    private Ejercicio ejercicio;
    private ControlRutina controlRutina;

    /**
     * Inicializa el controlador después de cargar el FXML.
     * Configura la tabla editable de peso y repeticiones
     * y asigna la acción al botón de guardar.
     */
    @FXML
    private void initialize() {
        tablaPesoReps.setEditable(true);

        colPeso.setCellValueFactory(new PropertyValueFactory<>("peso"));
        colPeso.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        colPeso.setOnEditCommit(event ->
                event.getRowValue().setPeso(event.getNewValue()));

        colReps.setCellValueFactory(new PropertyValueFactory<>("reps"));
        colReps.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        colReps.setOnEditCommit(event ->
                event.getRowValue().setReps(event.getNewValue()));

        saveButton.setOnAction(e -> guardarCambios());
    }

    /**
     * Carga los datos del ejercicio recibido en la interfaz
     * para permitir su edición.
     *
     * @param e ejercicio a editar
     */
    public void setEjercicio(Ejercicio e) {
        this.ejercicio = e;

        if (e.getRutaImagen() != null) {
            imageView.setImage(new Image(e.getRutaImagen()));
        }

        txtNombre.setText(e.getNombre());
        spinnerSeries.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20, e.getSeries())
        );
        txtNotas.setText(e.getNota());

        tablaPesoReps.getItems().clear();
        tablaPesoReps.getItems().add(
                new PesoRepsRow(e.getPeso(), e.getReps())
        );
    }

    /**
     * Guarda los cambios realizados en el ejercicio
     * y actualiza la vista de la rutina si es necesario.
     * Finalmente cierra la ventana de edición.
     */
    private void guardarCambios() {
        if (ejercicio == null) return;

        ejercicio.setSeries(spinnerSeries.getValue());
        ejercicio.setNota(txtNotas.getText());

        if (!tablaPesoReps.getItems().isEmpty()) {
            PesoRepsRow row = tablaPesoReps.getItems().get(0);
            ejercicio.setPeso(row.getPeso());
            ejercicio.setReps(row.getReps());
        }

        if (controlRutina != null) {
            controlRutina.refreshEjercicio(ejercicio);
        }

        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }

    /**
     * Establece el controlador de la rutina para poder
     * refrescar el ejercicio editado.
     *
     * @param c controlador de la rutina
     */
    public void setControlRutina(ControlRutina c) {
        this.controlRutina = c;
    }

    /**
     * Clase auxiliar que representa una fila de la tabla
     * de peso y repeticiones.
     */
    public static class PesoRepsRow {

        private double peso;
        private int reps;

        /**
         * Crea una nueva fila con peso y repeticiones.
         *
         * @param peso peso del ejercicio
         * @param reps número de repeticiones
         */
        public PesoRepsRow(double peso, int reps) {
            this.peso = peso;
            this.reps = reps;
        }

        /**
         * Devuelve el peso.
         *
         * @return peso del ejercicio
         */
        public double getPeso() {
            return peso;
        }

        /**
         * Establece el peso.
         *
         * @param p nuevo peso
         */
        public void setPeso(double p) {
            this.peso = p;
        }

        /**
         * Devuelve las repeticiones.
         *
         * @return número de repeticiones
         */
        public int getReps() {
            return reps;
        }

        /**
         * Establece las repeticiones.
         *
         * @param r nuevo número de repeticiones
         */
        public void setReps(int r) {
            this.reps = r;
        }
    }
}
