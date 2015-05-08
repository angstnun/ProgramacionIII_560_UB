package ar.edu.jdbc.model;

import java.util.Calendar;
import java.util.Date;

import ar.edu.jdbc.util.Fecha;

public class PropiedadModel {
	
	protected long id;
	protected String nombre;
	protected long numero;
	protected Calendar borradoFecha = null;
	protected boolean borrado = false;
	
	public PropiedadModel() {
		
	}
	
	public PropiedadModel(String id, long numero, String nombre) {
		implProp(Long.decode(id).longValue(), numero, nombre);
	}
	
	public PropiedadModel(long id, long numero, String nombre) {
		implProp(id, numero, nombre);
	}
	
	public PropiedadModel(long id, long numero) {
		implProp(id, numero, null);
	}
	
	private void implProp(long id, long numero, String nombre) {
		this.setId(id);
		this.setNumero(numero);
		this.setNombre(nombre);
	}
	
	public String getNombre() {
		if (this.nombre == null)
			return ("--");
		return this.nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getIdStr() {
		return Long.toString(this.getId(), 10);
	}
	
	public long getId() {
		return this.id;
	}
	
	public long getNumero() {
		return this.numero;
	}
	
	public void setNumero(long numero) {
		this.numero = numero;
	}
	
	public void setId(String id) {
		if (id != null)
			this.id = Long.decode(id).longValue();
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public void setBorrado() {
		this.borradoFecha = Calendar.getInstance();
		this.borrado = true;
	}
	
	public void setBorrado(Date fecha) throws Exception {
		this.borradoFecha = Fecha.loadFecha(fecha);
		this.borrado = (this.borradoFecha != null);
	}
	
	public void setBorrado(String fecha) throws Exception {
		this.borradoFecha = Fecha.loadFecha(fecha);
		this.borrado = (this.borradoFecha != null);
	}
	
	public boolean getBorrado() {
		return this.borrado;
	}
	
	public String getBorradoFecha() {
		return Fecha.fmtFecha(this.borradoFecha);
	}
	
	public String toString() {
		return this.getNombre().toUpperCase();
	}
	
}