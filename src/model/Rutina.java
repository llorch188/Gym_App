package model;

import java.util.ArrayList;

public class Rutina {

	// Atributos
	private int idRutina;
	private String nombre;
	private ArrayList<Dia> dias = new ArrayList<>();
	
	// Constructor
	public Rutina(int idRutina, String nombre, ArrayList<Dia> dias) {
		super();
		this.idRutina = idRutina;
		this.nombre = nombre;
		this.dias = dias;
	}
	
	public Rutina() {
		super();
	}

	// Getters and Setters
	public int getIdRutina() {
		return idRutina;
	}
	public void setIdRutina(int idRutina) {
		this.idRutina = idRutina;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public ArrayList<Dia> getDias() {
		return dias;
	}
	public void setDias(ArrayList<Dia> dias) {
		this.dias = dias;
	}
	
	
}
