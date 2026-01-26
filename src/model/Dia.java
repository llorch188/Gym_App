package model;

import java.util.ArrayList;

public class Dia {

	// Atributos
	private int idDia;
	private String nombre;
	private ArrayList<Ejercicio> ejercicios = new ArrayList<>();
	
	// Constructor
	public Dia(int idDia, String nombre, ArrayList<Ejercicio> ejercicios) {
		super();
		this.idDia = idDia;
		this.nombre = nombre;
		this.ejercicios = ejercicios;
	}
	
	// Getters and Setters
	public int getIdDia() {
		return idDia;
	}
	public void setIdDia(int idDia) {
		this.idDia = idDia;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public ArrayList<Ejercicio> getEjercicios() {
		return ejercicios;
	}
	public void setEjercicios(ArrayList<Ejercicio> ejercicios) {
		this.ejercicios = ejercicios;
	}
	
	
}
