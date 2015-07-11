package ar.edu.ub.colchita.utilidades;

/**
 * <b><big><u>Clase <code>NodoCoser</code>.</u></big></b>
 * <p>
 * Extiende de <code>NodoColchita</code>. Nodo utilizado para armar una estructura la cual maneja como se cosen los retazos
 * frente a las especificaciones del usuario.
 * <p>
 */

public class NodoCoser extends NodoColchita{
	
	public int giros = 0;
    
    public NodoCoser(NodoColchita n, NodoColchita j) { 
    	this.left = n;
    	this.right = j;
    }
    
	/**
	 * <b><u>M&eacutetodo <code>toString</code>.</u></b>
	 * <p>
	 * @return Devuelve el nombre de la clase en un string.
	 */
    
    public String toString() {
    	return "NodoCoser";
    }

}