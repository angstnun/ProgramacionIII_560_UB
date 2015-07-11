package ar.edu.ub.colchita.utilidades;

/**
 * <b><big><u><code>enum</code> <code>Constantes</code>.</u></big></b>
 * <p>
 * Representa una lista limitada de valores que se utilizan durante la ejecuci&oacuten del programa.
 * <p>
 */

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
