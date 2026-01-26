package model;

import javafx.beans.property.*;

public class Ejercicio {

    private final IntegerProperty idEjercicio;
    private final StringProperty nombre;
    private final StringProperty musculo;
    private final StringProperty nota;
    private final IntegerProperty series;
    private final IntegerProperty reps;
    private final DoubleProperty peso;
    private final StringProperty rutaImagen;

    public Ejercicio(int idEjercicio, String nombre, String musculo, String nota, int series, int reps, double peso) {
        this(idEjercicio, nombre, musculo, nota, series, reps, peso, null);
    }

    public Ejercicio(int idEjercicio, String nombre, String musculo, String nota, int series, int reps, double peso, String rutaImagen) {
        this.idEjercicio = new SimpleIntegerProperty(idEjercicio);
        this.nombre = new SimpleStringProperty(nombre);
        this.musculo = new SimpleStringProperty(musculo);
        this.nota = new SimpleStringProperty(nota);
        this.series = new SimpleIntegerProperty(series);
        this.reps = new SimpleIntegerProperty(reps);
        this.peso = new SimpleDoubleProperty(peso);
        this.rutaImagen = new SimpleStringProperty(rutaImagen);
    }

    // Getters y setters con propiedades
    public int getIdEjercicio() { return idEjercicio.get(); }
    public void setIdEjercicio(int id) { idEjercicio.set(id); }
    public IntegerProperty idEjercicioProperty() { return idEjercicio; }

    public String getNombre() { return nombre.get(); }
    public void setNombre(String n) { nombre.set(n); }
    public StringProperty nombreProperty() { return nombre; }

    public String getMusculo() { return musculo.get(); }
    public void setMusculo(String m) { musculo.set(m); }
    public StringProperty musculoProperty() { return musculo; }

    public String getNota() { return nota.get(); }
    public void setNota(String n) { nota.set(n); }
    public StringProperty notaProperty() { return nota; }

    public int getSeries() { return series.get(); }
    public void setSeries(int s) { series.set(s); }
    public IntegerProperty seriesProperty() { return series; }

    public int getReps() { return reps.get(); }
    public void setReps(int r) { reps.set(r); }
    public IntegerProperty repsProperty() { return reps; }

    public double getPeso() { return peso.get(); }
    public void setPeso(double p) { peso.set(p); }
    public DoubleProperty pesoProperty() { return peso; }

    public String getRutaImagen() { return rutaImagen.get(); }
    public void setRutaImagen(String r) { rutaImagen.set(r); }
    public StringProperty rutaImagenProperty() { return rutaImagen; }
}
