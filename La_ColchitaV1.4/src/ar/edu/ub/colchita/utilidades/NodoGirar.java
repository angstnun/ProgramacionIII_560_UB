package ar.edu.ub.colchita.utilidades;

/**
 * <b><big><u>Clase <code>NodoGirar</code>.</u></big></b>
 * <p>
 * Extiende de <code>NodoColchita</code>. Nodo utilizado para armar una estructura la cual maneja como se giran los retazos
 * frente a las especificaciones del usuario.
 * <p>
 */

public class NodoGirar extends NodoColchita{
    
    public NodoGirar(NodoColchita n) { 
    	this.left = n;
    }
    
	/**
	 * <b><u>M&eacutetodo <code>toString</code>.</u></b>
	 * <p>
	 * @return Devuelve el nombre de la clase en un string.
	 */
    
    public String toString() {
    	return "NodoGirar";
    }

}