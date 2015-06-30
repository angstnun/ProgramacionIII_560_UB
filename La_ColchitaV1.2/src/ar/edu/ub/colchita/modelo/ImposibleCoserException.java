package ar.edu.ub.colchita.modelo;

/**
 * <b><big><u>Excepcion <code>ImposibleCoserException</code>.</u></big></b>
 * <p>
 * Extiende de la clase <code>Exception</code>. 
 * Excepcion arrojada cuando dos retazos de la clase <code>Retazo</code> no coinciden en su atributo <code>alto</code>.
 */
public class ImposibleCoserException extends Exception{
	
	private static final long serialVersionUID = 1L;
	
	public ImposibleCoserException(){
		super("Medidas  de retazos incorrectas.");
	}
}
