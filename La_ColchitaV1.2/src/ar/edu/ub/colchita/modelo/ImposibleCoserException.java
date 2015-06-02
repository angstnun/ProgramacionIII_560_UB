package ar.edu.ub.colchita.modelo;

public class ImposibleCoserException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	public ImposibleCoserException() {
		super("Medidas incompatibles.");
	}
}
