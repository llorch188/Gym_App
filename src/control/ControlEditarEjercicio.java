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

    @FXML
    private void initialize() {
        tablaPesoReps.setEditable(true);

        colPeso.setCellValueFactory(new PropertyValueFactory<>("peso"));
        colPeso.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        colPeso.setOnEditCommit(event -> event.getRowValue().setPeso(event.getNewValue()));

        colReps.setCellValueFactory(new PropertyValueFactory<>("reps"));
        colReps.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        colReps.setOnEditCommit(event -> event.getRowValue().setReps(event.getNewValue()));

        saveButton.setOnAction(e -> guardarCambios());
    }

    public void setEjercicio(Ejercicio e) {
        this.ejercicio = e;

        if (e.getRutaImagen() != null) imageView.setImage(new Image(e.getRutaImagen()));
        txtNombre.setText(e.getNombre());
        spinnerSeries.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20, e.getSeries()));
        txtNotas.setText(e.getNota());

        tablaPesoReps.getItems().clear();
        tablaPesoReps.getItems().add(new PesoRepsRow(e.getPeso(), e.getReps()));
    }

    private void guardarCambios() {
        if (ejercicio == null) return;

        ejercicio.setSeries(spinnerSeries.getValue());
        ejercicio.setNota(txtNotas.getText());

        if (!tablaPesoReps.getItems().isEmpty()) {
            PesoRepsRow row = tablaPesoReps.getItems().get(0);
            ejercicio.setPeso(row.getPeso());
            ejercicio.setReps(row.getReps());
        }

        if (controlRutina != null) controlRutina.refreshEjercicio(ejercicio);

        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }

    public void setControlRutina(ControlRutina c) { this.controlRutina = c; }

    public static class PesoRepsRow {
        private double peso;
        private int reps;

        public PesoRepsRow(double peso, int reps) { this.peso = peso; this.reps = reps; }

        public double getPeso() { return peso; }
        public void setPeso(double p) { this.peso = p; }

        public int getReps() { return reps; }
        public void setReps(int r) { this.reps = r; }
    }
}
