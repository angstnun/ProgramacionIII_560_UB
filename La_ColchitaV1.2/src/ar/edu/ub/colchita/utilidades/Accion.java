package ar.edu.ub.colchita.utilidades;

public enum Accion {
	
	ALTA('A'), BAJA('B'), MODIFICACION('M');
	
	private char clave;
	
	private Accion(char clave) {
		this.clave = clave;
	}
	
	public char getClave() {
		return this.clave;
	}
	
}
