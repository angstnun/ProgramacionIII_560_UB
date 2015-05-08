package ar.edu.jdbc.util;

public enum Accion {
	
	ALTA('A'), BAJA('B'), MODIFICACION('M');
	
	private char representacion;
	
	private Accion(char representacion) {
		this.representacion = representacion;
	}
	
	public char getRepresentacion() {
		return this.representacion;
	}
	
}
